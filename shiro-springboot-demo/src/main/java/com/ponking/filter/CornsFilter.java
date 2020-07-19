package com.ponking.filter;


import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.cors.CorsUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Peng
 * @date 2020/6/25--13:55
 **/
//@Component
public class CornsFilter extends GenericFilter {
    /**
     * Content token header name.
     */
    public final static String API_ACCESS_KEY_HEADER_NAME = "API-" + HttpHeaders.AUTHORIZATION;
    /**
     * Admin token header name.
     */
    public final static String ADMIN_TOKEN_HEADER_NAME = "ADMIN-" + HttpHeaders.AUTHORIZATION;


    private final static String ALLOW_HEADERS = StringUtils.joinWith(",", HttpHeaders.CONTENT_TYPE, ADMIN_TOKEN_HEADER_NAME, API_ACCESS_KEY_HEADER_NAME);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        // Set customized header
        String originHeaderValue = httpServletRequest.getHeader(HttpHeaders.ORIGIN);
        if (StringUtils.isNotBlank(originHeaderValue)) {
            httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, originHeaderValue);
        }
        httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, ALLOW_HEADERS);
        httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, DELETE, OPTIONS");
        httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600");

        if (!CorsUtils.isPreFlightRequest(httpServletRequest)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }
}
