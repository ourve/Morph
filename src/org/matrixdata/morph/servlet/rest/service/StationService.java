package org.matrixdata.morph.servlet.rest.service;

import org.matrixdata.morph.constant.Constant;
import org.matrixdata.morph.dal.AreaDAL;
import org.matrixdata.morph.dal.StationDAL;
import org.matrixdata.morph.dal.exceptions.RecordExistException;
import org.matrixdata.morph.servlet.rest.exception.MorphRestException;
import org.matrixdata.morph.servlet.rest.pojo.RestArea;
import org.matrixdata.morph.servlet.rest.pojo.RestStation;

import java.util.List;

public class StationService {
    public static void addStation(RestStation station) throws MorphRestException {
        try {
            StationDAL.getInstance().addStation(station);
        }
        catch (RecordExistException e) {
            throw new MorphRestException(Constant.RECORD_EXIST, Constant.RECORD_EXIST_STR);
        }
    }

    public static List<RestStation> getStations() {
        List<RestStation> stations = StationDAL.getInstance().getStations();
        return stations;
    }

    public static RestStation getStation(String name) {
        return StationDAL.getInstance().getStation(name);
    }
}