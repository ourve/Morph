package org.matrixdata.morph.servlet.rest.exception;

public class MorphRestException extends Exception {
    public int status;

    public String errorMsg;

    public MorphRestException(int statusIn, String errorMsgIn) {
        status = statusIn;
        errorMsg = errorMsgIn;
    }
}