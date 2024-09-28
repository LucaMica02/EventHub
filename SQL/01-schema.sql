begin transaction;

-- DOMAINS
create domain String as varchar(100) check (value is not null);
create domain LongString as varchar(250);
create domain Stars as Real check (value >= 1 and value <= 5);
create domain IntGZ as Integer check (value > 0);
create domain IntGEZ as Integer check (value >= 0);
create domain RealGEZ as Real check (value >= 0);
create domain Email as String check (value ~ '^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$');
create domain Url as String check (value ~ '^https?:\/\/[^\s\/$.?#].[^\s]*$');

-- TYPES
create type EventState as enum ('Open', 'Closed', 'Finished', 'Cancelled');


-- TABLES
create table Nation (
    id serial not null,
    name String not null,
    primary key(id)
);

create table City (
    id serial not null,
    name String not null,
    nation IntGZ not null,
    foreign key (nation) references Nation (id),
    unique (name, nation),
    primary key (id)
);

create table AppUser (
    id serial not null,
    name String not null,
    lastname String not null,
    username String not null,
    email Email not null, 
    password text not null,
    street String not null,
    zipCode String not null,
    city IntGZ not null,
    foreign key (city) references City (id),
    unique (username),
    unique (email),
    primary key (id)
);

create table Creator (
    appUser IntGZ not null,
    taxID String not null,
    foreign key (appUser) references AppUser (id),
    unique (taxID),
    primary key (appUser)
);

create table Event (
    id serial not null,
    creator IntGZ not null, 
    title String not null,
    description LongString not null,
    maxPeople IntGEZ not null,
    startBooking Timestamp not null,
    endBooking Timestamp not null,
    startEvent Timestamp not null,
    endEvent Timestamp not null,
    state String not null,
    price RealGEZ not null,
    check (endBooking > startBooking),
    check (endEvent > startEvent),
    check (startEvent > endBooking),
    foreign key (creator) references Creator (appUser),
    primary key (id)
);

create table OnlineEvent (
    event Integer not null,
    url Url not null,
    foreign key (event) references Event (id),
    primary key (event)
);

create table InPersonEvent (
    event IntGZ not null,
    street String not null,
    zipCode String not null,
    city IntGZ not null,
    foreign key (city) references City (id),
    foreign key (event) references Event (id),
    primary key (event)
);

create table UserEvent (
    id serial not null,
    event IntGZ not null,
    appUser IntGZ not null,
    foreign key (event) references Event (id), 
    foreign key (appUser) references AppUser (id),
    primary key (id),
    unique (event, appUser)
);

create table Feedback (
    id serial not null,
    event IntGZ not null,
    appUser IntGZ not null,
    stars Stars not null,
    description LongString not null,
    dateTime Timestamp not null,
    foreign key (event, appUser) references UserEvent (event, appUser), 
    primary key (id),
    unique (event, appUser)
);

commit;