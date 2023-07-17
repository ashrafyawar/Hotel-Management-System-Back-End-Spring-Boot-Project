-- use the below database for manipulation.
USE hotel_management_system_db;

-- add 4 different user types
INSERT INTO UserTypes (TypeID, TypeName)
VALUES (1, 'guest'), (2, 'administrator'), (3, 'receptionist'), (4, 'housekeeper');

-- add 3 different room types.
INSERT INTO RoomTypes (TypeID, TypeName,RoomCapacity)
VALUES (1, 'one bed',2), (2, 'two bed',4), (3, 'family-sized',7);

-- add booking status types.
INSERT INTO BookingStatusTypes (TypeID, TypeName)
VALUES (1, 'checked in'), (2, 'checked out'), (3, 'created'), (4,'Canceled'),(5,'isConfirmed');

-- add housekeepingschedule status types.
INSERT INTO HKScheduleStatusTypes(TypeID, TypeName)
VALUES (1, 'to do'), (2, 'in progress'), (3, 'done');

-- add 10 rooms into rooms table
INSERT INTO Rooms (RoomID, RoomName, RoomType) VALUES 
(1, 'Room 1', 1), 
(2, 'Room 2', 2), 
(3, 'Room 3', 3), 
(4, 'Room 4', 1), 
(5, 'Room 5', 1), 
(6, 'Room 6', 3), 
(7, 'Room 7', 2), 
(8, 'Room 8', 1), 
(9, 'Room 9', 3), 
(10, 'Room 10', 2);

-- add 10 different aminities
INSERT INTO Amenities (AmenityID, AmenityName) VALUES 
(1, 'Minibar'), 
(2, 'TV'), 
(3, 'WiFi'), 
(4, 'Pool'), 
(5, 'Gym'), 
(6, 'Spa'), 
(7, 'Bar'), 
(8, 'Restaurant'), 
(9, 'Concierge'), 
(10, 'Laundry service');

-- add 1 admin, 10 guests, 5 receptionists adn 5 housekeeping users.
INSERT INTO Users (UserID, Username, UserPassword, UserSalary, UserType) VALUES 
(1, 'admin', 'password', 50000, 2), 
(2, 'guest1', 'password', 0, 1), 
(3, 'guest2', 'password', 0, 1), 
(4, 'guest3', 'password', 0, 1), 
(5, 'guest4', 'password', 0, 1), 
(6, 'guest5', 'password', 0, 1), 
(7, 'guest6', 'password', 0, 1), 
(8, 'guest7', 'password', 0, 1), 
(9, 'guest8', 'password', 0, 1), 
(10, 'guest9', 'password', 0, 1), 
(11, 'guest10', 'password', 0, 1), 
(12, 'receptionist1', 'password', 30000, 3), 
(13, 'receptionist2', 'password', 30000, 3), 
(14, 'housekeeping1', 'password', 20000, 4), 
(15, 'housekeeping2', 'password', 20000, 4), 
(16, 'housekeeping3', 'password', 20000, 4), 
(17, 'housekeeping4', 'password', 20000, 4), 
(18, 'housekeeping5', 'password', 20000, 4);

-- add 5 different aminities for each of the rooms.
INSERT INTO RoomAmenities (RoomID, AmenityID) VALUES
(1, 1),(1, 2),(1, 3),(1, 4),(1, 5),
(2, 1),(2, 2),(2, 3),(2, 4),(2, 5),
(3, 1),(3, 2),(3, 3),(3, 4),(3, 5),
(4, 1),(4, 2),(4, 3),(4, 4),(4, 5),
(5, 1),(5, 2),(5, 3),(5, 4),(5, 5),
(6, 6),(6, 7),(6, 8),(6, 9),(6, 10),
(7, 6),(7, 7),(7, 8),(7, 9),(7, 10),
(8, 6),(8, 7),(8, 8),(8, 9),(8, 10),
(9, 6),(9, 7),(9, 8),(9, 9),(9, 10),
(10, 6),(10, 7),(10, 8),(10, 9),(10, 10);

