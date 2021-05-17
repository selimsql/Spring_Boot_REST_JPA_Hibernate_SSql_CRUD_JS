Create Table User (
 Id BIGINT NOT NULL,
 Code VARCHAR(30) NOT NULL,
 Name VARCHAR(30) NOT NULL,
 Surname VARCHAR(30) NOT NULL,
 Password VARCHAR(100) NOT NULL,
 Phone VARCHAR(20),
 Status SmallInt NOT NULL
);

Create Unique Index UserPK On User(Id);
Create Unique Index UserCodeUIdx On User(Code);

Create SEQUENCE SEQ_USER_PK;