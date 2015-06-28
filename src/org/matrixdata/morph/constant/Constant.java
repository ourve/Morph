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

    // table phonenumber
    public static String TABLE_PHONENUMBER = "phonenumbers";
    public static String PHONENUMBER_COLUMNFAMILY = "cf";
    public static String PHONENUMBER_COLUMN_IDENTIFY = "identify";
    public static String PHONENUMBER_COLUMN_USERNAME = "username";
    public static String PHONENUMBER_COLUMN_CREATE_TIME = "createtime";
    public static String PHONENUMBER_COLUMN_STATUS = "status";
    public static String PHONENUMBER_STATUS_REGEISTERED = "registered";
    public static String PHONENUMBER_STATUS_UNAVAILABLE = "unavailable";
    public static String PHONENUMBER_STATUS_NOT_EXIST = "notexist";

    //status
    public static int STATUS_OK = 200;
    public static String STATUS_OK_STR = "ok";

    public static int AUTH_FAIL = 401;
    public static String AUTH_FAIL_STR = "auth fail";

    public static int RECORD_EXIST = 501;
    public static String RECORD_EXIST_STR = "record exist";
    public static int RECORD_NOT_EXIST = 502;
    public static String RECORD_NOT_EXIST_STR = "record not exist";


    public static int IDENTIFY_LENGTH = 6;
}
