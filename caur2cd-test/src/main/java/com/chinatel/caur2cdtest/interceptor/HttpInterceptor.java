package com.chinatel.caur2cdtest.interceptor;

import io.micrometer.core.instrument.util.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;

@Component
public class HttpInterceptor implements HandlerInterceptor {

    private final static Log log = LogFactory.getLog(HttpInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("the url of request:" + request.getRequestURL());
        log.info("  the path of request: " + request.getPathInfo());
        log.info("  Method: " + request.getMethod());
        log.info("  the param of request: " + request.getQueryString());
        log.info("      header: ");
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            log.info("          key=" + key + ",value=" + value);
        }
        log.info("      body: ");
        InputStream is = request.getInputStream();
        log.info("          " + IOUtils.toString(is, Charset.forName("UTF-8")));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
