package org.matrixdata.morph.servlet.rest.resource;

import org.apache.log4j.Logger;
import org.matrixdata.morph.constant.Constant;
import org.matrixdata.morph.dal.exceptions.RecordExistException;
import org.matrixdata.morph.servlet.rest.Response;
import org.matrixdata.morph.servlet.rest.exception.MorphRestException;
import org.matrixdata.morph.servlet.rest.pojo.RestPublicMessage;
import org.matrixdata.morph.servlet.rest.service.PublicMessageService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "user" path)
 */
@Path("publicmessage")
public class PublicMessageResource {
    Logger logger = Logger.getLogger(PublicMessageResource.class);

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPublicMessages() {
        logger.info("Start get users.");
        Response response = new Response(Constant.STATUS_OK, Constant.STATUS_OK_STR, PublicMessageService.getPublicMessages());
        return response;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{id:\\d+}")
    public String getUser(@PathParam("id") int ruleId) {
        //todo
        return "to do";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(RestPublicMessage publicMessage) {
        logger.info(String.format("Start add publicmessage"));
        try {
            PublicMessageService.addPublicMessage(publicMessage);
        }
        catch (MorphRestException e) {
            return new Response(e.status, e.errorMsg, null);
        }
        return new Response(Constant.STATUS_OK, Constant.STATUS_OK_STR, null);
    }
}