-- add 5 bookings into bookings table
INSERT INTO Bookings (BookingID, RoomID, GuestID, StartDate, EndDate, Price, IsPayed, PeopleCount,Stat) VALUES
(1, 1, 1, '2022-01-01', '2022-01-05', 100, 0, 2, 3),
(2, 2, 2, '2022-02-01', '2022-02-05', 200, 0, 4, 3),
(3, 3, 3, '2022-03-01', '2022-03-05', 300, 0, 2, 3),
(4, 4, 4, '2022-04-01', '2022-04-05', 400, 0, 4, 3),
(5, 5, 5, '2022-05-01', '2022-05-05', 500, 0, 2, 3);

-- aminity endpoint
-- create aminities:
INSERT INTO Amenities (AmenityID, AmenityName)
VALUES (11, 'Books');

-- modify an aminity:
UPDATE Amenities
SET AmenityName = 'Computers'
WHERE AmenityID = 11;

-- delete aminity:
DELETE FROM Amenities
WHERE AmenityID = 11;

-- get all aminities:
SELECT * FROM Amenities;

-- room endpoint
-- create room:
INSERT INTO Rooms (RoomID, RoomName, RoomType)
VALUES (11, 'Room 11', 2);
-- add some aminities into the room:
INSERT INTO RoomAmenities (RoomID, AmenityID) VALUES (11, 1), (11, 2), (11, 3);

-- modify room:
UPDATE Rooms
SET RoomName = 'MyRoom'
WHERE RoomID = 11;

-- change room type:
UPDATE Rooms
SET RoomType = 2
WHERE RoomID = 11;

-- delete a room:
-- delete the room amenities records associated with the room
DELETE FROM RoomAmenities WHERE RoomID = 10;
-- delete the housekeeping schedules associated with the room
DELETE FROM HousekeepingSchedules WHERE RoomID = 10;
-- delete any bookings associated with the room
DELETE FROM Bookings WHERE RoomID = 10;
-- finally, delete the room itself
DELETE FROM Rooms WHERE RoomID = 10;

-- add aminity into a room
INSERT INTO RoomAmenities (RoomID, AmenityID) VALUES (11, 4);

-- remove aminity from a room
DELETE FROM RoomAmenities
WHERE RoomID = 10 AND AmenityID = 6;

-- get all rooms:
SELECT * FROM Rooms;

-- Get Rooms that are available between specified dates
SELECT Rooms.*
FROM Rooms
LEFT JOIN Bookings ON Rooms.RoomID = Bookings.RoomID AND Bookings.EndDate >= '2022-01-01' AND Bookings.StartDate <= '2022-01-02'
WHERE Bookings.BookingID IS NULL;

-- user endpoints:
-- create user:
INSERT INTO Users (UserID, Username, UserPassword, UserSalary, UserType)
VALUES (19, 'newuser', 'password', 10, 1);

-- modify username:
UPDATE Users
SET Username = 'new_username'
WHERE UserID = 19;

-- booking endpoints:
-- make booking
 	-- find first available room
 	INSERT INTO Bookings (BookingID, RoomID, GuestID, StartDate, EndDate, Price,IsPayed, PeopleCount)
	VALUES (6,8, 5, '2022-01-01', '2022-01-05', 1000, 0, 2);

-- change booking date:
UPDATE Bookings
SET StartDate = '2022-03-11', EndDate = '2022-01-12'
WHERE BookingID = 6;

-- change booking price:
UPDATE Bookings
SET Price = 1200
WHERE BookingID = 6;

-- get info of a booking
SELECT * FROM Bookings WHERE BookingID = 6;

-- get all bookings:
SELECT * FROM Bookings;

-- pay for a booking:
UPDATE Bookings
SET IsPayed = 1
WHERE BookingID = 6;

-- check in for a booking:
UPDATE Bookings
SET Stat = 1
WHERE BookingID = 6;

-- check out from a booking:
UPDATE Bookings
SET Stat = 2
WHERE BookingID = 6;

-- housekeeping endpoints:
-- schedule:
INSERT INTO HousekeepingSchedules (ScheduleID, RoomID, AssignedTo, TaskDate, Stat) 
VALUES (1, 1, 14, '2022-12-24', 1);

-- clean the room:
UPDATE HousekeepingSchedules
SET Stat = 3
WHERE RoomID = 1;

-- Get the list of rooms that require cleaning assigned to the housekeeper
SELECT * FROM HousekeepingSchedules
WHERE Stat = 1;


-- statistics endpoints:
-- Get the number of bookings for a given room
SELECT COUNT(BookingID) AS 'Number of Bookings'
FROM Bookings
WHERE RoomID = 5;

