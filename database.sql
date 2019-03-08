CREATE TABLE WritingGroup (
    groupName 	VARCHAR(50) NOT NULL,
    headWriter 	VARCHAR(50),
    yearFormed 	VARCHAR(4),
    subject 	VARCHAR(20),
    CONSTRAINT WritingGroup_pk PRIMARY KEY (groupName)
);

CREATE TABLE Publisher (
  publisherName VARCHAR(50) NOT NULL,
  publisherAddress VARCHAR(50),
  publisherPhone VARCHAR(20),
  publisherEmail VARCHAR(40),
  CONSTRAINT Publisher_pk PRIMARY KEY (publisherName)
);
