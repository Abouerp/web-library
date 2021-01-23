package com.abouerp.zsc.library.exception;

/**
 * @author Abouerp
 */
public class ProblemManageNotFoundException extends ClientErrorException {
    private static final Integer DEFAULT_CODE = 400;
    private static final String DEFAULT_MSG = "ProblemManage Not Found";

    public ProblemManageNotFoundException() {
        super(DEFAULT_CODE, DEFAULT_MSG);
    }
}
