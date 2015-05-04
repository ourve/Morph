package org.matrixdata.morph.servlet.rest;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class APIApplication extends ResourceConfig {

    public APIApplication() {
        super(JacksonFeature.class);
    }
}