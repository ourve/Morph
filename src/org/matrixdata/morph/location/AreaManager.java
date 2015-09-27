package org.matrixdata.morph.location;

import ch.hsr.geohash.BoundingBox;
import ch.hsr.geohash.GeoHash;
import org.matrixdata.morph.constant.Constant;
import org.matrixdata.morph.dal.AreaDAL;
import org.matrixdata.morph.servlet.rest.pojo.RestArea;

/**
 * current areas of map
 */
public class AreaManager {
    private static AreaManager _INSTANCE = new AreaManager();

    public static AreaManager getInstance() {
        return _INSTANCE;
    }

    public Area getArea(double longitude, double latitude) {
        GeoHash geoHash = GeoHash.withCharacterPrecision(latitude, longitude, Constant.SMALLEST_AREA_CODE);
        String areaCode = AreaDAL.getInstance().getAreaFromGeohash(geoHash.toBase32());
        if (areaCode == null) {
            return null;
        }
        RestArea area = AreaDAL.getInstance().getArea(areaCode);
        return new Area(area);
    }

    public static void main(String[] args) {
        GeoHash geoHash = GeoHash.withCharacterPrecision(39.99, 116.359848, 5);
        BoundingBox box = geoHash.getBoundingBox();
    }

}