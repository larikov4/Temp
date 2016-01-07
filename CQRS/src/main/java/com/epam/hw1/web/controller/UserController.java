package com.epam.hw1.web.controller;


import com.epam.hw1.model.TimelineBean;
import com.epam.hw1.model.UserBean;
import com.epam.hw1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

/**
 * Gives REST API for manipulation user entities.
 *
 * @author Yevhen_Larikov on 20.12.2015.
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * Adds passed user to inner repository.
     * @param userBean the user
     */
    @RequestMapping(method = RequestMethod.POST,
            value = "/createUser",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void createUser(@RequestBody UserBean userBean) {
        userService.addUser(userBean);
    }

}
