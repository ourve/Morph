package org.matrixdata.morph.servlet.rest.pojo;

public class RestUser {
    public String id;
    public String sex;
    public String name;
    public String credit;

    public RestUser() {

    }

    public RestUser(String idIn, String sexIn, String nameIn, String creditIn) {
        id = idIn;
        sex = sexIn;
        name = nameIn;
        credit = creditIn;
    }
}
