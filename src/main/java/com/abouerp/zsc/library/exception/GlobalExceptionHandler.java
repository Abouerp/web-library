package com.abouerp.zsc.library.exception;

import com.abouerp.zsc.library.bean.ResultBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Abouerp
 */
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ClientErrorException.class)
    public HttpEntity<ResultBean<Object>> clientErrorException(ClientErrorException ex) {
        return handleException(ex);
    }

    private static ResponseEntity<ResultBean<Object>> handleException(ClientErrorException exception) {
        return ResponseEntity.status(exception.getCode())
                .body(exception.getResultBean());
    }

}