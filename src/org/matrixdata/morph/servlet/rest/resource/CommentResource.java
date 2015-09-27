package org.matrixdata.morph.servlet.rest.resource;

import org.apache.log4j.Logger;
import org.matrixdata.morph.constant.Constant;
import org.matrixdata.morph.servlet.rest.Response;
import org.matrixdata.morph.servlet.rest.exception.MorphRestException;
import org.matrixdata.morph.servlet.rest.pojo.RestComment;
import org.matrixdata.morph.servlet.rest.service.CommentService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("comment")
public class CommentResource {
    Logger logger = Logger.getLogger(CommentResource.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{messageid:\\w+}")
    public Response getComment(@PathParam("messageid") String messageid) {
        logger.info("Start get public messages.");
        Response response = new Response(Constant.STATUS_OK, Constant.STATUS_OK_STR,
                CommentService.getComment(messageid));
        return response;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(RestComment comment) {
        logger.info(String.format("Start add comment"));
        try {
            CommentService.addComment(comment);
        }
        catch (MorphRestException e) {
            return new Response(e.status, e.errorMsg, null);
        }
        return new Response(Constant.STATUS_OK, Constant.STATUS_OK_STR, null);
    }
}