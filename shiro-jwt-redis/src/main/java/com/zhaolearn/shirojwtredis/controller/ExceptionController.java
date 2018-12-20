package com.zhaolearn.shirojwtredis.controller;

import com.zhaolearn.shirojwtredis.common.ResultDTO;
import com.zhaolearn.shirojwtredis.exception.UnauthorizedException;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionController {
    // 捕捉shiro的异常
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public ResultDTO handle401(ShiroException e) {
        return new ResultDTO(401, "请输入正确参数", null);
    }
    // 捕捉UnauthorizedException
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ResultDTO handle401(Exception e) {
        return new ResultDTO(401, e.getMessage(), null);
    }
    // 捕捉其他所有异常
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultDTO globalException(HttpServletRequest request, Throwable ex) {
        return new ResultDTO(getStatus(request).value(), ex.getMessage(), null);
    }
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}

