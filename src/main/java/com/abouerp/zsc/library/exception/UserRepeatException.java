package com.abouerp.zsc.library.exception;

/**
 * @author techial
 */
public class UserRepeatException extends ClientErrorException {
    private static final Integer DEFAULT_CODE = 4001;
    private static final String DEFAULT_MSG = "User is Existing";

    public UserRepeatException() {
        super(DEFAULT_CODE, DEFAULT_MSG);
    }
}
