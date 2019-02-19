package com.springexp.correlation.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.springexp.correlation.Constants.X_CORRELATION_ID;

@Component
@Order(2) // defines the order of filters to be executed
@Slf4j
public class CorrelationIDFilter implements Filter {

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        log.info("Initializing filter :{}", this);
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        String xCorrelationID = req.getHeader(X_CORRELATION_ID);

        if (xCorrelationID != null) {
            log.info("Received request with Correlation ID : {}", xCorrelationID);
        } else {
            // TODO : generate a correlation ID
            xCorrelationID = "generated";
        }

        MDC.put(X_CORRELATION_ID, xCorrelationID);

        try {
            chain.doFilter(request, response);
        } finally {
            MDC.remove(X_CORRELATION_ID);
        }
    }

    @Override
    public void destroy() {
        log.warn("Destructing filter :{}", this);
    }
}
