package org.matrixdata.morph.servlet.rest.resource;

import org.apache.log4j.Logger;
import org.matrixdata.morph.constant.Constant;
import org.matrixdata.morph.servlet.rest.Response;
import org.matrixdata.morph.servlet.rest.service.AreaService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("area")
public class AreaResource {
    Logger logger = Logger.getLogger(AroundMessageResource.class);

    @GET
    @Path("/activeareas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActiveAreas() {
        logger.info("Start get active areas.");
        Response response = new Response(Constant.STATUS_OK, Constant.STATUS_OK_STR, AreaService.getActiveAreas());
        return response;
    }
}