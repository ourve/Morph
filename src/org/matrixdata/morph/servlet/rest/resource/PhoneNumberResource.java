package org.matrixdata.morph.servlet.rest.resource;

import org.apache.log4j.Logger;
import org.matrixdata.morph.constant.Constant;
import org.matrixdata.morph.dal.PhoneNumberDAL;
import org.matrixdata.morph.servlet.rest.Response;
import org.matrixdata.morph.servlet.rest.exception.MorphRestException;
import org.matrixdata.morph.servlet.rest.pojo.RestPhoneNumber;
import org.matrixdata.morph.servlet.rest.service.PhoneNumberService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("phonenumber")
public class PhoneNumberResource {
    Logger logger = Logger.getLogger(PhoneNumberResource.class);

    @GET
    @Path("{phonenumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPhoneNumber(@PathParam("phonenumber") String phoneNumber) {
        logger.info("Start get phone number.");
        RestPhoneNumber restPhoneNumber = null;
        try {
            restPhoneNumber = PhoneNumberService.getRestPhoneNumber(phoneNumber);
        }
        catch (MorphRestException e) {
            return new Response(e.status, e.errorMsg, null);
        }

        return new Response(Constant.STATUS_OK, Constant.STATUS_OK_STR, restPhoneNumber);
    }
}