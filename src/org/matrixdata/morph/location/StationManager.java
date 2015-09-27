package org.matrixdata.morph.location;

import org.matrixdata.morph.constant.Constant;

/**
 * current stations of map
 */
public class StationManager {
    private static StationManager _INSTANCE = new StationManager();

    public static StationManager getInstance() {
        return _INSTANCE;
    }

    public Station getStation(Area area) {
        String stationName =  area.getStation();
        if (stationName == null) {
            stationName = Constant.DEFAULT_STATION;
        }
        return new Station(stationName);
    }
}