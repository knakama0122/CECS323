--Table for Writing Groups
CREATE TABLE WritingGroup (
  groupName   VARCHAR(50) NOT NULL,
  headWriter  VARCHAR(50),
  yearFormed  INT,
  subject     VARCHAR(20),
  CONSTRAINT WritingGroup_pk PRIMARY KEY (groupName)
);

--table for Publishers
CREATE TABLE Publisher (
  publisherName     VARCHAR(50) NOT NULL,
  publisherAddress  VARCHAR(50),
  publisherPhone    VARCHAR(20),
  publisherEmail    VARCHAR(40),
  CONSTRAINT Publisher_pk PRIMARY KEY (publisherName)
);

--table for books
CREATE TABLE Book (
  groupName     VARCHAR(50) NOT NULL,
  bookTitle     VARCHAR(100) NOT NULL,
  publisherName VARCHAR(50) NOT NULL,
  yearPublished INT,
  numberPages   INT,
  CONSTRAINT Book_pk PRIMARY KEY (groupName, bookTitle),
  CONSTRAINT Book_fk1 FOREIGN KEY (groupName) REFERENCES WritingGroup (groupName),
  CONSTRAINT Book_fk2 FOREIGN KEY (publisherName) REFERENCES Publisher (publisherName),
  CONSTRAINT Book_ck UNIQUE (bookTitle, publisherName)
);

--data for writing groups
INSERT INTO WritingGroup (groupName, headWriter, yearFormed, subject) values
  ('Novelists', 'Jane Austen', 1900, 'Classic'),
  ('Playwrights', 'William Shakespeare', 1915, 'Drama'),
  ('The Historians', 'Howard Zinn', 1990, 'History'),
  ('Wanderers', 'J.R.R. Tolkien', 1940, 'Fantasy'),
  ('Scientists', 'Brian Greene', 2000, 'Science'),
  ('Far Out', 'Douglas Adams', 1980, 'Science Fiction');
--data for publishers
INSERT INTO Publisher (publisherName, publisherAddress, publisherPhone, publisherEmail) values
  ('Pan Books', 'Level 25, 1 Market Street', '61292859100', 'pan.reception@macmillan.com.au'),
  ('W.W. Norton & Company', '500 Fifth Avenue', '2154351905', 'mleary@wwnorton.com'),
  ('Allen & Unwin', 'Osmond House 26-27 Boswell Street', '4402072691610', 'weborders@allenandunwin.com'),
  ('HarperCollins' , '195 Broadway' , '2122077000', 'APsupport@harpercollins.com'),
  ('Oxford University Press', '2001 Evans Road', '18004459714', 'custserv.us@oup.com'),
  ('Queensbridge Publishing', 'Vernon Blvd & 41st Ave', '3239638150', 'info@queensbridgepublishing.com');

--data for books
INSERT INTO Book (groupName, bookTitle, publisherName, yearPublished, numberPages) values
  ('Novelists', 'The Little Prince', 'Pan Books', 1943, 96),
  ('Novelists', 'Pride and Prejudice', 'Pan Books', 1813, 272),
  ('Playwrights', 'Romeo and Juliet', 'W.W. Norton & Company', 1595, 282),
  ('The Historians', 'A Peoples History of the United States', 'Allen & Unwin', 1980, 784),
  ('The Historians', 'The Diary of a Young Girl', 'Allen & Unwin', 1947, 283),
  ('Wanderers', 'The Fellowship of the Ring', 'HarperCollins', 1954, 432),
  ('Wanderers', 'Harry Potter and the Sorcerers Stone', 'HarperCollins', 1997, 309),
  ('Scientists', 'A Brief History of Time', 'Oxford University Press', 1988, 212),
  ('Scientists', 'Pale Blue Dot: A Vision of the Human Future in Space', 'Oxford University Press', 1994, 384),
  ('Wanderers', 'A Game of Thrones', 'HarperCollins', 1996, 720),
  ('Far Out', 'The Hitchhikers Guide to the Galaxy', 'Queensbridge Publishing', 1979, 224),
  ('Far Out', 'Ready Player One', 'Queensbridge Publishing', 2011, 400),
  ('Far Out', 'The Giver', 'Queensbridge Publishing', 1993, 240),
  ('Far Out', 'I Want To Eat Your Pancreas', 'Pan Books', 2017, 329);

Delete from book;
Delete from publisher;
Delete from writinggroup;
drop table book;
drop table publisher;
drop table writinggroup;
