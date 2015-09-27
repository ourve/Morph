package org.matrixdata.morph.constant;

public class Constant {
    public static String HBASE_ZOOKEEPER_QUORUM = "localhost";

    // table user
    public static String TABLE_USER = "users";
    public static String USER_COLUMNFAMILY = "cf";
    public static String USER_COLUMN_PASSWORD = "password";
    public static String USER_COLUMN_PHONENUMBER = "phonenumber";
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

    // table activearea
    public static String TABLE_ACTIVEAREA = "activeareas";
    public static String ACTIVEAREA_COLUMNFAMILY = "cf";

    // table areas
    public static String TABLE_AREA = "areas";
    public static String AREA_COLUMNFAMILY = "cf";
    public static String AREA_COLUMN_STATION = "station";

    // table station
    public static String TABLE_STATION = "stations";
    public static String STATION_COLUMNFAMILY = "cf";

    //status
    public static int STATUS_OK = 200;
    public static String STATUS_OK_STR = "ok";

    public static int AUTH_FAIL = 401;
    public static String AUTH_FAIL_STR = "auth fail";

    public static int STATUS_UNKNOWN_ERROR = 500;
    public static String STATUS_UNKNOWN_ERROR_STR = "unknown error";

    public static int RECORD_EXIST = 501;
    public static String RECORD_EXIST_STR = "record exist";
    public static int RECORD_NOT_EXIST = 502;
    public static String RECORD_NOT_EXIST_STR = "record not exist";


    public static int IDENTIFY_LENGTH = 6;
}
