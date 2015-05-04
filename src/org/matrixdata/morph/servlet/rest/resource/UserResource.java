package org.matrixdata.morph.servlet.rest.resource;

import org.apache.log4j.Logger;
import org.matrixdata.morph.servlet.rest.Response;
import org.matrixdata.morph.servlet.rest.pojo.RestUser;
import org.matrixdata.morph.servlet.rest.service.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "user" path)
 */
@Path("user")
public class UserResource {
    Logger logger = Logger.getLogger(UserResource.class);

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        logger.info("Start get users.");
        Response response = new Response(200, UserService.getUsers());
        return response;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("{id:\\d+}")
    public String getUser(@PathParam("id") int ruleId) {
        logger.info("Start get users.");

        return "Got it!";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(RestUser user) {
        logger.info(String.format("Start add user. id = %s", user.id));
        UserService.addUser(user);
        return new Response(200, null);
    }


}