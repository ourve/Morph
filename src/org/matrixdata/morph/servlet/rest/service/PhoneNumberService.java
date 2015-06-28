package org.matrixdata.morph.servlet.rest.service;

import org.matrixdata.morph.constant.Constant;
import org.matrixdata.morph.dal.PhoneNumberDAL;
import org.matrixdata.morph.servlet.rest.exception.MorphRestException;
import org.matrixdata.morph.servlet.rest.pojo.RestPhoneNumber;

public class PhoneNumberService {
    public static RestPhoneNumber getRestPhoneNumber(String phoneNumber) throws MorphRestException {
        RestPhoneNumber ret =  PhoneNumberDAL.getInstance().getPhoneNumber(phoneNumber);
        if (ret == null) {
            throw new MorphRestException(Constant.RECORD_NOT_EXIST, Constant.RECORD_NOT_EXIST_STR);
        }
        return ret;
    }
}