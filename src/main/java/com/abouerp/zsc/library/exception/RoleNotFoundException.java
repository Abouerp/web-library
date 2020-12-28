package com.abouerp.zsc.library.exception;

/**
 * @author Abouerp
 */
public class RoleNotFoundException extends ClientErrorException {
    private static final Integer DEFAULT_CODE = 400;
    private static final String DEFAULT_MSG = "Role Not Found";

    public RoleNotFoundException() {
        super(DEFAULT_CODE, DEFAULT_MSG);
    }
}
