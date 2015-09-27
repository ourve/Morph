package org.matrixdata.morph.servlet.rest.service;

import ch.hsr.geohash.GeoHash;
import org.matrixdata.morph.constant.Constant;
import org.matrixdata.morph.dal.AreaDAL;
import org.matrixdata.morph.dal.PublicMessageDAL;
import org.matrixdata.morph.dal.UserDAL;
import org.matrixdata.morph.dal.exceptions.RecordExistException;
import org.matrixdata.morph.location.Area;
import org.matrixdata.morph.location.AreaManager;
import org.matrixdata.morph.location.Station;
import org.matrixdata.morph.location.StationManager;
import org.matrixdata.morph.servlet.rest.exception.MorphRestException;
import org.matrixdata.morph.servlet.rest.pojo.RestArea;
import org.matrixdata.morph.servlet.rest.pojo.RestPublicMessage;
import org.matrixdata.morph.servlet.rest.pojo.RestUser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AreaService {
    public static List<RestArea> getActiveAreas() {
        Area area = null;
        GeoHash geoHash = null;
        Set<String> existAreas = new HashSet<String>();
        List<RestArea> ret = new ArrayList<RestArea>();
        List<RestPublicMessage> publicMessages = PublicMessageDAL.getInstance().getPublicMessages();
        for (RestPublicMessage message : publicMessages) {
            area = AreaManager.getInstance().getArea(Double.parseDouble(message.longitude), Double.parseDouble(message.latitude));
            geoHash = area.getGeoHash();
            String base32 = geoHash.toBase32();
            if (existAreas.contains(base32)) {
                continue;
            }
            else {
                RestArea restArea = new RestArea(geoHash);
                ret.add(restArea);
                existAreas.add(base32);
            }
        }
        return ret;
    }

    public static void addArea(RestArea area) throws MorphRestException {
        try {
            AreaDAL.getInstance().addArea(area);
        }
        catch (RecordExistException e) {
            throw new MorphRestException(Constant.RECORD_EXIST, Constant.RECORD_EXIST_STR);
        }
    }

    public static List<RestArea> getAreas() {
        List<RestArea> areas = AreaDAL.getInstance().getAreas();
        return areas;
    }

    public static RestArea getArea() {
        return null;
    }
}