package com.epam.hw1.web;

import org.apache.log4j.Logger;
import org.springframework.core.*;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoggingHandlerExceptionResolver implements HandlerExceptionResolver, Ordered {
    private static Logger LOG = Logger.getLogger(LoggingHandlerExceptionResolver.class);
    private static final String VIEW_NAME = "exceptionView";
    public static final String ERROR_MESSAGE_ATTRIBUTE = "message";

    public int getOrder() {
        return Integer.MIN_VALUE;
    }

    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response,
                                         Object handler,
                                         Exception ex) {
        LOG.error("Exception is occurred: ", ex);
        return new ModelAndView(VIEW_NAME, new ModelMap(ERROR_MESSAGE_ATTRIBUTE, ex.getMessage()));
    }
}