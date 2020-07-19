package com.ponking.utils;

import com.ponking.constant.JwtAudienceConstants;
import com.ponking.exception.GlobalException;
import com.ponking.model.entity.User;
import com.ponking.service.PermissionService;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.*;

/**
 * @author Peng
 * @date 2020/6/25--15:26
 **/
@Slf4j
@Component
public class JwtUtil {

    @Autowired
    private PermissionService permissionService;

    /**
     * 解析jwt
     *
     * @param token
     * @return
     */
    private Claims getTokenBody(String token) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(JwtAudienceConstants.BASE64_SECRET))
                    .parseClaimsJws(token).getBody();
            return body;
        } catch (ExpiredJwtException eje) {
            throw new GlobalException("Token过期...");
        } catch (Exception e) {
            throw new GlobalException("token解析异常..");
        }
    }

    /**
     * 构建jwt
     *
     * @param user
     * @return
     */
    public String createToken(User user) {
        try {
            // 使用HS256加密算法
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
            //生成签名密钥
            byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(JwtAudienceConstants.BASE64_SECRET);
            Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
            long nowMillis = System.currentTimeMillis();
            long expMillis = nowMillis + JwtAudienceConstants.EXPIRES_SECOND * 1000;

            Map<String, Object> claims = new HashMap<>();
            List<String> roles = permissionService.listPermissionByUserName(user.getUsername());
            claims.put("roles",String.join(",",roles));
            String username = user.getUsername();
            //添加构成JWT的参数
            JwtBuilder builder = Jwts.builder()
                    .setHeaderParam("typ", "JWT")
                    .setSubject(username)
                    .setIssuer(JwtAudienceConstants.NAME)
                    .setIssuedAt(new Date(nowMillis))
                    .signWith(signatureAlgorithm, signingKey)
                    .setExpiration(new Date(expMillis))
                    .addClaims(claims);
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
    public String getUsername(String token) {
        return getTokenBody(token).getSubject();
    }

    /**
     * 获取角色
     * @param token
     * @return
     */
    public List<String> getRoles(String token){
        String roles = getTokenBody(token).get("roles", String.class);
        String[] split = roles.split(",");
        return Arrays.asList(split);
    }

    /**
     * 从token中获取用户ID
     *
     * @param token
     * @return
     */
    public String getUserId(String token) {
        return getTokenBody(token).get("userId", String.class);
    }


    /**
     * 是否已过期
     *
     * @param token
     * @return
     */
    public  boolean isExpiration(String token) {
        return getTokenBody(token).getExpiration().before(new Date());
    }
}
