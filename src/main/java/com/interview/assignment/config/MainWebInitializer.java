package com.interview.assignment.config;

import com.interview.assignment.common.ReadableRequestWrapperFilter;
import com.interview.assignment.common.SessionListener;
import com.interview.assignment.common.XSSRequestWrapperFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import java.util.EnumSet;

public class MainWebInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // DispatcherServlet 설정 & web-context 설정
        AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
        webContext.register(WebConfiguration.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(webContext);
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
        ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher", dispatcherServlet);

        servlet.addMapping("/");
        servlet.setLoadOnStartup(1);
        String osName = System.getProperty("os.name").toLowerCase();


        // root-context 설정
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(RootConfiguration.class);

        ContextLoaderListener listener = new ContextLoaderListener(rootContext);
        servletContext.addListener(listener);
        servletContext.addListener(new SessionListener());
        servletContext.getSessionCookieConfig().setName("MANAGEMENT_JSESSIONID");

        // filter 설정
        FilterRegistration.Dynamic filter = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        filter.setInitParameter("encoding", "UTF-8");
        filter.setInitParameter("forceEncoding", "true");

        FilterRegistration.Dynamic multipartFilter = servletContext.addFilter("MultipartFilter", MultipartFilter.class);
        multipartFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

//        FilterRegistration.Dynamic lucyXssFilter = servletContext.addFilter("XssEscapeServletFilter", XssEscapeServletFilter.class);
//        lucyXssFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

//        DelegatingFilterProxy springSecurityFilterChain = new DelegatingFilterProxy();
//        FilterRegistration.Dynamic springSecurity = servletContext.addFilter("springSecurityFilterChain", springSecurityFilterChain);
//        springSecurity.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");

        FilterRegistration.Dynamic xssRequestWrapperFilter = servletContext.addFilter("XssRequestWrapperFilter", XSSRequestWrapperFilter.class);
        xssRequestWrapperFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

        FilterRegistration.Dynamic readableRequestWrapperFilter = servletContext.addFilter("ReadableRequestWrapperFilter",
                ReadableRequestWrapperFilter.class);
        readableRequestWrapperFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }
}
