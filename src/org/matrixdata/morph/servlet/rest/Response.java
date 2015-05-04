package org.matrixdata.morph.servlet.rest;

public class Response {
    public int status;

    public Object data;

    public Response(int statusIn, Object dataIn) {
        status = statusIn;
        data = dataIn;
    }
}
