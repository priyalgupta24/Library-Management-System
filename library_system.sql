USE library_system;

DROP TABLE IF EXISTS books;
CREATE TABLE books (
  book_id varchar(30) NOT NULL,
  book_title varchar(50)  NOT NULL,
  book_author varchar(50) NOT NULL,
  book_status varchar(45)  NOT NULL,
  PRIMARY KEY (book_id)
);

INSERT INTO books (book_id,book_title,book_author,book_status) VALUES 
 ('A1','Computer Networks','Tanenbaum','Issued'),
 ('A2','Data Structures','Deepali Srivastava','Issued'),
 ('A3','Computer Graphics','Pauline Baker','Issued'),
 ('A4','Software Engineering','Lan Sommerville','Available'),
 ('A5','Algorithm Design','S.G. Stuart','Available'),
 ('A6','Introduction to Algorithms','Cormen','Issued'),
 ('A7','Let Us C','Yashwant Kanetkar','Available'),
 ('A8','Head First Java','Kathy Sierra','Available');

DROP TABLE IF EXISTS lib_member;
CREATE TABLE lib_member (
  mem_id varchar(11) NOT NULL,
  mem_name varchar(100) NOT NULL,
  mem_password varchar(100) NOT NULL,
  PRIMARY KEY  (mem_id)
);


INSERT INTO lib_member (mem_id,mem_name,mem_password) VALUES 
 ('1','Aditi','Aditi123'),
 ('2','Tanmay','Tanmay123'),
 ('3','Priyal','Priyal123'),
 ('4','Dhairya','Dhairya123');


DROP TABLE IF EXISTS lib_transaction;
CREATE TABLE lib_transaction (
  trn_mem_id varchar(10) NOT NULL,
  trn_book_id varchar(10) NOT NULL,
  trn_issue_dt date NOT NULL,
  trn_receive_dt date NOT NULL,
  PRIMARY KEY (trn_book_id)
);


INSERT INTO lib_transaction (trn_mem_id,trn_book_id,trn_issue_dt,trn_receive_dt) VALUES 
 ('1','A1','2019-07-01','2019-07-16'),
 ('2','A2','2019-07-08','2019-07-23'),
 ('3','A3','2019-08-12','2019-08-27'),
 ('4','A6','2019-08-14','2019-08-29');


