package com.zhaolearn.shirointegration5.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zhaolearn.shirointegration5.common.config.Constant;

import java.util.Date;

public class JWTUtil {
    /**
     * JWT-密钥名字
     */
    private static final String JWT_CLAIM="RYZEZHAO";
    /**
     * JWT-算法的盐
     */
    private static final String JWT_SALT="RYZEZHAO";

    /**
     * 校验token是否正确
     * @param token 密钥
     * @param passWord 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, String userName, String passWord) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SALT+passWord);
            JWTVerifier verifier = JWT.require(algorithm).withClaim(JWT_CLAIM, userName).build();
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
            return jwt.getClaim(JWT_CLAIM).asString();
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
        // 过期时间30分钟
        try {
            Date date = new Date(System.currentTimeMillis()+ Constant.EXRP_MS_HALFHOUR);
            Algorithm algorithm = Algorithm.HMAC256(JWT_SALT+passWord);
            return JWT.create().withClaim(JWT_CLAIM, userName).withExpiresAt(date).sign(algorithm);
        } catch (Exception e) {
            return null;
        }
    }
}
