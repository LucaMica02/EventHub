begin transaction;

set constraints all deferred;

INSERT INTO Nation (name) VALUES ('Italy'), ('UK'), ('USA'), ('Germany'), ('France'), ('Spain'), ('Poland'), ('Swiss');

INSERT INTO City (name, nation) 
VALUES
('Rome', 1),
('Milan', 1),
('London', 2),
('Glasgow', 2), 
('New York', 3),
('Los Angeles', 3),
('Miami', 3),
('Berlin', 4),
('Paris', 5),
('Barcelona', 6),
('Madrid', 6),
('Warsaw', 7),
('Zurich', 8);

INSERT INTO AppUser (name, lastname, username, email, password, street, zipcode, city)
VALUES
('John', 'Doe', 'johndoe', 'john.doe@example.com', '$2a$10$VbvuGz0rGqMlh/5Qb6ZxVODNYGTH/dAfMnhFqJd2f5OYF3UC/XqKm', '123 Main St', '12345', 1),
('Alice', 'Smith', 'alicesmith', 'alice.smith@example.com', '$2a$10$5wU8WQYTRkxFAj6GzNLVbOZnsQun89Wdf2j6LZSYJzZ.k8KmCVB3W', '456 Oak St', '67890', 2),
('Bob', 'Johnson', 'bobjohnson', 'bob.johnson@example.com', '$2a$10$HjG4z0eDrdohmqQFQvTkGOGO14dYFow9XpDZ3jb6jwnPupGH4hYwK', '789 Pine St', '23456', 3),
('Charlie', 'Williams', 'charliewilliams', 'charlie.williams@example.com', '$2a$10$KeY5y8EHRMvhBRJ9F.LfJ.HO9sBJOFt/fRhg6FNqFWUznfRt.Mo0i', '123 Birch St', '34567', 4),
('David', 'Brown', 'davidbrown', 'david.brown@example.com', '$2a$10$wNqzUQnRj10kmk7pyoMLYOZmJ7d8m.p1CxsmwbOQltthfnj5XkwY.', '456 Maple St', '45678', 5),
('Eve', 'Jones', 'evejones', 'eve.jones@example.com', '$2a$10$.fKgTwE5YrSw4xgr6/mgYOdkW5n5CxiX5BXvhW2b9DyvHq0InZMKy', '789 Cedar St', '56789', 6),
('Frank', 'Miller', 'frankmiller', 'frank.miller@example.com', '$2a$10$2zLtjV/Kbs0/UnCvYZfA/Ovfg9h5HLzYB8TqEJZgfsK4G3Te7FUkS', '123 Elm St', '67890', 7),
('Grace', 'Wilson', 'gracewilson', 'grace.wilson@example.com', '$2a$10$sBpCqOmNQ0/rT2D5MhsPs.jtDk5pg2ZPdT9zjG8cqKpRmFGWqxRy6', '456 Spruce St', '78901', 8),
('Henry', 'Moore', 'henrymoore', 'henry.moore@example.com', '$2a$10$ZJq/bOUujrtz4c8p.ZRqEu/6Kr.z6L7OCW8L.e7UL8gDQZtU8u5Zm', '789 Walnut St', '89012', 9),
('Ivy', 'Taylor', 'ivytaylor', 'ivy.taylor@example.com', '$2a$10$uU9.Kh7q9v8gJsnBb4cP5uE0MXvZCd1u1GFGKsQ/NNYg7NrtOKFoG', '123 Chestnut St', '90123', 10),
('Jack', 'Anderson', 'jackanderson', 'jack.anderson@example.com', '$2a$10$ED2zV5q8PpX/OuTOXLyJ0ONJ8g.NrlP2Rx8j4x0OMHcK0WZt2/wkO', '456 Cypress St', '01234', 11),
('Kate', 'Thomas', 'katethomas', 'kate.thomas@example.com', '$2a$10$CwS/nOl7HFrHFg0sXRV8IuR1Jh9BB0kTgP4IkcZAhZuGQ5uEOkXtO', '789 Poplar St', '12345', 12),
('Leo', 'Jackson', 'leojackson', 'leo.jackson@example.com', '$2a$10$XvQeJYqSVo3Jzw7t5N4skOcgy8FBl5NHVmLSRzTF4TO.eDqYALZeS', '123 Magnolia St', '23456', 13),
('Mia', 'White', 'miawhite', 'mia.white@example.com', '$2a$10$PflIFt7YfEZKOdHdOkNN/.4E7rJQMBw9rztd/30XjOHSh5BBxMh/q', '456 Willow St', '34567', 1),
('Noah', 'Harris', 'noahharris', 'noah.harris@example.com', '$2a$10$cTDYMuGxt2WXWFWGhJcdQOqaTsdvUO9CbjvthLJvHiRZo1B6Axv0S', '789 Palm St', '45678', 2),
('Olivia', 'Martin', 'oliviamartin', 'olivia.martin@example.com', '$2a$10$1Qk2aRs5Rv58Gpr2hyV5s.W9xM8A3vbhupBdmn.kUsX3Q/.u/Gk5G', '123 Dogwood St', '56789', 3),
('Paul', 'Thompson', 'paulthompson', 'paul.thompson@example.com', '$2a$10$kQ28dYFwT/f9XLQ4PSl8q.zrkm1Z.PWx.EiOym9nnDOkRWZCFO12O', '456 Sycamore St', '67890', 4),
('Quinn', 'Garcia', 'quinngarcia', 'quinn.garcia@example.com', '$2a$10$JkdBJGcNZUAv5n.kM9KuXuv.c1PGlTceScZdxcoi/2N7mRRwcsJp6', '789 Aspen St', '78901', 5),
('Ruby', 'Martinez', 'rubymartinez', 'ruby.martinez@example.com', '$2a$10$By8yMtYgB9GRXtZr.LK.k/6Vx5m.TUekjRFe6tF.s28bVd06y/S.C', '123 Beech St', '89012', 6),
('Sam', 'Robinson', 'samrobinson', 'sam.robinson@example.com', '$2a$10$tkcFiO1fJX5mT/Odx2D7D.ZbFDIBwvjsG7HyTO2MdmUsyQ5pGn4CS', '456 Hemlock St', '90123', 7),
('Tina', 'Clark', 'tinaclark', 'tina.clark@example.com', '$2a$10$3/zCRpPiM.vgMszO2JQs6Or.TS0aRM1x8mPxsMHRRYTUzWFFemvLu', '789 Hickory St', '01234', 8),
('Uma', 'Lewis', 'umalewis', 'uma.lewis@example.com', '$2a$10$GuTfTjzxt1i4O3M1kVvbg.KXHhz7C.kg1GLY98JGB6Wh8JKiCeD7a', '123 Redwood St', '12345', 9),
('Victor', 'Walker', 'victorwalker', 'victor.walker@example.com', '$2a$10$T8rAlXGcSFXY6K8ImVvWJOTYczUUs/ZG7XwL.JxPf/x53R0IkWaNi', '456 Maple St', '23456', 10),
('Wendy', 'Hall', 'wendyhall', 'wendy.hall@example.com', '$2a$10$M9gz/9dlsT2CmjldFt3KZeqcKm3F0xL0tGZ93KwPP4.N3JaOgKJi2', '789 Fir St', '34567', 11),
('Xander', 'Young', 'xanderyoung', 'xander.young@example.com', '$2a$10$8i/kZOxn9cR8SQUmz6eVROZ.jmJZqD3Mdq.jMfMuVn5S0ksh3CBny', '123 Sequoia St', '45678', 12),
('Ylenia', 'Nelson', 'ylenia', 'ylenia.nelson@example.com', '$2a$10$HxBKl9Yz8D7km/1qJLcPzOvFg9hKmh1sXjWlU1Qx3Cj9VGT8p2gHe', '456 Ironwood St', '56789', 13);

