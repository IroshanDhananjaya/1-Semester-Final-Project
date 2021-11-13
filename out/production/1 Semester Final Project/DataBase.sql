DROP DATABASE IF EXISTS School;
CREATE DATABASE IF NOT EXISTS School;
SHOW DATABASES;
USE School;

DROP TABLE IF EXISTS Hostal;
CREATE TABLE Hostal(
    HostalID VARCHAR (10),
    HostalName VARCHAR (15),
    HostalType VARCHAR (10),
    NoOfRoom INT,
    HostalContact VARCHAR (15),
    CONSTRAINT PRIMARY KEY (HostalID)
);

SHOW TABLES;
DESCRIBE Hostal;

DROP TABLE IF EXISTS Room;
CREATE TABLE Room(
    RoomID VARCHAR (10),
    HostalID VARCHAR (10),
    RoomDetails VARCHAR (25),
    NoOfBed INT,
    CONSTRAINT PRIMARY KEY (RoomID),
    CONSTRAINT FOREIGN KEY (HostalID) REFERENCES Hostal(HostalID) ON DELETE CASCADE ON UPDATE CASCADE
);

SHOW TABLES;
DESCRIBE Room;

DROP TABLE IF EXISTS Student;
CREATE TABLE Student(
    StudentIndexNumber VARCHAR (10),
    StudentFirstName VARCHAR (20),
    StudentLastName VARCHAR (20),
    Gender VARCHAR (6),
    Religion VARCHAR (15),
    BirthDay VARCHAR (20),
    MotherFirstName VARCHAR (20),
    MotherLastName VARCHAR (20),
    FatherFirstName VARCHAR (20),
    FatherLastName VARCHAR (20),
    FartherOccupation VARCHAR (15),
    StudentAddress VARCHAR (50),
    Contact VARCHAR (13),
    CONSTRAINT PRIMARY KEY (StudentIndexNumber)
);

SHOW TABLES;
DESCRIBE Student;

DROP TABLE IF EXISTS `Student Room Details`;
CREATE TABLE `Student Room Details`(
     RoomID VARCHAR (10),
    StudentIndexNumber VARCHAR (10),
    DateOFEntry VARCHAR (15),
    CONSTRAINT PRIMARY KEY (StudentIndexNumber),
    CONSTRAINT FOREIGN KEY (RoomID) REFERENCES Room (RoomID) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (StudentIndexNumber) REFERENCES Student (StudentIndexNumber) ON DELETE CASCADE ON UPDATE CASCADE
);

SHOW TABLES;
DESCRIBE `Student Room Details`;

DROP TABLE IF EXISTS `Section`;
CREATE TABLE `Section`(
    SectionID VARCHAR (10),
    SectionName VARCHAR (20),
    CONSTRAINT PRIMARY KEY (SectionID)
);

SHOW TABLES;
DESCRIBE `Section`;

DROP TABLE IF EXISTS Class;
CREATE TABLE Class(
    ClassID VARCHAR (10),
    ClassName VARCHAR (20),
    SectionID VARCHAR (10),
    ClassDetails VARCHAR (25),
    CONSTRAINT PRIMARY KEY (ClassID),
    CONSTRAINT FOREIGN KEY (SectionID) REFERENCES `Section`(SectionID) ON DELETE CASCADE ON UPDATE CASCADE
);

SHOW TABLES;
DESCRIBE Class;

DROP TABLE IF EXISTS `Student Rejistration`;
CREATE TABLE `Student Rejistration`(
    ClassID VARCHAR (10),
    StudentIndexNumber VARCHAR (10),
    DateOfRejister VARCHAR (25),
   CONSTRAINT PRIMARY KEY (StudentIndexNumber),
   CONSTRAINT FOREIGN KEY (ClassID) REFERENCES Class (ClassID) ON DELETE CASCADE ON UPDATE CASCADE,
   CONSTRAINT FOREIGN KEY (StudentIndexNumber) REFERENCES Student (StudentIndexNumber) ON DELETE CASCADE ON UPDATE CASCADE
);

DESCRIBE `Student Rejistration`;

