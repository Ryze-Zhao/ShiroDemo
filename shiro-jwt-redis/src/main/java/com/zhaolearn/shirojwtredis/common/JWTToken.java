package com.zhaolearn.shirojwtredis.common;

import org.apache.shiro.authc.AuthenticationToken;

public class JWTToken implements AuthenticationToken {
    // 密钥
    private String token;
    //后期需要加入如过期时间记录等多个信息。


    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
