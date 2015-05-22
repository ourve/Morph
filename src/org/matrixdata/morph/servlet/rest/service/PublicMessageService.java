package org.matrixdata.morph.servlet.rest.service;

import org.matrixdata.morph.dal.PublicMessageDAL;
import org.matrixdata.morph.dal.UserDAL;
import org.matrixdata.morph.servlet.rest.pojo.RestPublicMessage;
import org.matrixdata.morph.servlet.rest.pojo.RestUser;
import org.matrixdata.morph.servlet.rest.resource.PublicMessageResource;

import java.util.List;

public class PublicMessageService {
    public static List<RestPublicMessage> getPublicMessages() {
        return PublicMessageDAL.getInstance().getPublicMessages();
    }
}