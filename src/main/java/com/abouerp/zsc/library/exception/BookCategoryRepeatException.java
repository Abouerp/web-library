package com.abouerp.zsc.library.exception;

/**
 * @author Abouerp
 */
public class BookCategoryRepeatException extends ClientErrorException {
    private static final Integer DEFAULT_CODE = 4001;
    private static final String DEFAULT_MSG = "BookCategory is Existing";

    public BookCategoryRepeatException() {
        super(DEFAULT_CODE, DEFAULT_MSG);
    }
}
