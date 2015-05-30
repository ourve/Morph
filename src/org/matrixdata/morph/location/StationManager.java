package org.matrixdata.morph.location;

/**
 * current stations of map
 */
public class StationManager {
    private static StationManager _INSTANCE = new StationManager();

    public static StationManager getInstance() {
        return _INSTANCE;
    }

    public Station getStation(Area area) {
        return new Station(area.baseCode());
    }
}