package org.matrixdata.morph.servlet.rest.pojo;

public class RestUser {
    public String sex;
    public String name;
    public String credit;

    public RestUser() {

    }

    public RestUser(String sexIn, String nameIn, String creditIn) {
        sex = sexIn;
        name = nameIn;
        credit = creditIn;
    }
}
