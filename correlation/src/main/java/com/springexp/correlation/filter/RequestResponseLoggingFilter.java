package com.springexp.correlation.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(1) // defines the order of filters to be executed
@Slf4j
public class RequestResponseLoggingFilter implements Filter {

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        log.info("Initializing filter :{}", this);
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        log.info("Logging Request  {} : {}", req.getMethod(), req.getRequestURI());
        chain.doFilter(request, response);
        log.info("Logging Response {} {}", res.getContentType(), res.getStatus());
    }

    @Override
    public void destroy() {
        log.warn("Destructing filter :{}", this);
    }
}
