package com.ponking.filter;


import com.ponking.constant.AuthorizationConstants;
import com.ponking.constant.JwtAudienceConstants;
import com.ponking.exception.GlobalException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.Date;

/**
 * @author Peng
 * @date 2020/6/29--12:02
 **/
@Slf4j
public class AuthTokenFilter implements Filter {

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("AuthTokenFilter 执行了....");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String token = request.getHeader(AuthorizationConstants.TOKEN_HEADER);
        if(token == null){
            throw new GlobalException("token为空,未进行认证登录");
        }
        boolean isExp = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(JwtAudienceConstants.BASE64_SECRET))
                .parseClaimsJws(token).getBody().getExpiration().before(new Date());
        if(isExp){
            throw new GlobalException(("toke已过期"));
        }
        filterChain.doFilter(request,response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
}
