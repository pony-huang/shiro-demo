package com.ponking.filter;


import com.ponking.exception.GlobalException;
import com.ponking.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * @author Peng
 * @date 2020/6/29--12:02
 **/
@Slf4j
public class AuthTokenFilter implements Filter {

    @Override
    public void destroy() {
        log.info(this.getClass().getName()+" --- destroy ---");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info(this.getClass().getName()+" --- doFilter ---");

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String token = request.getHeader("X-Token");

        String uri = request.getRequestURI();

        // 方便测试,免过滤swagger
        String pattern = "^\\/swagger-ui.html.*";
        if(Pattern.matches(pattern,uri)){
            filterChain.doFilter(request,response);
            return;
        }

        if(uri.equals("/api/admin/index")){
            filterChain.doFilter(request,response);
            return;
        }

        if(token == null){
            throw new GlobalException("token为空,未进行认证登录");
        }
        if("/api/admin/logout".equals(uri)){
            filterChain.doFilter(request,response);
            return;
        }
        if(JwtUtil.isExpiration(token)){
            throw new GlobalException(("toke已过期"));
        }
        log.info("token = {}",token);
        filterChain.doFilter(request,response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info(this.getClass().getName()+" --- init ---");
    }
}
