package org.matrixdata.morph.servlet.rest.resource;

import org.apache.log4j.Logger;
import org.matrixdata.morph.constant.Constant;
import org.matrixdata.morph.servlet.rest.Response;
import org.matrixdata.morph.servlet.rest.exception.MorphRestException;
import org.matrixdata.morph.servlet.rest.pojo.RestArea;
import org.matrixdata.morph.servlet.rest.pojo.RestPublicMessage;
import org.matrixdata.morph.servlet.rest.service.AreaService;
import org.matrixdata.morph.servlet.rest.service.PublicMessageService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("area")
public class AreaResource {
    Logger logger = Logger.getLogger(AroundMessageResource.class);

    @GET
    @Path("/activeareas")
    @Produces(MediaType.APPLICATION_JSON)
    //todo
    public Response getActiveAreas() {
        logger.info("Start get active areas.");
        Response response = new Response(Constant.STATUS_OK, Constant.STATUS_OK_STR, AreaService.getActiveAreas());
        return response;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addActiveArea(RestArea area) {
        //todo
        return new Response(Constant.STATUS_OK, Constant.STATUS_OK_STR, null);
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{longitude:[\\d.]+}/{latitude:[\\d.]+}/{length:\\d+}")
    public Response getArea(@PathParam("longitude") double longitude,
                            @PathParam("latitude") double latitude,
                            @PathParam("length") int length) {
        //todo
        return new Response(Constant.STATUS_OK, Constant.STATUS_OK_STR, null);
    }


}