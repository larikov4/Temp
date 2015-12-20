package com.epam.hw1.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @author Yevhen_Larikov
 */
@RunWith(MockitoJUnitRunner.class)
public class LoggingHandlerExceptionResolverTest {
    public static final String EXCEPTION_MESSAGE = "exception message";
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Object handler;

    @Mock
    private Exception exception;

    private LoggingHandlerExceptionResolver exceptionResolver = new LoggingHandlerExceptionResolver();

    @Test
    public void shouldReturnMinValueWhenQueriedForOrder(){
        assertEquals(Integer.MIN_VALUE, exceptionResolver.getOrder());
    }

    @Test
    public void shouldReturnProperModelAndView(){
        when(exception.getMessage()).thenReturn(EXCEPTION_MESSAGE);

        ModelAndView modelAndView = exceptionResolver.resolveException(request, response, handler, exception);

        assertEquals(LoggingHandlerExceptionResolver.VIEW_NAME, modelAndView.getViewName());
        assertEquals(EXCEPTION_MESSAGE, modelAndView.getModelMap()
                .get(LoggingHandlerExceptionResolver.ERROR_MESSAGE_ATTRIBUTE));
    }
}
