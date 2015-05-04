package org.matrixdata.morph.servlet.rest.service;

import org.matrixdata.morph.dal.UserDAL;
import org.matrixdata.morph.servlet.rest.pojo.RestUser;

import java.util.List;

public class UserService {
    public static List<RestUser> getUsers() {
        return UserDAL.getInstance().getUsers();
    }

    public static void addUser(RestUser user) {
        UserDAL.getInstance().addUser(user);
    }
}
