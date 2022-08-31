package com.watermelonhit.blog.util;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录使用JWT技术。
 *
 * jwt 可以生成 一个加密的token，做为用户登录的令牌，当用户登录成功之后，发放给客户端。
 *
 * 请求需要登录的资源或者接口的时候，将token携带，后端验证token是否合法。
 *
 * jwt 有三部分组成：A.B.C
 *
 * A：Header，{"type":"JWT","alg":"HS256"} 固定
 *
 * B：playload，存放信息，比如，用户id，过期时间等等，可以被解密，不能存放敏感信息
 *
 * C:  签证，A和B加上秘钥 加密而成，只要秘钥不丢失，可以认为是安全的。
 *
 * jwt 验证，主要就是验证C部分 是否合法。
 * @Author watermelonhit
 * @DateTime 2021/8/26
 */
public class JWTUtils {
    //密钥
    private static final String jwtToken = "sus985211watermelonhit!###";
    //生成令牌
    public static String createToken(Long userId){
        //B部分
        Map<String,Object> claims = new HashMap<>();
        claims.put("userId",userId);
        //C部分
        JwtBuilder jwtBuilder = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, jwtToken) // 签发算法，秘钥为jwtToken
                .setClaims(claims) // body数据，要唯一，自行设置
                .setIssuedAt(new Date()) // 设置签发时间
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 60 * 1000));// 一天的有效时间
        String token = jwtBuilder.compact();
        return token;
    }
    //解析
    public static Map<String, Object> checkToken(String token){
        try {
            Jwt parse = Jwts.parser().setSigningKey(jwtToken).parse(token);
            return (Map<String, Object>) parse.getBody();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }

}

