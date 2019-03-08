CREATE TABLE WritingGroup (
  groupName 	VARCHAR(50) NOT NULL,
  headWriter 	VARCHAR(50),
  yearFormed  VARCHAR(4),
  subject     VARCHAR(20),
  CONSTRAINT WritingGroup_pk PRIMARY KEY (groupName)
);

CREATE TABLE Publisher (
  publisherName     VARCHAR(50) NOT NULL,
  publisherAddress  VARCHAR(50),
  publisherPhone    VARCHAR(20),
  publisherEmail    VARCHAR(40),
  CONSTRAINT Publisher_pk PRIMARY KEY (publisherName)
);

CREATE TABLE Book (
  groupName     VARCHAR(50) NOT NULL,
  bookTitle     VARCHAR(50) NOT NULL,
  publisherName VARCHAR(50) NOT NULL,
  yearPublished VARCHAR(4),
  numberPages   VARCHAR(4),
  CONSTRAINT Book_pk PRIMARY KEY (groupName, bookTitle),
  CONSTRAINT Book_fk1 FOREIGN KEY (groupName) REFERENCES WritingGroup (groupName),
  CONSTRAINT Book_fk2 FOREIGN KEY (publisherName) REFERENCES Publisher (publisherName),
  CONSTRAINT Book_ck UNIQUE (bookTitle, publisherName)
);
