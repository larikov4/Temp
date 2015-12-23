package com.epam.hw1.web.controller;

import com.epam.hw1.exception.EpambookException;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by Yevhn on 23.12.2015.
 */
@ControllerAdvice
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {
    public static final String ERROR_MESSAGE_ATTRIBUTE = "error";

    @ExceptionHandler(EpambookException.class)
    @ResponseBody
    public ModelMap handleEpambookException(EpambookException ex){
        return new ModelMap(ERROR_MESSAGE_ATTRIBUTE, ex.getMessage());
    }
}
