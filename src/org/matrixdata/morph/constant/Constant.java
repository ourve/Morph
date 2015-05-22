package org.matrixdata.morph.constant;

public class Constant {
    public static String HBASE_ZOOKEEPER_QUORUM = "localhost";

    // table user
    public static String TABLE_USER = "users";
    public static String USER_COLUMNFAMILY = "cf";
    public static String USER_COLUMN_SEX = "sex";
    public static String USER_COLUMN_NAME = "name";
    public static String USER_COLUMN_CREDIT = "credit";

    // table public message
    public static String TABLE_PUBLICMESSAGE = "publicmessages";
    public static String PUBLICMESSAGE_COLUMNFAMILY = "cf";
    public static String PUBLICMESSAGE_COLUMN_ID = "id";
    public static String PUBLICMESSAGE_COLUMN_TEXT = "text";
    public static String PUBLICMESSAGE_COLUMN_XLOCATION = "xlocation";
    public static String PUBLICMESSAGE_COLUMN_YLOCATION = "ylocation";
    public static String PUBLICMESSAGE_COLUMN_USERID = "userid";

    //status
    public static int STATUS_OK = 200;
    public static int RECORD_EXIST = 401;

}
