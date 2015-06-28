package org.matrixdata.morph.servlet.rest.pojo;

public class RestPhoneNumber {

    public String phoneNumber;

    public String identify;

    public String username;

    public String createtime;

    public String status;

    public RestPhoneNumber() {

    }

    public RestPhoneNumber(String phoneNumberIn,
                           String identifyIn,
                           String usernameIn,
                           String createtimeIn,
                           String statusIn) {
        phoneNumber = phoneNumberIn;
        identify = identifyIn;
        username = usernameIn;
        createtime = createtimeIn;
        status = statusIn;
    }
}