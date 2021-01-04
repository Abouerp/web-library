package com.abouerp.zsc.library.exception;

/**
 * @author Abouerp
 */
public class PasswordNotMatchException extends ClientErrorException {
    private static final Integer DEFAULT_CODE = 200;
    private static final String DEFAULT_MSG = "Password Not Match";

    public PasswordNotMatchException() {
        super(DEFAULT_CODE, DEFAULT_MSG);
    }
}