-- Get the ratio of (total bookings - cancelled_bookings) / total bookings
SELECT (COUNT(*) - SUM(CASE WHEN b.Stat = 4 THEN 1 ELSE 0 END)) / COUNT(*) AS 'Booking Ratio'
FROM Bookings b;

 -- get the average booking price for a room
SELECT AVG(Price) AS 'Average Booking Price'
FROM Bookings
WHERE RoomID = 5;


-- Get the user id of the user who stayed in the given room the most(in terms of booking count)
SELECT GuestID
FROM Bookings
WHERE RoomID = 3
GROUP BY GuestID
ORDER BY COUNT(*) DESC
LIMIT 1;

-- Get the user id of the housekeeper with most cleaned rooms
SELECT GuestID, SUM(DATEDIFF(EndDate, StartDate)) AS TotalDuration
FROM Bookings
WHERE RoomID = 3
GROUP BY GuestID
ORDER BY TotalDuration DESC
LIMIT 1;

-- Get the room details of rooms with at least K amenities
SELECT r.*
FROM Rooms r
INNER JOIN RoomAmenities ra ON r.RoomID = ra.RoomID
GROUP BY r.RoomID
HAVING COUNT(ra.AmenityID) >= 3;

-- Get the total amount of housekeeping tasks finished
SELECT COUNT(*) AS 'Total Tasks Finished'
FROM HousekeepingSchedules
WHERE Stat = 3;

-- Get the user id of the housekeeper with most pending task
SELECT AssignedTo, COUNT(*) AS 'Total Cleaned Rooms'
FROM HouseKeepingSchedules
WHERE Stat IN (1, 2)
GROUP BY AssignedTo
ORDER BY COUNT(*) DESC
LIMIT 1;

-- Get the room ids of the rooms with the highest number of different housekeepers
SELECT RoomID
FROM HousekeepingSchedules
GROUP BY RoomID
HAVING COUNT(DISTINCT AssignedTo) = (
  SELECT COUNT(DISTINCT AssignedTo)
  FROM HousekeepingSchedules
  GROUP BY RoomID
  ORDER BY COUNT(DISTINCT AssignedTo) DESC
  LIMIT 1
);

-- Get the average booking price for rooms which contains at least K amenities
SELECT AVG(b.Price)
FROM Bookings b
JOIN RoomAmenities ra ON ra.RoomID = b.RoomID
GROUP BY ra.RoomID
HAVING COUNT(DISTINCT ra.AmenityID) >= 4;

-- Get the average booking price of the rooms with the highest amount of bookings which lasted at least T days
SELECT AVG(b.Price) as 'Average Price'
FROM Bookings b
INNER JOIN Rooms r ON r.RoomID = b.RoomID
WHERE DATEDIFF(b.EndDate, b.StartDate) >= 3
GROUP BY r.RoomID
HAVING COUNT(b.BookingID) = (SELECT COUNT(BookingID) as 'Booking Count' FROM Bookings WHERE DATEDIFF(EndDate, StartDate) >= 3 ORDER BY 'Booking Count' DESC LIMIT 1);

-- Get the ids of amenities which are most common in the rooms
SELECT AmenityID
FROM RoomAmenities
GROUP BY AmenityID
ORDER BY COUNT(AmenityID) DESC;

-- Get the total price difference between the rooms which contain the most common amenity but not the second most common amenity and the rooms which does not contain the the most common amenity but contains the second most common amenity

	-- find the most and second most commont aminities as:
	SELECT AmenityID, COUNT(AmenityID) as 'Count'
	FROM RoomAmenities
	GROUP BY AmenityID
	ORDER BY Count DESC
	LIMIT 2;

SELECT SUM(CASE WHEN ra1.AmenityID = 5 AND ra2.AmenityID != 2 THEN b1.Price ELSE 0 END) - 
       SUM(CASE WHEN ra1.AmenityID != 5 AND ra2.AmenityID = 2 THEN b2.Price ELSE 0 END) AS total_price_difference
FROM Rooms r
JOIN RoomAmenities ra1 ON ra1.RoomID = r.RoomID
JOIN RoomAmenities ra2 ON ra2.RoomID = r.RoomID
JOIN Bookings b1 ON b1.RoomID = r.RoomID
JOIN Bookings b2 ON b2.RoomID = r.RoomID