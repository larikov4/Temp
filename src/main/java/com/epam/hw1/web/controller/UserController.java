package com.epam.hw1.web.controller;

import com.epam.hw1.facade.BookingFacade;
import com.epam.hw1.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

/**
 * @author Yevhen_Larikov
 */
@Controller()
@RequestMapping("/users")
public class UserController {

    private BookingFacade facade;

    @Autowired
    public void setFacade(BookingFacade facade) {
        this.facade = facade;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public User getCustomerAccount(@PathVariable long id) {
        return facade.getUserById(id);
    }
}
