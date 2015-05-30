package org.matrixdata.morph.location;

import ch.hsr.geohash.GeoHash;

/**
 * current areas of map
 */
public class AreaManager {
    private static AreaManager _INSTANCE = new AreaManager();

    public static AreaManager getInstance() {
        return _INSTANCE;
    }

    public Area getArea(double longitude, double latitude) {
        // todo : change the way to get area
        GeoHash geoHash = GeoHash.withCharacterPrecision(latitude, longitude, 5);
        return new Area(geoHash);
    }

    public static void main(String[] args) {
        System.out.print(System.currentTimeMillis());
    }

}