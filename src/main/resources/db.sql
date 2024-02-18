CREATE TABLE member(
    "no" number PRIMARY KEY,
    "id" varchar(13) not null,
    pw varchar(20) not null,
    nickname varchar(8) not null,
    interest varchar(40) not null,
    "location" varchar(40) not null,
    introduce varchar(50) not null,
    mail varchar(100) not null,
    review_board varchar(50) not null,
    join_club varchar(50) not null
);

CREATE TABLE club(
    "no" number PRIMARY KEY ,
    "name" varchar(15) not null,
    introduce varchar2(1500) not null,
    members varchar2(1500) not null,
    meetings varchar2(1500),
    read_books varchar2(1500),
    report_board blob,
    start_date date not null
);

CREATE TABLE meeting(
    "no" number PRIMARY KEY ,
    club_no number not null,
    book_title varchar(50) not null,
    join_member varchar(1500) not null,
    "online" char(1) not null,
    meeting_time date not null
);

CREATE TABLE review(
    "no" number PRIMARY KEY ,
    title varchar(50) not null,
    "content" blob not null,
    write_date date not null,
    writer varchar(13) not null,
    book varchar(13)
);



