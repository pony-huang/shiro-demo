package com.ponking.utils;

import com.ponking.common.JwtAudienceConstant;
import com.ponking.exception.GlobalException;
import com.ponking.model.entity.User;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Peng
 * @date 2020/6/25--15:26
 **/
public class JwtUtil {

    private static Logger log = LoggerFactory.getLogger(JwtUtil.class);


    /**
     * 解析jwt
     * @param token
     * @return
     */
    public static Claims parseJWT(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(JwtAudienceConstant.BASE64_SECRET))
                    .parseClaimsJws(token).getBody();
            return claims;
        } catch (ExpiredJwtException eje) {
            throw new GlobalException("Token过期...");
        } catch (Exception e) {
            throw new GlobalException("token解析异常..");
        }
    }

    /**
     * 构建jwt
     * @param user
     * @return
     */
    public static String createToken(User user) {
        try {
            // 使用HS256加密算法
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

            long nowMillis = System.currentTimeMillis();

            //生成签名密钥
            byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(JwtAudienceConstant.BASE64_SECRET);
            Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());


            Map<String,Object> claims = new HashMap<>();
            claims.put("userId",user.getId());
            claims.put("username",user.getUsername());

            //添加构成JWT的参数
            JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                    .setSubject(user.getUsername())
                    .setIssuer(JwtAudienceConstant.CLIENT_ID)
                    .setIssuedAt(new Date())
                    .setAudience(JwtAudienceConstant.NAME)
                    .signWith(signatureAlgorithm, signingKey);
            builder.setClaims(claims);
            //添加Token过期时间
            int expiresSecond = JwtAudienceConstant.EXPIRES_SECOND;
            if (expiresSecond >= 0) {
                long expMillis = nowMillis + expiresSecond;
                Date exp = new Date(expMillis);
                builder.setExpiration(exp);
            }
            //生成JWT
            return builder.compact();
        } catch (Exception e) {
            log.error("签名失败", e);
            throw new RuntimeException();
        }
    }


    /**
     * 从token中获取用户名
     *
     * @param token
     * @return
     */
//    public static String getUsername(String token) {
//        return parseJWT(token).getSubject();
//    }

    /**
     * 从token中获取用户ID
     * @param token
     * @return
     */
    public static String getUserId(String token){
        return parseJWT(token).get("userId", String.class);
    }

    /**
     * 从token中获取用户ID
     * @param token
     * @return
     */
    public static String getUsername(String token){
        return parseJWT(token).get("username", String.class);
    }

    /**
     * 是否已过期
     *
     * @param token
     * @return
     */
    public static boolean isExpiration(String token) {
        return parseJWT(token).getExpiration().before(new Date());
    }

    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        User user = new User();
        user.setId("12345678");
        user.setNickName("HelloWorld");
        user.setUsername("admin");
        String token = createToken(user);
        System.out.println(token);

        System.out.println("userId:"+getUserId(token));
        System.out.println("username:"+getUsername(token));

    }
}
