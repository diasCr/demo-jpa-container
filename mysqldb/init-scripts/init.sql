CREATE DATABASE IF NOT EXISTS dashboard;

GRANT ALL ON `dashboard`.* TO 'mddashboard'@'%' IDENTIFIED BY 'geheim';

CREATE TABLE dashboard.transmission(
	ID int NOT NULL AUTO_INCREMENT,
	APPLIANCE_ID int NOT NULL,
    STATUS int NOT NULL,
    SEND_DATE TIMESTAMP NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE dashboard.organization(
	ID int NOT NULL AUTO_INCREMENT,
    DISPLAY_NAME varchar(50) NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE dashboard.user(
	ID int NOT NULL AUTO_INCREMENT,
    LASTNAME varchar(255) NOT NULL,
    FIRSTNAME varchar(255),
    ORGANIZATION_ID int,
    PRIMARY KEY (ID),
	FOREIGN KEY (ORGANIZATION_ID) REFERENCES dashboard.organization(ID)
);

CREATE TABLE dashboard.appliance(
	ID int NOT NULL AUTO_INCREMENT,
    STATUS int NOT NULL,
    ORGANIZATION_ID int NOT NULL,
    PRIMARY KEY (ID),
	FOREIGN KEY (ORGANIZATION_ID) REFERENCES dashboard.organization(ID)
);

INSERT INTO dashboard.organization(DISPLAY_NAME)
VALUES ('MediData AG');

INSERT INTO dashboard.user(LASTNAME, FIRSTNAME, ORGANIZATION_ID)
VALUES ('TestLastname', 'TestFirstname', 1);

INSERT INTO dashboard.appliance(STATUS, ORGANIZATION_ID) 
VALUES (0, 1), (0,1), (1,1);