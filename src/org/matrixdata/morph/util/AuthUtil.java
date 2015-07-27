package org.matrixdata.morph.util;

import org.apache.log4j.Logger;
import org.matrixdata.morph.dal.UserDAL;
import org.matrixdata.morph.servlet.rest.pojo.RestUser;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AuthUtil {
    static Logger logger = Logger.getLogger(AuthUtil.class);

    public static boolean verify(HttpServletRequest request) {
        if (request.getHeader("Authorization") == null) {
            return false;
        }

        String encoded = (request.getHeader("Authorization"));
        String tmp = encoded.substring(6);
        String decodeStr = null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] decodeByte = decoder.decodeBuffer(tmp);
            decodeStr = new String(decodeByte);
        }
        catch (IOException e) {
            logger.info("No header Authorization in request");
            return false;
        }

        String username = decodeStr.substring(0, decodeStr.indexOf(":"));
        String password = decodeStr.substring(decodeStr.indexOf(":") + 1);
        RestUser user = UserDAL.getInstance().getUser(username);
        if (user == null) {
            logger.info(String.format("No request user %s, auth fail.", username));
            return false;
        }

        if (!user.password.equals(password)) {
            logger.info(String.format("Password is not correct, auth fail [username : %s, password: %s].", username, password));
            return false;
        }

        return true;
    }
}