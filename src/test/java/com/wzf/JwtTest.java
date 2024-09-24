package com.wzf;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    @Test
    public void testGen(){
        Map<String, Object> claims = new HashMap<>();
        claims.put("id",1);
        claims.put("username","张三");
        //生成jwt的代码
        String token = JWT.create()
                .withClaim("user", claims)//添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis()))//添加过期时间
                .sign(Algorithm.HMAC256("itheima"));//指定算法，配置密钥
        System.out.println(token);
    }

    @Test
    public void testParse(){
       String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9" +
               ".eyJjbGFpbXMiOnsiaWQiOjIsInVzZXJuYW1lIjoid2FuZ2JhIn0sImV4cCI6MTcyNDIwMzM3Mn0" +
               ".Oxt6VvXaMv9pZpf5PYuafRJ8-CRPgT_fCoTe7MG4hiE";
       JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("itheima")).build();
       DecodedJWT decodedJWT = jwtVerifier.verify(token);//验证token， 生成一个解析后的JWT对象
        Map<String, Claim> claims = decodedJWT.getClaims();
        System.out.println(claims.get("user"));

        //如果篡改了头部和载荷部分的数据，那么验证失败
        //如果密钥改了，验证失败
        //token过期
    }
}
