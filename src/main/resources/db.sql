drop table club;
drop table member;
drop table meeting;
drop table review;
drop table "comment";

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
    report_board varchar(1500),
    start_date date not null,
    profile varchar(300) not null,
    location varchar(150) not null
);

CREATE TABLE meeting(
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

insert into club (no, name, introduce, members, meetings, read_books, report_board, start_date, profile, location) values (1, 'Emlyn Rube', 'Teal', '1, 5, 9, 12, 17, 22, 28, 33, 39, 44', '20, 25, 30, 35, 40, 45', '978-0-306-40615-7, 978-3-16-148410-0, 978-1-234-56789-0, 978-4-567-89012-3, 978-9-876-54321-0, 978-6-543-21098-7, 978-2-109-87654-3, 978-8-765-43210-9, 978-5-432-10987-6, 978-7-654-32109-8', '1, 5, 10', TO_DATE('3/23/2023', 'MM/DD/YYYY'), 'http://dummyimage.com/213x100.png/cc0000/ffffff', 'PO Box 72035');
insert into club (no, name, introduce, members, meetings, read_books, report_board, start_date, profile, location) values (2, 'Barrie Brd', 'Maroon', '1, 5, 9, 12, 17, 22, 28, 33, 39, 44', '20, 25, 30, 35, 40, 45', '978-0-306-40615-7, 978-3-16-148410-0, 978-1-234-56789-0, 978-4-567-89012-3, 978-9-876-54321-0, 978-6-543-21098-7, 978-2-109-87654-3, 978-8-765-43210-9, 978-5-432-10987-6, 978-7-654-32109-8', '1, 5, 10', TO_DATE('2/24/2023', 'MM/DD/YYYY'), 'http://dummyimage.com/175x100.png/ff4444/ffffff', 'Apt 738');
insert into club (no, name, introduce, members, meetings, read_books, report_board, start_date, profile, location) values (3, 'Johnna dicks', 'Fuscia', '1, 5, 9, 12, 17, 22, 28, 33, 39, 44', '20, 25, 30, 35, 40, 45', '978-0-306-40615-7, 978-3-16-148410-0, 978-1-234-56789-0, 978-4-567-89012-3, 978-9-876-54321-0, 978-6-543-21098-7, 978-2-109-87654-3, 978-8-765-43210-9, 978-5-432-10987-6, 978-7-654-32109-8', '1, 5, 10', TO_DATE('1/26/2024', 'MM/DD/YYYY'), 'http://dummyimage.com/232x100.png/5fa2dd/ffffff', 'Suite 92');
insert into club (no, name, introduce, members, meetings, read_books, report_board, start_date, profile, location) values (4, 'Rutle eghton', 'Puce', '1, 5, 9, 12, 17, 22, 28, 33, 39, 44', '20, 25, 30, 35, 40, 45', '978-0-306-40615-7, 978-3-16-148410-0, 978-1-234-56789-0, 978-4-567-89012-3, 978-9-876-54321-0, 978-6-543-21098-7, 978-2-109-87654-3, 978-8-765-43210-9, 978-5-432-10987-6, 978-7-654-32109-8', '1, 5, 10', TO_DATE('8/5/2023', 'MM/DD/YYYY'), 'http://dummyimage.com/249x100.png/ff4444/ffffff', 'PO Box 67897');
insert into club (no, name, introduce, members, meetings, read_books, report_board, start_date, profile, location) values (5, 'Muffin Bnston', 'Indigo', '1, 5, 9, 12, 17, 22, 28, 33, 39, 44', '20, 25, 30, 35, 40, 45', '978-0-306-40615-7, 978-3-16-148410-0, 978-1-234-56789-0, 978-4-567-89012-3, 978-9-876-54321-0, 978-6-543-21098-7, 978-2-109-87654-3, 978-8-765-43210-9, 978-5-432-10987-6, 978-7-654-32109-8', '1, 5, 10', TO_DATE('1/10/2024', 'MM/DD/YYYY'), 'http://dummyimage.com/131x100.png/cc0000/ffffff', 'Suite 34');

commit;