INSERT INTO Creator (appUser, taxID)
VALUES
(23, 'IT12345678901'),
(24, 'IT12345678902'),
(25, 'GB123456789'),
(26, 'GB123456786');

INSERT INTO Event (creator, title, description, maxPeople, startBooking, endBooking, startEvent, endEvent, state, price)
VALUES 
(25, 'HealthTech Summit 2024', 'Exploring the intersection of healthcare and technology.', 550, '2024-12-01 09:00:00', '2024-12-30 18:00:00', '2025-01-05 09:00:00', '2025-01-07 18:00:00', 'Closed', 320.00),
(26, 'AI Ethics Conference 2024', 'Discussing the ethical implications of artificial intelligence.', 300, '2024-11-01 09:00:00', '2024-12-01 18:00:00', '2024-12-10 09:00:00', '2024-12-11 18:00:00', 'Closed', 150.00),
(23, 'Tech Conference 2024', 'A comprehensive technology conference covering AI, Blockchain, and Cloud Computing.', 500, '2024-09-01 09:00:00', '2024-10-01 18:00:00', '2024-10-10 09:00:00', '2024-10-12 18:00:00', 'Open', 300.00),
(23, 'Blockchain Summit 2024', 'An in-depth look at the future of blockchain technology.', 300, '2024-06-01 09:00:00', '2024-07-01 18:00:00', '2024-07-15 09:00:00', '2024-07-15 18:00:00', 'Finished', 150.00),
(24, 'AI Expo 2024', 'Explore the latest advancements in artificial intelligence.', 700, '2024-05-01 09:00:00', '2024-06-01 18:00:00', '2024-06-10 09:00:00', '2024-06-12 18:00:00', 'Finished', 250.00),
(25, 'Cybersecurity Conference 2024', 'A comprehensive event on modern cybersecurity challenges.', 400, '2024-03-01 09:00:00', '2024-04-01 18:00:00', '2024-04-05 09:00:00', '2024-04-07 18:00:00', 'Finished', 200.00),
(26, 'Data Science Summit 2024', 'An event focused on data analysis, machine learning, and big data.', 600, '2024-07-01 09:00:00', '2024-08-01 18:00:00', '2024-08-10 09:00:00', '2024-08-12 18:00:00', 'Finished', 180.00),
(23, 'Cloud Computing Expo 2024', 'The latest trends and innovations in cloud computing.', 350, '2024-02-01 09:00:00', '2024-03-01 18:00:00', '2024-03-10 09:00:00', '2024-03-12 18:00:00', 'Finished', 220.00),
(24, 'Virtual Reality Conference 2024', 'Explore the future of VR technology and its applications.', 500, '2024-11-01 09:00:00', '2024-12-01 18:00:00', '2024-12-10 09:00:00', '2024-12-12 18:00:00', 'Closed', 300.00),
(25, 'Quantum Computing Workshop 2024', 'An introductory workshop on quantum computing principles.', 250, '2024-10-15 09:00:00', '2024-11-15 18:00:00', '2024-11-20 09:00:00', '2024-11-21 18:00:00', 'Closed', 400.00),
(26, 'IoT Innovations Conference 2024', 'The latest developments in the Internet of Things (IoT).', 600, '2024-10-05 09:00:00', '2024-11-05 18:00:00', '2024-11-10 09:00:00', '2024-11-12 18:00:00', 'Closed', 350.00),
(23, 'Fintech Forum 2024', 'A deep dive into the world of financial technology.', 450, '2024-11-10 09:00:00', '2024-12-10 18:00:00', '2024-12-15 09:00:00', '2024-12-17 18:00:00', 'Closed', 275.00),
(24, 'Robotics Expo 2024', 'The cutting-edge of robotics and automation.', 800, '2024-10-20 09:00:00', '2024-11-20 18:00:00', '2024-11-25 09:00:00', '2024-11-27 18:00:00', 'Closed', 500.00),
(23, 'Smart Cities Conference 2024', 'The future of urban living with smart technology.', 700, '2024-12-15 09:00:00', '2025-01-15 18:00:00', '2025-01-20 09:00:00', '2025-01-22 18:00:00', 'Closed', 400.00),
(24, '5G Technology Symposium 2024', 'The advancements and applications of 5G networks.', 900, '2024-11-05 09:00:00', '2024-12-05 18:00:00', '2024-12-15 09:00:00', '2024-12-17 18:00:00', 'Closed', 450.00);

