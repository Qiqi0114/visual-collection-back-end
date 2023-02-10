package com.example.collectiontableadministration.utils;

import cn.hutool.json.JSONTokener;
import com.alibaba.fastjson.JSON;
import com.example.collectiontableadministration.domain.response.vo.UserAndRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * JWT工具类
 *
 * @author 杨子涵
 */
public class JwtUtils {
    /**
     * 设置过期时间
      */
  private static final long EXPIRE = 60000 * 60 * 24 * 7;
    /**
     * 加密钥匙
     */
  private static final String SECRET = "xdClass.net168";
    /**
     * 令牌前缀
     */
  private static final String TOKEN_PREFIX = "xdclass";
    /**
     * subject
     */
  private static final String SUBJECT = "xdclass";

    /**
     * 根据用户信息 生成令牌
     * @param user 实体类
     * @return token
     */
  public static  String geneJsonWebToken(UserAndRole user){
     String token =  Jwts.builder().setSubject(SUBJECT)
              .claim("userJson", JSON.toJSONString(user))
              .setIssuedAt(new Date())
              .setExpiration(new Date(System.currentTimeMillis()+EXPIRE))
              .signWith(SignatureAlgorithm.HS256,SECRET).compact();
      token = TOKEN_PREFIX + token;
      return token;
  }
  public static Claims checkJwt(String token){
      try{
          final  Claims claims = Jwts.parser().setSigningKey(SECRET)
                  .parseClaimsJws(token.replace(TOKEN_PREFIX,"")).getBody();
              return claims;
      }catch (Exception e){
          return null;
      }
  }
}
