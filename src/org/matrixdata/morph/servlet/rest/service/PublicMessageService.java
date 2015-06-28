package org.matrixdata.morph.servlet.rest.service;

import org.matrixdata.morph.constant.Constant;
import org.matrixdata.morph.dal.PublicMessageDAL;
import org.matrixdata.morph.dal.exceptions.RecordExistException;
import org.matrixdata.morph.location.Area;
import org.matrixdata.morph.location.AreaManager;
import org.matrixdata.morph.location.Station;
import org.matrixdata.morph.location.StationManager;
import org.matrixdata.morph.servlet.rest.exception.MorphRestException;
import org.matrixdata.morph.servlet.rest.pojo.RestPublicMessage;

import java.util.List;

public class PublicMessageService {
    public static List<RestPublicMessage> getPublicMessages() {
        return PublicMessageDAL.getInstance().getPublicMessages();
    }

    public static void addPublicMessage(RestPublicMessage message) throws MorphRestException{
        double longitude = Double.valueOf(message.longitude);
        double latitude = Double.valueOf(message.latitude);

        Area msgArea = AreaManager.getInstance().getArea(longitude, latitude);
        Station msgStation = StationManager.getInstance().getStation(msgArea);

        try {
            PublicMessageDAL.getInstance().addPublicMessage(message, msgStation);
        }
        catch (RecordExistException e) {
            throw new MorphRestException(Constant.RECORD_EXIST, Constant.RECORD_EXIST_STR);
        }
    }
}