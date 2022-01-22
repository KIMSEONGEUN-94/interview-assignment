package com.interview.assignment.common;

import com.navercorp.lucy.security.xss.servletfilter.XssEscapeFilter;
import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilterWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import javax.servlet.*;
import java.io.IOException;
import java.util.Objects;

@Slf4j
public class XSSRequestWrapperFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        if (isRequestBody(request)) {
            chain.doFilter(new XSSRequestWrapper(request), response);
        } else {
            chain.doFilter(new XssEscapeServletFilterWrapper(request, xssEscapeFilter), response);
        }
    }

    @Override
    public void destroy() {

    }

    private boolean isRequestBody(ServletRequest req) {
        return Objects.nonNull(req.getContentType()) && req.getContentType().contains(MediaType.APPLICATION_JSON_VALUE);
    }

    private final XssEscapeFilter xssEscapeFilter = XssEscapeFilter.getInstance();
}
