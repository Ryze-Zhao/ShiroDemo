package com.zhaolearn.shirojwtredis.exception;

public class MyException extends RuntimeException {
    public MyException(String msg) {
        super(msg);
    }

    public MyException() {
        super();
    }
}
