package org.matrixdata.morph.servlet.rest.pojo;

public class RestPublicMessage {
    public String text;

    public String longitude;

    public String latitude;

    public String userid;

    public String timestamp;

    public RestPublicMessage() {

    }

    public RestPublicMessage(String textIn,
                             String longitudeIn,
                             String latitudeIn,
                             String useridIn,
                             String timestampIn ) {
        text = textIn;
        longitude = longitudeIn;
        latitude = latitudeIn;
        userid = useridIn;
        timestamp = timestampIn;
    }
}