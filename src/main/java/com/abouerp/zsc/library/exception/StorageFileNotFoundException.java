package com.abouerp.zsc.library.exception;

/**
 * @author Abouerp
 */
public class StorageFileNotFoundException extends ClientErrorException {
    private static final Integer DEFAULT_CODE = 200;
    private static final String DEFAULT_MSG = "Storage File Not Found";

    public StorageFileNotFoundException() {
        super(DEFAULT_CODE, DEFAULT_MSG);
    }
}
