package com.abouerp.zsc.library.exception;

/**
 * @author Abouerp
 */
public class ExcelErrorException extends ClientErrorException {
    private static final Integer DEFAULT_CODE = 400;
    private static final String DEFAULT_MSG = "Excel Error";

    public ExcelErrorException() {
        super(DEFAULT_CODE, DEFAULT_MSG);
    }
}
