package org.matrixdata.morph.location;

import ch.hsr.geohash.GeoHash;

/**
 * a simple implement of geohash
 */
public class Area {
    private GeoHash _geohash;

    public Area(GeoHash geohash) {
        _geohash = geohash;
    }

    public String baseCode() {
        return _geohash.toBase32();
    }
}