INSERT INTO OnlineEvent (event, url)
VALUES
(1, 'https://www.healthtechsummit.net'),
(2, 'https://www.aiec2024.com'),
(3, 'https://www.techconference2024.com'),
(4, 'https://www.blockchainsummit2024.org'),
(5, 'https://www.aiexpo2024.net');

INSERT INTO InPersonEvent(event, street, zipcode, city)
VALUES
(6, '456 Maple Avenue', '12345', 1),
(7, '789 Oak Lane', '23456', 2),
(8, '101 Pine Road', '34567', 3),
(9, '202 Elm Street', '45678', 4),
(10, '303 Cedar Boulevard', '56789', 5),
(11, '404 Birch Crescent', '67890', 6),
(12, '505 Willow Drive', '78901', 7),
(13, '606 Spruce Way', '89012', 8),
(14, '707 Fir Court', '90123', 9),
(15, '808 Redwood Terrace', '01234', 10);

INSERT INTO UserEvent (event, appUser)
VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 1),
(5, 1),
(1, 2),
(2, 2),
(3, 2),
(4, 2),
(5, 2),
(1, 3),
(2, 3),
(3, 3),
(4, 3),
(5, 3),
(1, 4),
(2, 4),
(3, 4),
(4, 4),
(5, 4),
(6, 5),
(7, 6),
(8, 7),
(9, 8),
(10, 9),
(11, 10),
(12, 11),
(13, 12),
(14, 13),
(15, 14);

INSERT INTO Feedback (event, appUser, stars, description, dateTime)
VALUES
(4, 1, 4, 'Blockchain Summit 2024 was a well-organized, insightful event, perfect for exploring healthcare innovation.', '2024-08-15 18:00:00'),
(4, 2, 5, 'A highly engaging summit that effectively showcased the future of blockchain technology.', '2024-07-20 18:00:00'),
(4, 3, 3, 'Blockchain Summit 2024 was a meticulously planned, enlightening event, ideal for delving into healthcare innovation.', '2024-07-15 21:00:00'),
(4, 4, 4, 'Blockchain Summit 2024 was a smoothly coordinated, informative event, excellent for exploring advancements in healthcare innovation.', '2024-07-23 18:00:00'),
(5, 1, 3, 'AI Expo 2024 offered deep insights into the crucial ethical challenges of artificial intelligence.', '2024-07-12 18:00:00'),
(5, 2, 3, 'AI Expo 2024 provided valuable perspectives on the pressing ethical dilemmas surrounding artificial intelligence.', '2024-08-12 18:00:00'),
(5, 3, 4, 'At AI Expo 2024, attendees gained profound understanding of the key ethical issues in AI development and deployment.', '2024-06-12 22:00:00'),
(5, 4, 5, 'AI Expo 2024 delivered comprehensive discussions on the vital ethical challenges shaping the future of artificial intelligence.', '2024-06-25 18:00:00');

commit;