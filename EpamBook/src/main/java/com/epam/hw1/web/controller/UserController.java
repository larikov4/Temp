package com.epam.hw1.web.controller;


import com.epam.hw1.exception.EpambookException;
import com.epam.hw1.exception.UserNotFoundException;
import com.epam.hw1.model.NoteBean;
import com.epam.hw1.model.TimelineBean;
import com.epam.hw1.model.UserBean;
import com.epam.hw1.service.impl.FriendServiceImpl;
import com.epam.hw1.service.impl.TimelineServiceImpl;
import com.epam.hw1.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Yevhen_Larikov on 20.12.2015.
 */
@Controller
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(method = RequestMethod.POST,
            value = "/createUser",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void createUser(@RequestBody UserBean userBean) {
        userService.addUser(userBean);
    }

}
