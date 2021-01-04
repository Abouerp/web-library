package com.abouerp.zsc.library.exception;

/**
 * @author Abouerp
 */
public class BookCategoryNotFoundException extends ClientErrorException {
    private static final Integer DEFAULT_CODE = 200;
    private static final String DEFAULT_MSG = "BookCategory Not Found";

    public BookCategoryNotFoundException() {
        super(DEFAULT_CODE, DEFAULT_MSG);
    }
}
