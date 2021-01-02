package com.abouerp.zsc.library.exception;

/**
 * @author Abouerp
 */
public class UserNotFoundException extends ClientErrorException {
    private static final Integer DEFAULT_CODE = 4002;
    private static final String DEFAULT_MSG = "User Not Found";

    public UserNotFoundException() {
        super(DEFAULT_CODE, DEFAULT_MSG);
    }
}
