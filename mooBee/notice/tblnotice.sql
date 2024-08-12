CREATE TABLE tblnotice (
noticeNum INT PRIMARY KEY,
title VARCHAR(20) NOT NULL,
content TEXT(500) NOT NULL,
noticeImg VARCHAR(255),
noticeDate DATE DEFAULT(NOW())
);