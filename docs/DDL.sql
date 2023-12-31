USE hotel_management_system_db;

CREATE TABLE IF NOT EXISTS UserTypes (
TypeID INT PRIMARY KEY AUTO_INCREMENT,
TypeName VARCHAR(255) UNIQUE
);


CREATE TABLE IF NOT EXISTS  RoomTypes (
TypeID INT PRIMARY KEY AUTO_INCREMENT,
TypeName VARCHAR(255) UNIQUE,
RoomCapacity INT UNIQUE
);

CREATE TABLE IF NOT EXISTS  BookingStatusTypes (
TypeID INT PRIMARY KEY AUTO_INCREMENT,
TypeName VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS  HKScheduleStatusTypes (
TypeID INT PRIMARY KEY AUTO_INCREMENT,
TypeName VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS  Amenities (
AmenityID INT PRIMARY KEY AUTO_INCREMENT,
AmenityName VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS  Users (
UserID INT PRIMARY KEY AUTO_INCREMENT,
Username VARCHAR(255),
UserPassword VARCHAR(255),
UserSalary INT,
UserType INT,
FOREIGN KEY (UserType) REFERENCES UserTypes(TypeID) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS  Rooms (
RoomID INT PRIMARY KEY AUTO_INCREMENT,
RoomName VARCHAR(255) UNIQUE,
RoomType INT,
FOREIGN KEY (RoomType) REFERENCES RoomTypes(TypeID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS  Bookings (
BookingID INT PRIMARY KEY AUTO_INCREMENT,
RoomID INT,
GuestID INT,
StartDate DATE,
EndDate DATE,
Price INT,
IsPayed BOOLEAN,
PeopleCount INT,
Stat INT,
FOREIGN KEY (RoomID) REFERENCES Rooms(RoomID)  ON DELETE CASCADE,
FOREIGN KEY (GuestID) REFERENCES Users(UserID) ON DELETE CASCADE,
FOREIGN KEY (Stat) REFERENCES BookingStatusTypes(TypeID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS  HousekeepingSchedules (
ScheduleID INT PRIMARY KEY AUTO_INCREMENT,
RoomID INT,
AssignedTo INT,
TaskDate DATE,
Stat INT,
FOREIGN KEY (RoomID) REFERENCES Rooms(RoomID) ON DELETE CASCADE,
FOREIGN KEY (Stat) REFERENCES HKScheduleStatusTypes(TypeID) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS  RoomAmenities (
RoomID INT,
AmenityID INT,
PRIMARY KEY (RoomID, AmenityID),
FOREIGN KEY (RoomID) REFERENCES Rooms(RoomID) ON DELETE CASCADE,
FOREIGN KEY (AmenityID) REFERENCES Amenities(AmenityID) ON DELETE CASCADE
);