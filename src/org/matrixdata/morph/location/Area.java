package org.matrixdata.morph.location;

import ch.hsr.geohash.GeoHash;
import org.matrixdata.morph.servlet.rest.pojo.RestArea;

/**
 * a simple implement of geohash
 */
public class Area {
    private GeoHash _geohash;

    private String _station;

    public Area(RestArea restArea) {
        _geohash = GeoHash.fromGeohashString(restArea.areacode);
        _station = restArea.station;
    }

    public GeoHash getGeoHash() {
        return _geohash;
    }

    public String baseCode() {
        return _geohash.toBase32();
    }

    public String getStation() {
        return _station;
    }
}