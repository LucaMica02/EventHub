begin transaction;

-- REVOCATION
REVOKE UPDATE (id) ON Nation FROM PUBLIC;
REVOKE DELETE ON Nation FROM PUBLIC;

REVOKE UPDATE (id, nation) ON City FROM PUBLIC;
REVOKE DELETE ON City FROM PUBLIC;

REVOKE UPDATE (id, name, lastname) ON AppUser FROM PUBLIC;

REVOKE UPDATE (appUser) ON Creator FROM PUBLIC;

REVOKE UPDATE ON Event FROM PUBLIC;
GRANT UPDATE (state) ON Event TO PUBLIC;
REVOKE DELETE ON Event FROM PUBLIC;

REVOKE UPDATE ON OnlineEvent FROM PUBLIC;
REVOKE DELETE ON OnlineEvent FROM PUBLIC;

REVOKE UPDATE ON InPersonEvent FROM PUBLIC;
REVOKE DELETE ON InPersonEvent FROM PUBLIC;

REVOKE UPDATE ON UserEvent FROM PUBLIC;

REVOKE UPDATE ON Feedback FROM PUBLIC;
REVOKE DELETE ON Feedback FROM PUBLIC;


/* ### Event TRIGGERS ### */

-- FUNCTION
CREATE OR REPLACE FUNCTION check_event_complete_and_disjoint() RETURNS trigger AS $check_event_complete_and_disjoint$
declare
    onlineBool Boolean;
    InPersonBool Boolean;
begin
    onlineBool := exists (select 1 from OnlineEvent oe where oe.event = new.id);
    InPersonBool := exists (select 1 from InPersonEvent ipe where ipe.event = new.id);

    -- verify that the event does not exist in both tables (disjoint constraint)
    if onlineBool and InPersonBool then
        raise exception 'Disjoint constraint violated for event %', new.id;

    -- check that the event exists in at least one of the tables (complete constraint)
    elsif not onlineBool and not InPersonBool then
        raise exception 'Complete constraint violated for event %', new.id;
    end if;

    return new;
end;
$check_event_complete_and_disjoint$ language plpgsql;

-- TRIGGER
CREATE CONSTRAINT TRIGGER trigger_check_event_complete_and_disjoint
AFTER INSERT ON Event DEFERRABLE 
FOR EACH ROW EXECUTE FUNCTION check_event_complete_and_disjoint();

-- FUNCTION
CREATE OR REPLACE FUNCTION check_valid_state() RETURNS trigger AS $check_valid_state$
declare
    openBool Boolean;
    closedBool Boolean;
    finishedBool Boolean;
    registeredUser Integer;
begin
    select count(appUser) into registeredUser
    from UserEvent ue 
    where ue.event = new.id;

    openBool := (now() between new.startBooking and new.endBooking) and registeredUser < new.maxPeople;
    closedBool := ((now() between new.startBooking and new.endBooking) and registeredUser = new.maxPeople) 
                or (not (now() between new.startBooking and new.endBooking) );
    finishedBool := now() > new.endEvent;

    -- check if the state is valid
    if new.state = 'Open' and not openBool then
        raise exception 'The event % cant be open', new.id;
    elsif new.state = 'Closed' and not closedBool then
        raise exception 'The event % cant be closed', new.id;
    elsif new.state = 'Finished' and not finishedBool then
        raise exception 'The event % cant be finished', new.id;
    end if;

    return new;
end;
$check_valid_state$ language plpgsql;

-- TRIGGER
CREATE TRIGGER trigger_check_valid_state
BEFORE INSERT OR UPDATE ON Event
FOR EACH ROW EXECUTE FUNCTION check_valid_state();

/* ### Feedback TRIGGERS ### */
-- FUNCTION
CREATE OR REPLACE FUNCTION valid_feedback() RETURNS trigger AS $valid_feedback$
declare 
    state EventState;
    dateEvent Timestamp;
begin
    SELECT e.state, e.endEvent 
    INTO state, dateEvent
    FROM Event e
    WHERE NEW.event = e.id;

    -- check if the event state is equal to 'Finished'
    if state != 'Finished' then
        raise exception 'For give a feedback the event must be finished';
    elsif new.dateTime < dateEvent then 
        raise exception 'The feedback timestamp must be after the end event timestamp';
    elsif new.dateTime > localtimestamp then 
        raise exception 'The feedback timestamp can not be after current timestamp';
    end if;
    return new;
end;
$valid_feedback$ language plpgsql;

-- TRIGGER
CREATE TRIGGER trigger_valid_feedback
BEFORE INSERT ON Feedback 
FOR EACH ROW EXECUTE FUNCTION valid_feedback();


/* ### UserEvent TRIGGERS ### */

-- FUNCTION
CREATE OR REPLACE FUNCTION user_event_not_a_creator() RETURNS trigger AS $user_event_not_a_creator$
declare 
    isError Boolean;
begin
    -- check if user is not a creator
    isError := exists(select 1 from Creator where Creator.appUser = new.appUser);
    if isError then
        raise exception 'The creators cannot partecipate in the events';
    end if;
    return new;
end;
$user_event_not_a_creator$ language plpgsql;

-- TRIGGER
CREATE TRIGGER trigger_user_event_not_a_creator
BEFORE INSERT ON UserEvent 
FOR EACH ROW EXECUTE FUNCTION user_event_not_a_creator();

-- FUNCTION
CREATE OR REPLACE FUNCTION check_max_people_event() RETURNS trigger AS $$
DECLARE
    max_people INT;
    count_people INT;
BEGIN
    -- get the maximum number of people allowed for the event
    SELECT maxPeople INTO max_people
    FROM public.Event
    WHERE public.Event.id = NEW.event;

    -- counts the number of users already registered for the event
    SELECT COUNT(*) INTO count_people
    FROM public.UserEvent
    WHERE public.UserEvent.event = NEW.event;

    -- check if the number of registered users is less than the maximum allowed
    IF count_people >= max_people THEN
        RAISE EXCEPTION 'The event is full, registration denied for the user %', NEW.user;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- TRIGGER
CREATE TRIGGER trigger_check_max_people_event
BEFORE INSERT ON UserEvent 
FOR EACH ROW EXECUTE FUNCTION check_max_people_event();

commit;