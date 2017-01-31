CREATE TABLE QEP
(
IdGlobal numeric PRIMARY KEY,
QEPStr char(458),
SQLStr Blob,
ExplainStr Blob
)
/

CREATE TABLE LogDeleted 
(
TabId numeric,
TuplePos numeric,
Flag numeric 
) 
/
CREATE TABLE UpdateLog 
(
TabId numeric,
TuplePos numeric,
TupleSize numeric,
CompleteTup char(498)
)
/

CREATE TABLE User 
( 
IdGlobal numeric PRIMARY KEY,
TCELLIP char(15),
TCELLPORT numeric, 
PUBLICKEY Blob
)
/

CREATE TABLE File
(
IdGlobal numeric PRIMARY KEY,
FILEGID char(100),
FILEID char(100),
SECRETKEY char(30),
IV char(30),
TYPE char(10),
DESCRIPTION char(100)
)
/

CREATE TABLE MyInfo 
( 
IdGlobal numeric PRIMARY KEY,
MYTCELLIP char(15),
MYTCELLPORT numeric, 
MYPUBLICKEY Blob,
MYPRIVATEKEY Blob
)
/

CREATE TABLE SharedKey 
( 
IdGlobal numeric PRIMARY KEY,
IND numeric,
ENCRYPTEDSHARE char(100), 
SHARE char(100),
PROOFC char(100),
PROOFR char(100),
UCODE Blob
)
/
