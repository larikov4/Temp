package com.epam.hw1.web.controller;

import com.epam.hw1.exception.UserNotFoundException;
import com.epam.hw1.exception.UsersAreNotFriendsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by Yevhn on 23.12.2015.
 */
@Controller
public class ExceptionHandlingController {
    public static final String ERROR_MESSAGE_ATTRIBUTE = "error";

    @ExceptionHandler(UserNotFoundException.class)
    public ModelMap handleUserNotFound(){
        return new ModelMap(ERROR_MESSAGE_ATTRIBUTE, "Cannot find user.");
    }

    @ExceptionHandler(UsersAreNotFriendsException.class)
    public ModelMap handleUsersAreNotFriendsException(){
        return new ModelMap(ERROR_MESSAGE_ATTRIBUTE, "Users are not friends.");
    }
}
