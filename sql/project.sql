USE prj2;

CREATE TABLE board
(
    id       INT PRIMARY KEY AUTO_INCREMENT,
    title    VARCHAR(100)  NOT NULL,
    content  VARCHAR(1000) NOT NULL,
    writer   VARCHAR(100)  NOT NULL,
    inserted DATETIME      NOT NULL DEFAULT NOW()
);

SELECT *
FROM board
ORDER BY id DESC;

#member table 만들기
CREATE TABLE member
(
    id        INT PRIMARY KEY AUTO_INCREMENT,
    email     VARCHAR(100) NOT NULL UNIQUE,
    password  VARCHAR(100) NOT NULL,
    nick_name VARCHAR(100) NOT NULL UNIQUE,
    inserted  DATETIME     NOT NULL DEFAULT NOW()
);

SELECT *
FROM member;

# board table 수정
# writer column
# member_id column reference member(id)

ALTER TABLE board
    DROP COLUMN writer;
DESC board;
ALTER TABLE board
    ADD COLUMN member_id INT REFERENCES member (id) AFTER content;

UPDATE board
SET member_id = (SELECT id FROM member ORDER BY id DESC LIMIT 1)
WHERE id > 0;

ALTER TABLE board
    MODIFY COLUMN member_id INT NOT NULL;

SELECT *
FROM member
WHERE email = 'qq@qq';

DELETE
FROM board
WHERE member_id = 9;
DELETE
FROM member
WHERE email = 'qq@qq';

DESC board;

SELECT *
FROM board;

USE prj2;

# 게시물 여러개 입력
INSERT INTO board(title, content, member_id)
SELECT title, content, member_id
FROM board;

SELECT COUNT(*)
FROM board;

SELECT *
FROM member;

UPDATE member
SET nick_name='abcd'
WHERE id = 21;
UPDATE member
SET nick_name='efg'
WHERE id = 22;

UPDATE board
SET member_id = 21
WHERE id % 2 = 0;
UPDATE board
SET member_id=22
WHERE id % 2 = 1;

UPDATE board
SET title  = 'abc def',
    content= 'ghi jkl'
WHERE id % 3 = 0;
UPDATE board
SET title  = 'mno pqr',
    content= 'stu vwx'
WHERE id % 3 = 1;
UPDATE board
SET title  = 'yz1 234',
    content= '567 890'
WHERE id % 3 = 2;

DESC board;

CREATE TABLE board_file
(
    board_id INT          NOT NULL REFERENCES board (id),
    name     VARCHAR(500) NOT NULL,
    PRIMARY KEY (board_id, name)
);

SELECT *
FROM board_file
WHERE board_id = 554;

CREATE TABLE authority
(
    member_id INT         NOT NULL REFERENCES member (id),
    name      VARCHAR(20) NOT NULL,
    PRIMARY KEY (member_id, name)
);

INSERT INTO authority (member_id, name)
VALUES (25, 'admin');

SELECT *
FROM authority;

SELECT *
FROM member;

#board like table create
CREATE TABLE board_like
(
    board_id  INT REFERENCES board (id),
    member_id INT REFERENCES member (id),
    PRIMARY KEY (board_id, member_id)
);

SELECT *
FROM board_like;

SELECT b.id, COUNT(DISTINCT f.name), COUNT(DISTINCT l.member_id)
FROM board b
         JOIN member m ON b.member_id = m.id
         LEFT JOIN board_file f ON b.id = f.board_id
         LEFT JOIN board_like l ON b.id = l.board_id
WHERE b.id = 2;

#댓글 테이블
CREATE TABLE comment
(
    id        INT PRIMARY KEY AUTO_INCREMENT,
    board_id  INT          NOT NULL REFERENCES board (id),
    member_id INT          NOT NULL REFERENCES member (id),
    comment   VARCHAR(500) NOT NULL,
    inserted  DATETIME     NOT NULL DEFAULT NOW()
);

SELECT *
FROM board;
SELECT *
FROM member;

SELECT *
FROM comment;

INSERT INTO board
    (title, content, member_id)
SELECT title, content, member_id
FROM board;