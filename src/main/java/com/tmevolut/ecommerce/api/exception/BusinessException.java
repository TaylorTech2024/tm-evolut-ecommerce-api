package com.tmevolut.ecommerce.api.exception;

public class BusinessException extends RuntimeException {

    public BusinessException(String msg) {
        super(msg);
    }
}