package com.zhaolearn.shirointegration5.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JWTUtil {
    // 过期时间30分钟
    private static final long EXPIRE_TIME = 30*60*1000;
    // 密钥名字
    private static final String CLAIM="RYZEZHAO";
    // 算法的盐
    private static final String SALT="RYZEZHAO";
    /**
     * 校验token是否正确
     * @param token 密钥
     * @param passWord 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String userName, String passWord) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SALT+passWord);
            JWTVerifier verifier = JWT.require(algorithm).withClaim(CLAIM, userName).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }
    /**
     *给入token获取UserName
     * @return token中包含的用户名
     */
    public static String getUserName(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(CLAIM).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
    /**
     * 生成签名,EXPIRE_TIME 后过期
     * @param userName 用户名
     * @param passWord 用户的密码
     * @return 加密的token
     */
    public static String sign(String userName, String passWord) {
        try {
            Date date = new Date(System.currentTimeMillis()+EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(SALT+passWord);
            return JWT.create().withClaim(CLAIM, userName).withExpiresAt(date).sign(algorithm);
        } catch (Exception e) {
            return null;
        }
    }
}
