package org.matrixdata.morph.servlet.rest.pojo;

import java.util.List;

public class RestStation {
    public String name;

    public List<String> areas;

    public RestStation() {

    }

    public RestStation(String nameIn, List<String> areasIn) {
        name = nameIn;
        areas = areasIn;
    }
}