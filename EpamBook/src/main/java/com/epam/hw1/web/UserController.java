package com.epam.hw1.web;


import com.epam.hw1.model.Note;
import com.epam.hw1.model.Timeline;
import com.epam.hw1.model.User;
import com.epam.hw1.repository.impl.UserRepositoryImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Yevhn on 20.12.2015.
 */
@Controller
public class UserController {
    private static Logger LOG = Logger.getLogger(UserController.class);

    @Autowired
    private UserRepositoryImpl repository;

    @RequestMapping(method = RequestMethod.POST, value = "/createUser",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void createUser(@RequestBody User user) {
        repository.addUser(user);
    }

    @RequestMapping(method = RequestMethod.POST,
            value = "/user/{username}/timeline/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void addNote(@PathVariable String username, @RequestBody Note note) {
        repository.addNote(username, note);
    }

    @RequestMapping(method = RequestMethod.GET,
            value = "/user/{username}/timeline/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Timeline getTimeline(@PathVariable String username) {
        return repository.getTimeline(username);
    }

    @RequestMapping(method = RequestMethod.POST,
            value = "/user/{username}/friend/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void makeFriends(@PathVariable String username, @RequestBody User friend) {
        repository.makeFriends(username, friend.getUsername());
    }

    @RequestMapping(method = RequestMethod.GET,
            value = "/user/{username}/friend/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<User> getFriends(@PathVariable String username) {
        return repository.getFriends(username);
    }

    @RequestMapping(method = RequestMethod.GET,
            value = "/user/{username}/friend/{friendUsername}/timeline",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Timeline getFriendTimeline(@PathVariable String username,
                                      @PathVariable String friendUsername) {
        if(!repository.isFrends(username, friendUsername)){
            return null;//TODO
        }
        return repository.getTimeline(friendUsername);
    }


    @RequestMapping(method = RequestMethod.POST,
            value = "/user/{username}/friend/{friendUsername}/timeline",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void addNoteToFriendTimeline(@PathVariable String username,
                                        @RequestBody Note note) {
        repository.getTimeline(username).addNote(new Note(username, note.getAuthor()));
    }
}
