package com.wmz.blockchain.controller;

import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by wmz on 2018/3/17.
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {
    private static Logger logger = Logger.getLogger(WebMvcConfigurer.class);

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {

        registry.addInterceptor(new HandlerInterceptorAdapter() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                Enumeration<String> headerNames = request.getHeaderNames();
                while (headerNames.hasMoreElements()) {
                    String s = headerNames.nextElement();
                    logger.info("header--" + s + ": " + request.getHeader(s));
                }
                Enumeration paramNames = request.getParameterNames();
                logger.info("url--" + request.getRequestURL());
                logger.info("ip--" + request.getRemoteAddr());
                while (paramNames.hasMoreElements()) {
                    String paramName = (String) paramNames.nextElement();
                    logger.info("body--" + paramName + ": " + request.getParameter(paramName));
                }
                logger.info("QueryString--" + request.getQueryString());

                return true;
            }
        }).addPathPatterns("/**");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new JsonHttpMessageConverter());
    }
}



