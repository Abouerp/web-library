package com.abouerp.zsc.library.exception;

/**
 * @author Abouerp
 */
public class BookDetailNotFoundException extends ClientErrorException {
    private static final Integer DEFAULT_CODE = 400;
    private static final String DEFAULT_MSG = "BookDetail Not Found";

    public BookDetailNotFoundException() {
        super(DEFAULT_CODE, DEFAULT_MSG);
    }
}