DROP TABLE IF EXISTS Teachers;
CREATE TABLE Teachers(
    TeacherID VARCHAR (10),
    TeacherName VARCHAR (20),
    TeacherAddress VARCHAR (50),
    Gender VARCHAR (6),
    Religion VARCHAR (10),
    Contact VARCHAR (13),
    CONSTRAINT PRIMARY KEY (TeacherID)
);


SHOW TABLES;
DESCRIBE Teachers;

DROP TABLE IF EXISTS Subject;
CREATE TABLE Subject(
    SubjectID VARCHAR (15),
    SubjectName VARCHAR (15),
    SubjectMeadium VARCHAR (10),
    CONSTRAINT PRIMARY KEY (SubjectID)
);

SHOW TABLES;
DESCRIBE Subject;

DROP TABLE IF EXISTS `Teacher and Subject`;
CREATE TABLE `Teacher and Subject`(
    TeacherID VARCHAR (10),
    SubjectID VARCHAR (15),
    CONSTRAINT PRIMARY KEY (SubjectID,TeacherID),
    CONSTRAINT FOREIGN KEY (TeacherID) REFERENCES Teachers(TeacherID) ON DELETE CASCADE ON UPDATE CASCADE ,
    CONSTRAINT FOREIGN KEY (SubjectID) REFERENCES Subject(SubjectID) ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES;
DESCRIBE `Teacher and Subject`;

DROP TABLE IF EXISTS `Teacher and Class`;
CREATE TABLE `Teacher and Class`(
    TeacherID VARCHAR (10),
    ClassID VARCHAR (10),
    CONSTRAINT PRIMARY KEY (ClassID,TeacherID),
    CONSTRAINT FOREIGN KEY (TeacherID) REFERENCES Teachers(TeacherID) ON DELETE CASCADE ON UPDATE CASCADE ,
    CONSTRAINT FOREIGN KEY (ClassID) REFERENCES Class(ClassID) ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES;
DESCRIBE `Teacher and Class`;

DROP TABLE IF EXISTS Sports;
CREATE TABLE Sports(
    SportID VARCHAR (10),
    SportName VARCHAR (20),
    SportType VARCHAR (10),
    CONSTRAINT PRIMARY KEY (SportID)

);

SHOW TABLES;
DESCRIBE Sports;

DROP TABLE IF EXISTS `Student and Sports`;
CREATE TABLE `Student and Sports`(
    SportID VARCHAR (10),
    StudentIndexNumber VARCHAR (10),
    CONSTRAINT PRIMARY KEY (SportID,StudentIndexNumber),
    CONSTRAINT FOREIGN KEY (SportID) REFERENCES Sports(SportID) ON DELETE CASCADE ON UPDATE CASCADE ,
    CONSTRAINT FOREIGN KEY (StudentIndexNumber) REFERENCES Student(StudentIndexNumber) ON DELETE CASCADE ON UPDATE CASCADE
);

SHOW TABLES;
DESCRIBE `Student and Sports`;

DROP TABLE IF EXISTS Exam;
CREATE TABLE Exam (
    ExamNumber VARCHAR (15),
    ExamName VARCHAR (20),
    CONSTRAINT PRIMARY KEY (ExamNumber)
);
DESCRIBE Exam;

DROP TABLE IF EXISTS `Student Exam Details`;
CREATE TABLE `Student Exam Details`(
    StudentIndexNumber VARCHAR (10),
    ExamNumber VARCHAR (15),
    Marks VARCHAR (10),
    ResultType VARCHAR (5),
    DateHeld VARCHAR (13),
    CONSTRAINT PRIMARY KEY (StudentIndexNumber,ExamNumber),
    CONSTRAINT FOREIGN KEY (StudentIndexNumber) REFERENCES Student(StudentIndexNumber) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (ExamNumber) REFERENCES Exam(ExamNumber) ON DELETE CASCADE ON UPDATE CASCADE
);
DESCRIBE `Student Exam Details`;

DROP TABLE IF EXISTS `User`;
CREATE TABLE `User`(
    userNIC VARCHAR (15),
    userName VARCHAR (20),
    userPW VARCHAR (150),
    userType VARCHAR (10),
    CONSTRAINT PRIMARY KEY (userNIC)
);
INSERT INTO `User` VALUES('200108202209','Iroshan ',md5('1234'),'admin');
INSERT INTO `User` VALUES('200108323906','Dhananjaya',md5('4321'),'Teacher');

