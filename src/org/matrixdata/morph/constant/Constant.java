package org.matrixdata.morph.constant;

public class Constant {
    public static String HBASE_ZOOKEEPER_QUORUM = "localhost";

    // table user
    public static String TABLE_USER = "users";
    public static String USER_COLUMNFAMILY = "cf";
    public static String USER_COLUMN_SEX = "sex";
    public static String USER_COLUMN_CREDIT = "credit";

    // table public message
    public static String TABLE_PUBLICMESSAGE = "publicmessages";
    public static String PUBLICMESSAGE_COLUMNFAMILY = "cf";
    public static String PUBLICMESSAGE_COLUMN_TEXT = "text";
    public static String PUBLICMESSAGE_COLUMN_LONGITUDE = "longitude";
    public static String PUBLICMESSAGE_COLUMN_LATITUDE = "latitude";
    public static String PUBLICMESSAGE_COLUMN_USERID = "userid";
    public static String PUBLICMESSAGE_COLUMN_TIMESTAMP = "timestamp";

    //status
    public static int STATUS_OK = 200;
    public static int RECORD_EXIST = 401;

}
