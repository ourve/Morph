package org.matrixdata.morph.servlet.rest.resource;

import org.apache.log4j.Logger;
import org.matrixdata.morph.constant.Constant;
import org.matrixdata.morph.servlet.rest.Response;
import org.matrixdata.morph.servlet.rest.exception.MorphRestException;
import org.matrixdata.morph.servlet.rest.pojo.RestStation;
import org.matrixdata.morph.servlet.rest.service.StationService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("station")
public class StationResource {
    Logger logger = Logger.getLogger(StationResource.class);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addStation(RestStation station) {
        logger.info(String.format("Start add station. name = %s", station.name));
        try {
            StationService.addStation(station);
        }
        catch (MorphRestException e) {
            return new Response(e.status, e.errorMsg, null);
        }
        return new Response(Constant.STATUS_OK, Constant.STATUS_OK_STR, null);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStations() {
        logger.info("Start get stations.");
        Response response = new Response(Constant.STATUS_OK, Constant.STATUS_OK_STR, StationService.getStations());
        return response;
    }

    @GET
    @Path("{name}")
    public Response getStation(@PathParam("name") String name) {
        logger.info("Start get station.");
        Response response = new Response(Constant.STATUS_OK, Constant.STATUS_OK_STR, StationService.getStation(name));
        return response;
    }
}