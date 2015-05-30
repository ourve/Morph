package org.matrixdata.morph.servlet.rest.service;

import org.matrixdata.morph.dal.PublicMessageDAL;
import org.matrixdata.morph.dal.exceptions.RecordExistException;
import org.matrixdata.morph.location.Area;
import org.matrixdata.morph.location.AreaManager;
import org.matrixdata.morph.location.Station;
import org.matrixdata.morph.location.StationManager;
import org.matrixdata.morph.servlet.rest.pojo.RestPublicMessage;

import java.util.List;

public class PublicMessageService {
    public static List<RestPublicMessage> getPublicMessages() {
        return PublicMessageDAL.getInstance().getPublicMessages();
    }

    public static void addPublicMessage(RestPublicMessage message) throws RecordExistException {
        double longitude = Double.valueOf(message.longitude);
        double latitude = Double.valueOf(message.latitude);

        Area msgArea = AreaManager.getInstance().getArea(longitude, latitude);
        Station msgStation = StationManager.getInstance().getStation(msgArea);

        PublicMessageDAL.getInstance().addPublicMessage(message, msgStation);
    }
}