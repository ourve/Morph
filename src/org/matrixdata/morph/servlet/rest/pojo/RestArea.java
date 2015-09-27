package org.matrixdata.morph.servlet.rest.pojo;

import ch.hsr.geohash.BoundingBox;
import ch.hsr.geohash.GeoHash;

public class RestArea {

    public String areacode;

    public String minLongitude;

    public String maxLongitude;

    public String minLatitude;

    public String maxLatitude;

    public String station;

    public RestArea() {
        station = "";
    }

    public RestArea(GeoHash geoHash) {
        areacode = geoHash.toBase32();
        BoundingBox box = geoHash.getBoundingBox();
        minLongitude = String.format("%f", box.getMinLon());
        maxLongitude = String.format("%f", box.getMaxLon());
        minLatitude = String.format("%f", box.getMinLat());
        maxLatitude = String.format("%f", box.getMaxLat());
    }

    public RestArea(String areacodeIn, String stationIn) {
        areacode = areacodeIn;
        station = stationIn;
        GeoHash geoHash = GeoHash.fromGeohashString(areacodeIn);
        BoundingBox box = geoHash.getBoundingBox();
        minLongitude = String.format("%f", box.getMinLon());
        maxLongitude = String.format("%f", box.getMaxLon());
        minLatitude = String.format("%f", box.getMinLat());
        maxLatitude = String.format("%f", box.getMaxLat());
    }
}