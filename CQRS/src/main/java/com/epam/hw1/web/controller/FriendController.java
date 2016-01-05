package com.epam.hw1.web.controller;

import com.epam.hw1.exception.UserNotFoundException;
import com.epam.hw1.model.UserBean;
import com.epam.hw1.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Gives REST API for manipulation friends entities.
 *
 * @author Yevhen_Larikov
 */
@Controller
public class FriendController {
    @Autowired
    private FriendService friendService;

    /**
     * Makes two users friends. Friend is symmetric relation.
     * @param username the username
     * @param friend the friend
     * @throws UserNotFoundException is thrown when user wasn't found.
     */
    @RequestMapping(method = RequestMethod.POST,
            value = "/user/{username}/friend/",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void makeFriends(@PathVariable String username, @RequestBody UserBean friend) throws UserNotFoundException {
        friendService.makeFriends(username, friend.getUsername());
    }

    /**
     * Returns all friend of user by passed username.
     * @param username the username
     * @return list of friends
     * @throws UserNotFoundException is thrown when user wasn't found.
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/user/{username}/friend/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<String> getFriends(@PathVariable String username) throws UserNotFoundException {
        return friendService.getFriends(username);
    }


}
