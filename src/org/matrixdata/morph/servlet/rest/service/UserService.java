package org.matrixdata.morph.servlet.rest.service;

import org.matrixdata.morph.constant.Constant;
import org.matrixdata.morph.dal.PhoneNumberDAL;
import org.matrixdata.morph.dal.UserDAL;
import org.matrixdata.morph.dal.exceptions.RecordExistException;
import org.matrixdata.morph.servlet.rest.exception.MorphRestException;
import org.matrixdata.morph.servlet.rest.pojo.RestPhoneNumber;
import org.matrixdata.morph.servlet.rest.pojo.RestUser;
import org.matrixdata.morph.util.SMSDeliver;

import java.util.List;
import java.util.Random;

public class UserService {
    public static List<RestUser> getUsers() {
        return UserDAL.getInstance().getUsers();
    }

    public static void addUser(RestUser user, String identify) throws MorphRestException {
        try {
            RestPhoneNumber phoneNumber = PhoneNumberDAL.getInstance().getPhoneNumber(user.phoneNumber);
            if (phoneNumber == null) {
                throw new MorphRestException(Constant.AUTH_FAIL, "This is not a valid phone");
            }

            if (!identify.equals(phoneNumber.identify)) {
                throw new MorphRestException(Constant.AUTH_FAIL, "Identify is wrong.");
            }

            UserDAL.getInstance().addUser(user);
        }
        catch (RecordExistException e) {
            throw new MorphRestException(Constant.RECORD_EXIST, Constant.RECORD_EXIST_STR);
        }
    }


    public static void getIdentify(String phoneNumber) throws MorphRestException {
        String identify = _getIdentifyStr();
        String timeStr = String.format("%d", System.currentTimeMillis());

        RestPhoneNumber oldPhoneNumber = PhoneNumberDAL.getInstance().getPhoneNumber(phoneNumber);
        if ((oldPhoneNumber != null) && oldPhoneNumber.status.equals(Constant.PHONENUMBER_STATUS_REGEISTERED)) {
            throw new MorphRestException(Constant.RECORD_EXIST, "this is a registered phone.");
        }

        RestPhoneNumber restPhoneNumber = new RestPhoneNumber(phoneNumber, identify,
                phoneNumber, timeStr, Constant.PHONENUMBER_STATUS_UNAVAILABLE);

        SMSDeliver.sendMessage(phoneNumber, _getSMSText(identify));

        PhoneNumberDAL.getInstance().updatePhoneNumber(restPhoneNumber);
    }

    private static String _getIdentifyStr() {
        int length = Constant.IDENTIFY_LENGTH;
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    private static String _getSMSText(String identify) {
        String text = "验证码：" + identify + "。【数字矩阵】";
        return text;
    }
}
