package com.abouerp.zsc.library.exception;

/**
 * @author Abouerp
 */
public class BadRequestException extends ClientErrorException {
    private static final Integer DEFAULT_CODE = 200;
    private static final String DEFAULT_MSG = "Bad Request";

    public BadRequestException() {
        super(DEFAULT_CODE, DEFAULT_MSG);
    }
}
