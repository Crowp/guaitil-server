package com.guaitilsoft.config.security;

import org.springframework.beans.factory.annotation.Value;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WebSecurityCorsFilter implements Filter {

    @Value("${guaitil-client.cors}")
    private String urlCors;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        res.setHeader("Access-Control-Allow-Origin", this.urlCors);
        res.setHeader("Access-Control-Allow-Methods", this.urlCors);
        res.setHeader("Access-Control-Max-Age", "3600");
        res.setHeader("Access-Control-Allow-Headers", this.urlCors);
        chain.doFilter(request, res);
    }
    @Override
    public void destroy() {
    }
}