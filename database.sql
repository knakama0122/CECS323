CREATE TABLE Publisher (
  publisherName VARCHAR(50) NOT NULL,
  publisherAddress VARCHAR(50),
  publisherPhone VARCHAR(20),
  publisherEmail VARCHAR(40),
  CONSTRAINT Publisher_pk PRIMARY KEY (publisherName)
);
