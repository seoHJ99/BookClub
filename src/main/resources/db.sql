drop table club;
drop table member;
drop table meeting;
drop table review;
drop table "COMMENT";

CREATE TABLE member(
    "NO" number PRIMARY KEY,
    "ID" varchar(13) not null,
    pw varchar(20) not null,
    "PROFILE" varchar(300) not null,
    nickname varchar(8) not null,
    "LOCATION" varchar(40) not null,
    introduce varchar(50) not null,
    mail varchar(100) not null,
    review_board varchar(50),
    join_club varchar(50)
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
    "NO" number not null,
    club_no number not null,
    book_title varchar(50) not null,
    join_member varchar(1500) not null,
    is_online char(1) not null,
    meeting_date date not null,
    meeting_time date not null,
    CONSTRAINT pk_meeting PRIMARY KEY(club_no, "NO")
);

CREATE TABLE review(
    "NO" number PRIMARY KEY ,
    title varchar(50) not null,
    "CONTENT" varchar(4000) not null,
    write_date date not null,
    writer varchar(20) not null,
    book varchar(13)
);

CREATE TABLE REVIEW_COMMENT(
    board_no NUMBER not null,
    writer_id VARCHAR(15) NOT NULL,
    write_date date NOT NULL,
    write_time date NOT NULL,
    content varchar(500) not null,
    CONSTRAINT pk_comment PRIMARY KEY (board_no, writer_id, write_date, write_time)
);

-- 클럽 데이터
insert into club (no, name, introduce, members, meetings, read_books, report_board, start_date, profile, location) values (1, 'Emlyn Ruberry', 'Teal', '1, 5, 9, 12, 17, 22, 28, 33, 39, 44', '20, 25, 30, 35, 40, 45', '978-0-306-40615-7, 978-3-16-148410-0, 978-1-234-56789-0, 978-4-567-89012-3, 978-9-876-54321-0, 978-6-543-21098-7, 978-2-109-87654-3, 978-8-765-43210-9, 978-5-432-10987-6, 978-7-654-32109-8', '1, 5, 10', TO_DATE('3/23/2023', 'MM/DD/YYYY'), 'http://dummyimage.com/213x100.png/cc0000/ffffff', 'PO Box 72035');
insert into club (no, name, introduce, members, meetings, read_books, report_board, start_date, profile, location) values (2, 'Barrie Bradd', 'Maroon', '1, 5, 9, 12, 17, 22, 28, 33, 39, 44', '20, 25, 30, 35, 40, 45', '978-0-306-40615-7, 978-3-16-148410-0, 978-1-234-56789-0, 978-4-567-89012-3, 978-9-876-54321-0, 978-6-543-21098-7, 978-2-109-87654-3, 978-8-765-43210-9, 978-5-432-10987-6, 978-7-654-32109-8', '1, 5, 10', TO_DATE('2/24/2023', 'MM/DD/YYYY'), 'http://dummyimage.com/175x100.png/ff4444/ffffff', 'Apt 738');
insert into club (no, name, introduce, members, meetings, read_books, report_board, start_date, profile, location) values (3, 'Johnna Maddicks', 'Fuscia', '1, 5, 9, 12, 17, 22, 28, 33, 39, 44', '20, 25, 30, 35, 40, 45', '978-0-306-40615-7, 978-3-16-148410-0, 978-1-234-56789-0, 978-4-567-89012-3, 978-9-876-54321-0, 978-6-543-21098-7, 978-2-109-87654-3, 978-8-765-43210-9, 978-5-432-10987-6, 978-7-654-32109-8', '1, 5, 10', TO_DATE('1/26/2024', 'MM/DD/YYYY'), 'http://dummyimage.com/232x100.png/5fa2dd/ffffff', 'Suite 92');
insert into club (no, name, introduce, members, meetings, read_books, report_board, start_date, profile, location) values (4, 'Rutledge Aughton', 'Puce', '1, 5, 9, 12, 17, 22, 28, 33, 39, 44', '20, 25, 30, 35, 40, 45', '978-0-306-40615-7, 978-3-16-148410-0, 978-1-234-56789-0, 978-4-567-89012-3, 978-9-876-54321-0, 978-6-543-21098-7, 978-2-109-87654-3, 978-8-765-43210-9, 978-5-432-10987-6, 978-7-654-32109-8', '1, 5, 10', TO_DATE('8/5/2023', 'MM/DD/YYYY'), 'http://dummyimage.com/249x100.png/ff4444/ffffff', 'PO Box 67897');
insert into club (no, name, introduce, members, meetings, read_books, report_board, start_date, profile, location) values (5, 'Muffin Brenston', 'Indigo', '1, 5, 9, 12, 17, 22, 28, 33, 39, 44', '20, 25, 30, 35, 40, 45', '978-0-306-40615-7, 978-3-16-148410-0, 978-1-234-56789-0, 978-4-567-89012-3, 978-9-876-54321-0, 978-6-543-21098-7, 978-2-109-87654-3, 978-8-765-43210-9, 978-5-432-10987-6, 978-7-654-32109-8', '1, 5, 10', TO_DATE('1/10/2024', 'MM/DD/YYYY'), 'http://dummyimage.com/131x100.png/cc0000/ffffff', 'Suite 34');

