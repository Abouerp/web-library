package com.abouerp.zsc.library.exception;

/**
 * @author Abouerp
 */
public class ModularNotFoundException extends ClientErrorException {
    private static final Integer DEFAULT_CODE = 400;
    private static final String DEFAULT_MSG = "ModularVO Not Found Request";

    public ModularNotFoundException() {
        super(DEFAULT_CODE, DEFAULT_MSG);
    }
}
