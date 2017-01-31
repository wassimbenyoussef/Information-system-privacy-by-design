package dao;

/**
 * 
 * @author Majdi Ben Fredj
 * 
 * This class contains the schema database
 *
 */
public class Schema {
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

}