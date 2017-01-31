package dao;

public class TCell_QEP_IDs {
	/**
	 * EP_QEP class must exist in every application. It allows to interact
	 * hard coded QEPs inside SGBD. Application QEP start id should be greater
	 * than the value of last element of this class.
	 */
	public static class EP_QEP // 1
	{
		public static final int EP_QEP_INSERT = 0;
	}

	/* Application QEP ids */
	public static class QEP // 2
	{
		public static final int EP_insertUser = EP_QEP.EP_QEP_INSERT + 1;
		public static final int EP_insertFileDesc = EP_insertUser + 1;
		public static final int EP_deleteFileDescByFileGid = EP_insertFileDesc + 1;
		public static final int EP_deleteUserByUserGid = EP_deleteFileDescByFileGid + 1;
		public static final int EP_isFileGidExists = EP_deleteUserByUserGid + 1;
		public static final int EP_getUserById = EP_isFileGidExists + 1;
		public static final int EP_getFileDescByGid = EP_getUserById + 1;
		public static final int EP_getAllUsersGid = EP_getFileDescByGid + 1;
		public static final int EP_getAllFiles = EP_getAllUsersGid + 1;
		public static final int EP_getMyInfo = EP_getAllFiles + 1;
		public static final int EP_insertMyInfo = EP_getMyInfo + 1;
		public static final int EP_getAllUsers = EP_insertMyInfo + 1;
		public static final int EP_getAllFilesDesc = EP_getAllUsers + 1;
		public static final int EP_insertSharedKey = EP_getAllFilesDesc + 1;
		public static final int EP_getSharedKeyById = EP_insertSharedKey + 1;
		public static final int EP_deleteMyInfo = EP_getSharedKeyById + 1;
	}
}
