drop table club;
drop table member;
drop table meeting;
drop table review;
drop table "COMMENT";

CREATE TABLE member(
    "NO" number PRIMARY KEY,
    "ID" varchar(13) not null,
    pw varchar(20) not null,
    nickname varchar(8) not null,
    interest varchar(40) not null,
    "LOCATION" varchar(40) not null,
    introduce varchar(50) not null,
    mail varchar(100) not null,
    review_board varchar(50) not null,
    join_club varchar(50) not null
);

CREATE TABLE club(
    "NO" number PRIMARY KEY ,
    "NAME" varchar(15) not null,
    introduce varchar2(1500) not null,
    members varchar2(1500) not null,
    meetings varchar2(1500),
    read_books varchar2(1500),
    report_board blob,
    start_date date not null,
    profile varchar(300) not null,
    location varchar(150) not null
);

CREATE TABLE meeting(
    "NO" number PRIMARY KEY ,
    club_no number not null,
    book_title varchar(50) not null,
    join_member varchar(1500) not null,
    "ONLINE" char(1) not null,
    meeting_date date not null,
    meeting_time date not null
);

CREATE TABLE review(
    "NO" number PRIMARY KEY ,
    title varchar(50) not null,
    "CONTENT" blob not null,
    write_date date not null,
    writer varchar(13) not null,
    book varchar(13)
);

CREATE TABLE "COMMENT"(
    board_no NUMBER not null,
    writer_id VARCHAR(15) NOT NULL,
    "DATE" DATE NOT NULL,
    "TIME" date NOT NULL,
    content varchar(500) not null,
    CONSTRAINT pk_comment PRIMARY KEY (board_no, writer_id, "DATE", "TIME")
);