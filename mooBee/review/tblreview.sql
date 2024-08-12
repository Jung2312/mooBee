CREATE TABLE tblreview (
reviewNum INT PRIMARY KEY,
userId VARCHAR(30),
title VARCHAR(20),
content TEXT(500),
docid INT,
reviewDate DATE DEFAULT (NOW()),
scope INT,
recommend INT,
FOREIGN KEY (userId) REFERENCES tblUser(userId),
FOREIGN KEY (docid) REFERENCES tblMovie(docid)
);