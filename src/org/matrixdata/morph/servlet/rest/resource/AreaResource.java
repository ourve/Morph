package org.matrixdata.morph.servlet.rest.resource;

import ch.hsr.geohash.GeoHash;
import org.apache.log4j.Logger;
import org.matrixdata.morph.constant.Constant;
import org.matrixdata.morph.dal.AreaDAL;
import org.matrixdata.morph.servlet.rest.Response;
import org.matrixdata.morph.servlet.rest.exception.MorphRestException;
import org.matrixdata.morph.servlet.rest.pojo.RestArea;
import org.matrixdata.morph.servlet.rest.service.AreaService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("area")
public class AreaResource {
    Logger logger = Logger.getLogger(AreaResource.class);

    /*@GET
    @Path("/activeareas")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActiveAreas() {
        logger.info("Start get active areas.");
        Response response = new Response(Constant.STATUS_OK, Constant.STATUS_OK_STR, AreaService.getActiveAreas());
        return response;
    }*/

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addArea(RestArea area) {
        logger.info(String.format("Start add area. code = %s", area.areacode));
        try {
            AreaService.addArea(area);
        }
        catch (MorphRestException e) {
            return new Response(e.status, e.errorMsg, null);
        }
        return new Response(Constant.STATUS_OK, Constant.STATUS_OK_STR, null);
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{longitude:[\\d.]+}/{latitude:[\\d.]+}")
    public Response getArea(@PathParam("longitude") double longitude,
                            @PathParam("latitude") double latitude) {
        GeoHash geoHash = GeoHash.withCharacterPrecision(latitude, longitude, Constant.SMALLEST_AREA_CODE);
        String areaCode = AreaDAL.getInstance().getAreaFromGeohash(geoHash.toBase32());
        RestArea area = null;
        if (areaCode != null) {
            area = AreaDAL.getInstance().getArea(areaCode);
        }

        return new Response(Constant.STATUS_OK, Constant.STATUS_OK_STR, area);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAreas() {
        logger.info("Start get areas.");
        Response response = new Response(Constant.STATUS_OK, Constant.STATUS_OK_STR, AreaService.getAreas());
        return response;
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{areacode}")
    public Response deleteArea(@PathParam("areacode") String areacode) {
        logger.info("Start delete area.");

        try {
            AreaService.deleteArea(areacode);
        }
        catch (MorphRestException e) {
            return new Response(e.status, e.errorMsg, null);
        }

        return new Response(Constant.STATUS_OK, Constant.STATUS_OK_STR, null);
    }
}