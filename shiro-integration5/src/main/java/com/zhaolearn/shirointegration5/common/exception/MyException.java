package com.zhaolearn.shirointegration5.common.exception;

public class MyException extends RuntimeException {
    public MyException(String msg) {
        super(msg);
    }

    public MyException() {
        super();
    }
}
