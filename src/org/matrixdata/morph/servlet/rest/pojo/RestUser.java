package org.matrixdata.morph.servlet.rest.pojo;

public class RestUser {
    public String username;

    public String password;

    public String phoneNumber;

    public String sex;

    public String credit;


    public RestUser() {

    }

    public RestUser(String usernameIn,
                    String passwordIn,
                    String phoneNumberIn,
                    String sexIn,
                    String creditIn) {
        username = usernameIn;
        password = passwordIn;
        phoneNumber = phoneNumberIn;
        sex = sexIn;
        credit = creditIn;
    }
}
