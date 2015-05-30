package org.matrixdata.morph.servlet.rest.resource;

import org.apache.log4j.Logger;
import org.matrixdata.morph.servlet.rest.Response;
import org.matrixdata.morph.servlet.rest.service.AroundMessageService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("aroundmessage")
public class AroundMessageResource {
    Logger logger = Logger.getLogger(AroundMessageResource.class);

    @GET
    @Path("{longitude:[\\d.]+}/{latitude:[\\d.]+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPublicMessages(@PathParam("longitude") double longitude,
                                      @PathParam("latitude") double latitude) {
        logger.info(String.format("Start get around messages, longitude=%f, latitude=%f.", longitude, latitude));
        Response response = new Response(200, AroundMessageService.getAroundMessages(longitude, latitude));
        return response;
    }
}