-- 맴버 데이터
SET DEFINE OFF;
insert into member (NO, ID, pw, nickname, location, introduce, mail, review_board, join_club, "PROFILE") values (1, 'Aspirin', 'Green Pepper', 'KELY', 'Female', '337941086995106', 'cdamiata0@xrea.com', '1,2', '1', 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fko.wikipedia.org%2Fwiki%2F%25EC%2582%25AC%25EB%259E%258C&psig=AOvVaw1-0d_0mYJMmvdnoIHghwKr&ust=1708955706825000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCIDi66DSxoQDFQAAAAAdAAAAABAD');
insert into member (NO, ID, pw, nickname, location, introduce, mail, review_board, join_club, "PROFILE") values (2, 'Bright', 'Homeopathic', 'NCPK', 'Male', '337941134576635', 'scoie1@woothemes.com', '', '1,2', 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fko.wikipedia.org%2Fwiki%2F%25EC%2582%25AC%25EB%259E%258C&psig=AOvVaw1-0d_0mYJMmvdnoIHghwKr&ust=1708955706825000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCIDi66DSxoQDFQAAAAAdAAAAABAD');
insert into member (NO, ID, pw, nickname, location, introduce, mail, review_board, join_club, "PROFILE") values (3, 'Cheratussin', 'OXYBUTYNIN', 'EGHD', 'Male', '374622544743528', 'cgilroy2@163.com', '3,4', '3', 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fko.wikipedia.org%2Fwiki%2F%25EC%2582%25AC%25EB%259E%258C&psig=AOvVaw1-0d_0mYJMmvdnoIHghwKr&ust=1708955706825000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCIDi66DSxoQDFQAAAAAdAAAAABAD');
insert into member (NO, ID, pw, nickname, location, introduce, mail, review_board, join_club, "PROFILE") values (4, 'citroma', 'Hydrocodone', 'LKZA', 'Female', '372301305734834', 'sgaynes3@icq.com', '', '1,2,3,4,5', 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fko.wikipedia.org%2Fwiki%2F%25EC%2582%25AC%25EB%259E%258C&psig=AOvVaw1-0d_0mYJMmvdnoIHghwKr&ust=1708955706825000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCIDi66DSxoQDFQAAAAAdAAAAABAD');
insert into member (NO, ID, pw, nickname, location, introduce, mail, review_board, join_club, "PROFILE") values (5, 'Pacific', 'TUNA', 'RPVJ', 'Female', '374283846796027', 'jjosuweit4@prnewswire.com', '5,6,7', '5', 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fko.wikipedia.org%2Fwiki%2F%25EC%2582%25AC%25EB%259E%258C&psig=AOvVaw1-0d_0mYJMmvdnoIHghwKr&ust=1708955706825000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCIDi66DSxoQDFQAAAAAdAAAAABAD');
insert into member (NO, ID, pw, nickname, location, introduce, mail, review_board, join_club, "PROFILE") values (6, 'Naproxen', 'Anas', 'ZUMY', 'Male', '337941102079067', 'adalinder5@cnet.com', '', '2,1', 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fko.wikipedia.org%2Fwiki%2F%25EC%2582%25AC%25EB%259E%258C&psig=AOvVaw1-0d_0mYJMmvdnoIHghwKr&ust=1708955706825000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCIDi66DSxoQDFQAAAAAdAAAAABAD');
insert into member (NO, ID, pw, nickname, location, introduce, mail, review_board, join_club, "PROFILE") values (7, 'VIDAZA', 'AZITHROMYCIN', 'LGAV', 'Female', '341721422227131', 'jbleckly6@devhub.com', '11', '4', 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fko.wikipedia.org%2Fwiki%2F%25EC%2582%25AC%25EB%259E%258C&psig=AOvVaw1-0d_0mYJMmvdnoIHghwKr&ust=1708955706825000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCIDi66DSxoQDFQAAAAAdAAAAABAD');
insert into member (NO, ID, pw, nickname, location, introduce, mail, review_board, join_club, "PROFILE") values (8, 'Greasewood', 'Triamterene', 'WSSS', 'Male', '374622813789087', 'ghouselee7@google.ca', '8,9', '3', 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fko.wikipedia.org%2Fwiki%2F%25EC%2582%25AC%25EB%259E%258C&psig=AOvVaw1-0d_0mYJMmvdnoIHghwKr&ust=1708955706825000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCIDi66DSxoQDFQAAAAAdAAAAABAD');
insert into member (NO, ID, pw, nickname, location, introduce, mail, review_board, join_club, "PROFILE") values (9, 'Bamboo', 'rx act', 'UOII', 'Female', '374283726602691', 'aastley8@msu.edu', '10', '3', 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fko.wikipedia.org%2Fwiki%2F%25EC%2582%25AC%25EB%259E%258C&psig=AOvVaw1-0d_0mYJMmvdnoIHghwKr&ust=1708955706825000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCIDi66DSxoQDFQAAAAAdAAAAABAD');
insert into member (NO, ID, pw, nickname, location, introduce, mail, review_board, join_club, "PROFILE") values (10, 'Preference', 'Lidocaine', 'HKGA', 'Female', '374283752709428', 'ddamp9@devhub.com', '', '', 'https://www.google.com/url?sa=i&url=https%3A%2F%2Fko.wikipedia.org%2Fwiki%2F%25EC%2582%25AC%25EB%259E%258C&psig=AOvVaw1-0d_0mYJMmvdnoIHghwKr&ust=1708955706825000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCIDi66DSxoQDFQAAAAAdAAAAABAD');

-- 미팅 데이터
insert into meeting (club_no, no, book_title, join_member, is_online, meeting_date, meeting_time) values (1, 1, 'Man from Down Under, The', 10, 'Y', TO_DATE('7/15/2023', 'MM/DD/YYYY'), TO_TIMESTAMP('23:50:00', 'HH24:MI:SS'));
insert into meeting (club_no, no, book_title, join_member, is_online, meeting_date, meeting_time) values (2, 1, 'Shrek', 6, 'Y', TO_DATE('10/14/2023', 'MM/DD/YYYY'), TO_TIMESTAMP('12:18:00', 'HH24:MI:SS'));
insert into meeting (club_no, no, book_title, join_member, is_online, meeting_date, meeting_time) values (3, 1, 'Boys, Les', 5, 'Y', TO_DATE('6/12/2023', 'MM/DD/YYYY'), TO_TIMESTAMP('05:22:00', 'HH24:MI:SS'));
insert into meeting (club_no, no, book_title, join_member, is_online, meeting_date, meeting_time) values (4, 1, 'Woman in Red, The', 9, 'N', TO_DATE('1/8/2024', 'MM/DD/YYYY'), TO_TIMESTAMP('12:15:00', 'HH24:MI:SS'));
insert into meeting (club_no, no, book_title, join_member, is_online, meeting_date, meeting_time) values (5, 1, 'Airplane II: The Sequel', 5, 'N', TO_DATE('2/18/2024', 'MM/DD/YYYY'), TO_TIMESTAMP('21:53:00', 'HH24:MI:SS'));
insert into meeting (club_no, no, book_title, join_member, is_online, meeting_date, meeting_time) values (6, 1, 'Lucky Break', 4, 'N', TO_DATE('2/4/2024', 'MM/DD/YYYY'), TO_TIMESTAMP('06:05:00', 'HH24:MI:SS'));
insert into meeting (club_no, no, book_title, join_member, is_online, meeting_date, meeting_time) values (7, 1, 'Puppet Master: The Legacy (Puppet Master 8)', 6, 'N', TO_DATE('1/11/2024', 'MM/DD/YYYY'), TO_TIMESTAMP('10:53:00', 'HH24:MI:SS'));
insert into meeting (club_no, no, book_title, join_member, is_online, meeting_date, meeting_time) values (8, 1, 'When the Game Stands Tall', 4, 'N', TO_DATE('6/17/2023', 'MM/DD/YYYY'), TO_TIMESTAMP('13:02:00', 'HH24:MI:SS'));
insert into meeting (club_no, no, book_title, join_member, is_online, meeting_date, meeting_time) values (9, 1, 'Sweeney, The', 1, 'N', TO_DATE('5/2/2023', 'MM/DD/YYYY'), TO_TIMESTAMP('19:21:00', 'HH24:MI:SS'));
insert into meeting (club_no, no, book_title, join_member, is_online, meeting_date, meeting_time) values (10, 1, 'Åsa-Nisse - Wälkom to Knohult', 7, 'N', TO_DATE('2/8/2024', 'MM/DD/YYYY'), TO_TIMESTAMP('15:44:00', 'HH24:MI:SS'));

-- 리뷰 데이터
insert into review (no, title, content, write_date, writer, book) values (1, 'Breaking Upwards', '10-600 - Partitions', TO_DATE('12/30/2023', 'MM/DD/YYYY'), 'Amlodipine Besylate', '024338303-7');
insert into review (no, title, content, write_date, writer, book) values (2, 'Herr Lehmann', '3-330 - Poured Concrete Basement Walls', TO_DATE('10/8/2023', 'MM/DD/YYYY'), 'morphine sulfate', '878334171-4');
insert into review (no, title, content, write_date, writer, book) values (3, 'Cassandra Crossing, The', '11-030 - Teller and Service Equipment', TO_DATE('5/30/2023', 'MM/DD/YYYY'), 'Cetirizine', '459905486-0');
insert into review (no, title, content, write_date, writer, book) values (4, 'Hemingway Gellhorn', '1-530 - Temporary Construction', TO_DATE('4/19/2023', 'MM/DD/YYYY'), 'Minocycline', '219041029-0');
insert into review (no, title, content, write_date, writer, book) values (5, 'Striptease', '2-500 - Utility Services', TO_DATE('9/17/2023', 'MM/DD/YYYY'), 'Throat-Releev', '058228685-9');
insert into review (no, title, content, write_date, writer, book) values (6, 'Counter Investigation (Contre-enquête)', '1-700 - Execution Requirements', TO_DATE('2/22/2024', 'MM/DD/YYYY'), 'Azathioprine', '506020007-8');
insert into review (no, title, content, write_date, writer, book) values (7, 'American Pie Presents: Band Camp', '11-010 - Maintenance Equipment', TO_DATE('3/12/2023', 'MM/DD/YYYY'), 'ACCURETIC', '670758976-8');
insert into review (no, title, content, write_date, writer, book) values (8, 'Point and Shoot', '17-040 - Profit', TO_DATE('9/8/2023', 'MM/DD/YYYY'), 'PERFECTION LUMIERE', '488831656-2');
insert into review (no, title, content, write_date, writer, book) values (9, 'Ivanhoe', '10-340 - Manufactured Exterior Specialties', TO_DATE('1/10/2024', 'MM/DD/YYYY'), 'Cabbage', '167434263-2');
insert into review (no, title, content, write_date, writer, book) values (10, 'Silent Witness (Do Not Disturb)', '10-900 - Wardrobe and Closet Specialties', TO_DATE('6/2/2023', 'MM/DD/YYYY'), 'Hot Spot', '520978540-8');

-- 댓글 데이터
insert into "REVIEW_COMMENT" (board_no, writer_id, write_date, write_time, content) values (1, 'Tikal', TO_DATE('5/23/2023', 'MM/DD/YYYY'), TO_DATE('10/4/2023', 'MM/DD/YYYY'), 'United Promotions Inc.');
insert into "REVIEW_COMMENT" (board_no, writer_id, write_date, write_time, content) values (2, 'Sochi', TO_DATE('7/20/2023', 'MM/DD/YYYY'), TO_DATE('12/18/2023', 'MM/DD/YYYY'), 'Dispensing Solutions, Inc.');
insert into "REVIEW_COMMENT" (board_no, writer_id, write_date, write_time, content) values (3, 'Paranavaí', TO_DATE('12/24/2023', 'MM/DD/YYYY'), TO_DATE('11/29/2023', 'MM/DD/YYYY'), 'EBPD LLC');
insert into "REVIEW_COMMENT" (board_no, writer_id, write_date, write_time, content) values (4, 'Bethel', TO_DATE('11/16/2023', 'MM/DD/YYYY'), TO_DATE('4/6/2023', 'MM/DD/YYYY'), 'Carolina Medical Products Company');
insert into "REVIEW_COMMENT" (board_no, writer_id, write_date, write_time, content) values (5, 'Ceuta', TO_DATE('9/18/2023', 'MM/DD/YYYY'), TO_DATE('10/16/2023', 'MM/DD/YYYY'), 'Group Practice Solutions, Inc.');
insert into "REVIEW_COMMENT" (board_no, writer_id, write_date, write_time, content) values (6, 'aa', TO_DATE('5/20/2023', 'MM/DD/YYYY'), TO_DATE('3/23/2023', 'MM/DD/YYYY'), 'Noxell');
insert into "REVIEW_COMMENT" (board_no, writer_id, write_date, write_time, content) values (7, 'Havre St-Pierre', TO_DATE('12/19/2023', 'MM/DD/YYYY'), TO_DATE('7/31/2023', 'MM/DD/YYYY'), 'Apotheca Company');
insert into "REVIEW_COMMENT" (board_no, writer_id, write_date, write_time, content) values (8, 'Red Devil', TO_DATE('3/14/2023', 'MM/DD/YYYY'), TO_DATE('6/3/2023', 'MM/DD/YYYY'), 'Sun ss Care Research, LLC');
insert into "REVIEW_COMMENT" (board_no, writer_id, write_date, write_time, content) values (9, 'Wahiawa', TO_DATE('1/11/2024', 'MM/DD/YYYY'), TO_DATE('3/22/2023', 'MM/DD/YYYY'), 'PD-Rx Pharmaceuticals, Inc.');
insert into "REVIEW_COMMENT" (board_no, writer_id, write_date, write_time, content) values (10, 'bb', TO_DATE('4/11/2023', 'MM/DD/YYYY'), TO_DATE('3/22/2023', 'MM/DD/YYYY'), 'REMEDYREPACK INC.');
insert into "REVIEW_COMMENT" (board_no, writer_id, write_date, write_time, content) values (11, 'Pocatello', TO_DATE('1/19/2024', 'MM/DD/YYYY'), TO_DATE('8/2/2023', 'MM/DD/YYYY'), 'KAISER FOUNDATION HOSPITALS');
insert into "REVIEW_COMMENT" (board_no, writer_id, write_date, write_time, content) values (12, 'Bennettsville', TO_DATE('4/12/2023', 'MM/DD/YYYY'), TO_DATE('6/20/2023', 'MM/DD/YYYY'), 'Fenwal, Inc.');
insert into "REVIEW_COMMENT" (board_no, writer_id, write_date, write_time, content) values (13, 'Olbia (SS)', TO_DATE('12/2/2023', 'MM/DD/YYYY'), TO_DATE('11/10/2023', 'MM/DD/YYYY'), 'Neutrogena Corporation');
insert into "REVIEW_COMMENT" (board_no, writer_id, write_date, write_time, content) values (14, 'Chigorodó', TO_DATE('9/15/2023', 'MM/DD/YYYY'), TO_DATE('12/26/2023', 'MM/DD/YYYY'), 'Medline Industries, Inc.');
insert into "REVIEW_COMMENT" (board_no, writer_id, write_date, write_time, content) values (15, 'cc', TO_DATE('6/6/2023', 'MM/DD/YYYY'), TO_DATE('3/26/2023', 'MM/DD/YYYY'), 'Apotheca Company');
insert into "REVIEW_COMMENT" (board_no, writer_id, write_date, write_time, content) values (16, 'Janakpur', TO_DATE('8/18/2023', 'MM/DD/YYYY'), TO_DATE('4/25/2023', 'MM/DD/YYYY'), 'Preferred Pharmaceuticals, Inc');
insert into "REVIEW_COMMENT" (board_no, writer_id, write_date, write_time, content) values (17, 'Bern', TO_DATE('9/4/2023', 'MM/DD/YYYY'), TO_DATE('11/27/2023', 'MM/DD/YYYY'), 'McNEIL-PPC, Inc.');
insert into "REVIEW_COMMENT" (board_no, writer_id, write_date, write_time, content) values (18, 'Skwentna', TO_DATE('3/8/2023', 'MM/DD/YYYY'), TO_DATE('12/30/2023', 'MM/DD/YYYY'), 'Genesis Pharmaceutical, Inc.');
insert into "REVIEW_COMMENT" (board_no, writer_id, write_date, write_time, content) values (19, 'Norridgewock', TO_DATE('9/5/2023', 'MM/DD/YYYY'), TO_DATE('11/15/2023', 'MM/DD/YYYY'), 'Jubilant HollisterStier LLC');
insert into "REVIEW_COMMENT" (board_no, writer_id, write_date, write_time, content) values (20, 'Omidiyeh', TO_DATE('1/23/2024', 'MM/DD/YYYY'), TO_DATE('9/27/2023', 'MM/DD/YYYY'), 'Bryant Ranch Prepack');


commit;