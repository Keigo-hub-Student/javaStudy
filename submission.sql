

USE StudentManagement;

CREATE TABLE IF NOT EXISTS students (
    ID varchar(100) NOT NULL,
    name varchar(50),
    nameKana varchar(100),
    nickname varchar(100),
    mailAddress varchar(200),
    address varchar(200),
    age int,
    gender varchar(100),
    PRIMARY KEY (ID)
);

CREATE TABLE IF NOT EXISTS student_courses (
    course_ID varchar(100),
    student_ID varchar(100),
    course_name varchar(100),
    registrationDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expirationDate TIMESTAMP AS (registrationDate + INTERVAL 3 MONTH) STORED,
    CONSTRAINT FK_STUDENT
        FOREIGN KEY (student_ID)
        REFERENCES students(ID)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

INSERT IGNORE INTO students
(ID, name, nameKana, nickname, mailAddress, address, age, gender)
VALUES
('00001','田中太郎','たなかたろう','タナタロ','ttttttt@gmail.com','東京都港区',23,'男'),
('00002','櫻井美穂','さくらいみほ','ミホミホ','uuuuuuuu@gmail.com','東京都世田谷区',33,'女'),
('00003','槇原希','まきはらのぞみ','マキノ','eeeeeeee@gmail.com','東京都渋谷区',19,'女');

INSERT IGNORE INTO student_courses
(course_ID, student_ID, course_name)
VALUES
('a00001','00001','JAVA'),
('a00002','00002','JAVA'),
('a00003','00003','AWS');
