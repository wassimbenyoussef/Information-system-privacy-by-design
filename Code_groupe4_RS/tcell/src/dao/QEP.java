package dao;

/**
 * 
 * 
 * @author Majdi Ben Fredj
 * 
 * The execution plans of queries should be declaring this class
 * 
 */

public class QEP {

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