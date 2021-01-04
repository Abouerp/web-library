package com.abouerp.zsc.library.exception;

/**
 * @author Abouerp
 */
public class UnauthorizedException extends ClientErrorException {
    private static final Integer DEFAULT_CODE = 401;
    private static final String DEFAULT_MSG = "Unauthorized";

    public UnauthorizedException() {
        super(DEFAULT_CODE, DEFAULT_MSG);
    }
}
