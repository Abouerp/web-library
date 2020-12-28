package com.abouerp.zsc.library.bean;

import java.io.Serializable;

/**
 * @author Abouerp
 */
public class ResultBean<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Integer DEFAULT_CODE = 200;
    private static final String DEFAULT_MSG = "OK";

    private final Integer code;

    private final String msg;

    private final T data;

    public ResultBean(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultBean() {
        this(DEFAULT_CODE, DEFAULT_MSG, null);
    }

    public ResultBean(Integer code, String msg) {
        this(code, msg, null);
    }

    public ResultBean(T data) {
        this(DEFAULT_CODE, DEFAULT_MSG, data);
    }

    public static <T> ResultBean<T> ok(T data) {
        return new ResultBean<>(data);
    }

    public static <T> ResultBean<T> ok() {
        return new ResultBean<>();
    }

    public static <T> ResultBean<T> of(Integer code, String msg) {
        return new ResultBean<>(code, msg);
    }

    public static <T> ResultBean<T> of(Integer code, String msg, T data) {
        return new ResultBean<>(code, msg, data);
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}

