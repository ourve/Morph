package org.matrixdata.morph.location;

/**
 * the unit of sending/receiving/transporting message
 * in current version a station is relate to an area
 */

public class Station {
    private String _name;

    public Station(String name) {
        _name = name;
    }

    public String getName() {
        return _name;
    }
}