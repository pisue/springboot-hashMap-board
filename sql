CREATE TABLE MEMBER(
	member_id number(6) NOT NULL PRIMARY KEY,
	name varchar2(10) NOT null,
	phone varchar2(20),
	address varchar2(100),
	reg_date DATE,
	update_date DATE,
	grade char(1)
);

