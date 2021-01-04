package com.abouerp.zsc.library.exception;

/**
 * @author Abouerp
 */
public class BookNotFoundException extends ClientErrorException {
    private static final Integer DEFAULT_CODE = 200;
    private static final String DEFAULT_MSG = "Book Not Found";

    public BookNotFoundException() {
        super(DEFAULT_CODE, DEFAULT_MSG);
    }
}
