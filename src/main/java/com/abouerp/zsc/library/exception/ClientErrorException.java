package com.abouerp.zsc.library.exception;


import com.abouerp.zsc.library.bean.ResultBean;

/**
 * @author Abouerp
 */
public class ClientErrorException extends RuntimeException {

    private final Integer code;

    private final String msg;

    public ClientErrorException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public ResultBean<Object> getResultBean() {
        return ResultBean.of(code, msg);
    }
}
