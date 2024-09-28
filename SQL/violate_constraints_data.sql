/*
-- TRIGGERSSSSS AND DATA TO VIOLATE IT

-- trigger_check_event_complete_and_disjoint
INSERT into Event (creator, title, description, maxPeople, startBooking, endBooking, startEvent, endEvent, state, price) 
VALUES (25, 'HealthTech Summit 2025', 'Exploring the intersection of healthcare and technology.', 550, '2025-12-01 09:00:00', '2025-12-30 18:00:00', '2026-01-05 09:00:00', '2026-01-07 18:00:00', 'Closed', 320.00);

-- trigger_check_valid_state
-- Violate Open
INSERT into Event (creator, title, description, maxPeople, startBooking, endBooking, startEvent, endEvent, state, price) 
VALUES (25, 'HealthTech Summit 2025', 'Exploring the intersection of healthcare and technology.', 550, '2025-12-01 09:00:00', '2025-12-30 18:00:00', '2026-01-05 09:00:00', '2026-01-07 18:00:00', 'Open', 320.00);
-- Violate Closed
INSERT into Event (creator, title, description, maxPeople, startBooking, endBooking, startEvent, endEvent, state, price) 
VALUES (25, 'HealthTech Summit 2025', 'Exploring the intersection of healthcare and technology.', 550, '2024-08-01 09:00:00', '2024-12-30 18:00:00', '2026-01-05 09:00:00', '2026-01-07 18:00:00', 'Closed', 320.00);
-- Violate Finished
INSERT into Event (creator, title, description, maxPeople, startBooking, endBooking, startEvent, endEvent, state, price) 
VALUES (25, 'HealthTech Summit 2025', 'Exploring the intersection of healthcare and technology.', 550, '2025-12-01 09:00:00', '2025-12-30 18:00:00', '2026-01-05 09:00:00', '2026-01-07 18:00:00', 'Finished', 320.00);

-- trigger_valid_feedback
INSERT INTO Feedback (event, "user", stars, description)
VALUES
(1, 1, 4, 'Blockchain Summit 2024 was a well-organized, insightful event, perfect for exploring healthcare innovation.');

-- trigger_user_event_not_a_creator
INSERT INTO UserEvent (event, "user") VALUES (1, 25);

-- trigger_check_max_people_event
begin transaction;
INSERT into Event (creator, title, description, maxPeople, startBooking, endBooking, startEvent, endEvent, state, price) 
VALUES (25, 'HealthTech Summit 2024', 'Exploring the intersection of healthcare and technology.', 1, '2024-08-01 09:00:00', '2024-12-30 18:00:00', '2025-01-05 09:00:00', '2025-01-07 18:00:00', 'Open', 320.00);
INSERT INTO OnlineEvent (event, url)
VALUES (20, 'https://www.healthtechsummit.net');
INSERT INTO UserEvent (event, "user")
VALUES (20, 1), (16, 2);
commit;

-- RollBACK --> drop table if exists Nation, City, AppUser, Creator, Event, OnlineEvent, InPersonEvent, UserEvent, Feedback cascade;
*/