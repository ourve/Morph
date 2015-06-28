package org.matrixdata.morph.servlet.rest;

public class Response {
    public int status;

    public String errorMsg;

    public Object data;

    public Response(int statusIn, String errorMsgIn, Object dataIn) {
        status = statusIn;
        errorMsg = errorMsgIn;
        data = dataIn;
    }
}
