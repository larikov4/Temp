package com.epam.hw1.web.controller.handler;

import com.epam.hw1.exception.EpambookException;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Handles exceptions occurred in controllers.
 *
 * Created by Yevhen_Larikov on 23.12.2015.
 */
@ControllerAdvice
public class ExceptionHandlingController {
    private static final Logger LOG = Logger.getLogger(ExceptionHandlingController.class);
    public static final String ERROR_MESSAGE_ATTRIBUTE = "error";

    /**
     * Handles epambook exceptions sending response with error message.
     */
    @ExceptionHandler(EpambookException.class)
    @ResponseBody
    public ModelMap handleEpambookException(EpambookException ex){
        LOG.debug("Epambook exception is occurred: ", ex);
        return new ModelMap(ERROR_MESSAGE_ATTRIBUTE, ex.getMessage());
    }

    /**
     * Handles unexpected exceptions sending 500 HTTP response.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleEpambookException(Exception ex){
        LOG.debug("Unexpected error is occurred: ", ex);
    }
}
