package org.matrixdata.morph.servlet.rest.pojo;

public class RestPublicMessage {
    public String id;

    public String text;

    public String xLocation;

    public String yLocation;

    public String userid;

    public RestPublicMessage() {

    }

    public RestPublicMessage(String idIn, String textIn, String xLocationIn, String yLocationIn, String useridIn ) {
        id = idIn;
        text = textIn;
        xLocation = xLocationIn;
        yLocation = yLocationIn;
        userid = useridIn;
    }
}