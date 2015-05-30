package org.matrixdata.morph.servlet.rest.service;

import org.matrixdata.morph.dal.PublicMessageDAL;
import org.matrixdata.morph.location.Area;
import org.matrixdata.morph.location.AreaManager;
import org.matrixdata.morph.location.Station;
import org.matrixdata.morph.location.StationManager;
import org.matrixdata.morph.servlet.rest.pojo.RestPublicMessage;

import java.util.List;

public class AroundMessageService {
    public static List<RestPublicMessage> getAroundMessages(double longitude, double latitude) {
        Area msgArea = AreaManager.getInstance().getArea(longitude, latitude);
        Station msgStation = StationManager.getInstance().getStation(msgArea);

        // todo add timestamp filter
        return PublicMessageDAL.getInstance().getPublicMessages(msgStation, 1000000000);
    }
}