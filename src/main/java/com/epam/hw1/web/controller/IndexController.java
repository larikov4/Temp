package com.epam.hw1.web.controller;

import com.epam.hw1.facade.BookingFacade;
import com.epam.hw1.model.Event;
import com.epam.hw1.model.impl.EventBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.IllegalFormatException;
import java.util.List;

/**
 * @author Yevhen_Larikov
 */
@Controller
@RequestMapping("/test")
public class IndexController {
    private static Logger LOG = Logger.getLogger(IndexController.class);


    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String ping() {
        LOG.error("ping...");
        return "ping";
    }

    @RequestMapping(value = "/e", method = RequestMethod.GET)
    @ResponseBody
    public String trowException() {
        LOG.error("test exception!");
        throw new RuntimeException("Test exception message");
    }
}


