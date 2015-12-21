package com.epam.hw1.web;


import com.epam.hw1.model.NoteBean;
import com.epam.hw1.model.TimelineBean;
import com.epam.hw1.model.UserBean;
import com.epam.hw1.service.impl.FriendServiceImpl;
import com.epam.hw1.service.impl.TimelineServiceImpl;
import com.epam.hw1.service.impl.UserServiceImpl;
import org.apache.log4j.Logger;
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
    @Autowired
    private TimelineServiceImpl timelineService;
    @Autowired
    private FriendServiceImpl friendService;

    @RequestMapping(method = RequestMethod.POST,
            value = "/createUser",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void createUser(@RequestBody UserBean userBean) {
        userService.addUser(userBean);
    }

    @RequestMapping(method = RequestMethod.POST,
            value = "/user/{username}/timeline/",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void addNote(@PathVariable String username, @RequestBody NoteBean noteBean) {
        timelineService.addNote(username, noteBean);
    }

    @RequestMapping(method = RequestMethod.POST,
            value = "/user/{username}/friend/",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void makeFriends(@PathVariable String username, @RequestBody UserBean friend) {
        friendService.makeFriends(username, friend.getUsername());
        //ResponseEntity
    }

    @RequestMapping(method = RequestMethod.POST,
            value = "/user/{username}/friend/{friendUsername}/timeline",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void addNoteToFriendTimeline(@PathVariable String username,
                                        @PathVariable String friendUsername,
                                        @RequestBody NoteBean noteBean) {
        if (!friendService.isFriends(username, friendUsername)) {
//            return null;//TODO
        }
        timelineService.getTimelineBean(friendUsername).addNote(new NoteBean(noteBean.getNote(), username));
    }

    @RequestMapping(method = RequestMethod.GET,
            value = "/user/{username}/timeline/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public TimelineBean getTimeline(@PathVariable String username) {
        return timelineService.getTimelineBean(username);
    }

    @RequestMapping(method = RequestMethod.GET,
            value = "/user/{username}/friend/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<String> getFriends(@PathVariable String username) {
        return friendService.getFriends(username);
    }


    @RequestMapping(method = RequestMethod.GET,
            value = "/user/{username}/friend/{friendUsername}/timeline",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public TimelineBean getFriendTimeline(@PathVariable String username,
                                          @PathVariable String friendUsername) {
        if (!friendService.isFriends(username, friendUsername)) {
            return null;//TODO
        }
        return timelineService.getTimelineBean(friendUsername);
    }
}
