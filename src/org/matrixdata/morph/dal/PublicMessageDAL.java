package org.matrixdata.morph.dal;

import org.apache.log4j.Logger;
import org.matrixdata.morph.servlet.rest.pojo.RestPublicMessage;

import java.util.ArrayList;
import java.util.List;

public class PublicMessageDAL {
    static Logger logger = Logger.getLogger(PublicMessageDAL.class);

    static private PublicMessageDAL _INSTANCE = new PublicMessageDAL();

    static public PublicMessageDAL getInstance() {
        return _INSTANCE;
    }

    public List<RestPublicMessage> getPublicMessages() {
        RestPublicMessage message1 = new RestPublicMessage("id1", "This is a test message", "116.359848", "39.989933", "id1");
        RestPublicMessage message2 = new RestPublicMessage("id2", "Another test message", "116.344434", "39.998568", "id2");


        List<RestPublicMessage> ret = new ArrayList<RestPublicMessage>();
        ret.add(message1);
        ret.add(message2);
        return ret;
    }
}