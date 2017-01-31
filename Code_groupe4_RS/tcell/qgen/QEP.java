package org.inria.dmsp;
public class QEP {
public static final String META =
	"TAB_DESC,7\n"+
	"0	QEP	512\n"+
	"1	LogDeleted	12\n"+
	"2	UpdateLog	512\n"+
	"3	User	49\n"+
	"4	File	386\n"+
	"5	MyInfo	73\n"+
	"6	SharedKey	440\n"+
	"COL_DESC,34\n"+
	"0	0	IdGlobal	4	1	0\n"+
	"1	0	QEPStr	460	0	4\n"+
	"2	0	SQLStr	24	9	464\n"+
	"3	0	ExplainStr	24	9	488\n"+
	"4	1	TabId	4	1	0\n"+
	"5	1	TuplePos	4	1	4\n"+
	"6	1	Flag	4	1	8\n"+
	"7	2	TabId	4	1	0\n"+
	"8	2	TuplePos	4	1	4\n"+
	"9	2	TupleSize	4	1	8\n"+
	"10	2	CompleteTup	500	0	12\n"+
	"11	3	IdGlobal	4	1	0\n"+
	"12	3	TCELLIP	17	0	4\n"+
	"13	3	TCELLPORT	4	1	21\n"+
	"14	3	PUBLICKEY	24	9	25\n"+
	"15	4	IdGlobal	4	1	0\n"+
	"16	4	FILEGID	102	0	4\n"+
	"17	4	FILEID	102	0	106\n"+
	"18	4	SECRETKEY	32	0	208\n"+
	"19	4	IV	32	0	240\n"+
	"20	4	TYPE	12	0	272\n"+
	"21	4	DESCRIPTION	102	0	284\n"+
	"22	5	IdGlobal	4	1	0\n"+
	"23	5	MYTCELLIP	17	0	4\n"+
	"24	5	MYTCELLPORT	4	1	21\n"+
	"25	5	MYPUBLICKEY	24	9	25\n"+
	"26	5	MYPRIVATEKEY	24	9	49\n"+
	"27	6	IdGlobal	4	1	0\n"+
	"28	6	IND	4	1	4\n"+
	"29	6	ENCRYPTEDSHARE	102	0	8\n"+
	"30	6	SHARE	102	0	110\n"+
	"31	6	PROOFC	102	0	212\n"+
	"32	6	PROOFR	102	0	314\n"+
	"33	6	UCODE	24	9	416\n"+
	"FK_DESC,0\n"+
	"SKT_DESC,0\n"+
	"SKT_COL_DESC,0\n"+
	"CI_DESC,5\n"+
	"0	0	0	0	1\n"+
	"1	3	3	11	1\n"+
	"2	4	4	15	1\n"+
	"3	5	5	22	1\n"+
	"4	6	6	27	1\n"+
	"";

public static String EP_insertUser =
	"/*EP \u0004 6 1 1 -1 1 ?1 # 5 0 0 1 4 3 1 11 r0 12 ?2 13 ?3 14 ?4 # \u0000*/";

public static String EP_insertFileDesc =
	"/*EP \u0007 6 1 1 -1 2 ?1 # 5 0 0 1 7 4 1 15 r0 16 ?2 17 ?3 18 ?4 19 ?5 20 ?6 21 ?7 # \u0000*/";

public static String EP_deleteFileDescByFileGid =
	"/*EP \u0001 0 4 4 4 # 1 3 3 4 r0 2 4 1 15 16 # 4 2 2 3 16 0 ?1 r2 # 5 1 1 2 3 1 1 4 v14 5 r0 6 v10 # 9 0 0 1 4 r0 # \u0000*/";

public static String EP_deleteUserByUserGid =
	"/*EP \u0001 2 2 2 -1 1 ?1 # 5 1 1 2 3 1 1 4 v13 5 r1 6 v10 # 9 0 0 1 3 r1 # \u0000*/";

public static String EP_isFileGidExists =
	"/*EP \u0001 0 2 2 4 # 1 1 1 2 r0 2 4 1 15 16 # 4 0 0 1 16 0 ?1 r2 # \u0000 2 1 1 IdGlobal 0 2 FILEGID # \u0000*/";

public static String EP_getUserById =
	"/*EP \u0001 2 1 1 -1 1 ?1 # 1 0 0 1 r1 3 3 1 12 14 13 # \u0000 4 1 0 IdGlobal 0 2 TCELLIP 9 3 PUBLICKEY 1 4 TCELLPORT # \u0000*/";

public static String EP_getFileDescByGid =
	"/*EP \u0001 0 2 2 4 # 1 1 1 2 r0 4 4 1 17 18 19 16 # 4 0 0 1 16 0 ?1 r4 # \u0000 3 0 1 FILEID 0 2 SECRETKEY 0 3 IV # \u0000*/";

public static String EP_getAllUsersGid =
	"/*EP \u0000 0 1 1 3 # 1 0 0 1 r0 1 3 1 11 # \u0000 1 1 1 IdGlobal # \u0000*/";

public static String EP_getAllFiles =
	"/*EP \u0000 0 1 1 4 # 1 0 0 1 r0 3 4 1 16 20 21 # \u0000 3 0 1 FILEGID 0 2 TYPE 0 3 DESCRIPTION # \u0000*/";

public static String EP_getMyInfo =
	"/*EP \u0000 0 1 1 5 # 1 0 0 1 r0 5 5 1 22 23 24 25 26 # \u0000 5 1 1 IdGlobal 0 2 MYTCELLIP 1 3 MYTCELLPORT 9 4 MYPUBLICKEY 9 5 MYPRIVATEKEY # \u0000*/";

public static String EP_insertMyInfo =
	"/*EP \u0005 6 1 1 -1 3 ?1 # 5 0 0 1 5 5 1 22 r0 23 ?2 24 ?3 25 ?4 26 ?5 # \u0000*/";

public static String EP_getAllUsers =
	"/*EP \u0000 0 1 1 3 # 1 0 0 1 r0 4 3 1 11 12 14 13 # \u0000 4 1 1 IdGlobal 0 2 TCELLIP 9 3 PUBLICKEY 1 4 TCELLPORT # \u0000*/";

public static String EP_getAllFilesDesc =
	"/*EP \u0000 0 1 1 4 # 1 0 0 1 r0 7 4 1 15 16 17 18 19 20 21 # \u0000 7 1 1 IdGlobal 0 2 FILEGID 0 3 FILEID 0 4 SECRETKEY 0 5 IV 0 6 TYPE 0 7 DESCRIPTION # \u0000*/";

public static String EP_insertSharedKey =
	"/*EP \u0007 6 1 1 -1 4 ?1 # 5 0 0 1 7 6 1 27 r0 28 ?2 29 ?3 30 ?4 31 ?5 32 ?6 33 ?7 # \u0000*/";

public static String EP_getSharedKeyById =
	"/*EP \u0001 2 1 1 -1 4 ?1 # 1 0 0 1 r1 6 6 1 28 29 30 31 32 33 # \u0000 6 1 2 IND 0 3 ENCRYPTEDSHARE 0 4 SHARE 0 5 PROOFC 0 6 PROOFR 9 7 UCODE # \u0000*/";

public static String EP_deleteMyInfo =
	"/*EP \u0001 2 2 2 -1 3 ?1 # 5 1 1 2 3 1 1 4 v15 5 r1 6 v10 # 9 0 0 1 5 r1 # \u0000*/";

}

