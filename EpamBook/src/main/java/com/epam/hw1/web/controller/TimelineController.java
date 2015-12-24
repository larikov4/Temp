package com.epam.hw1.web.controller;

import com.epam.hw1.exception.EpambookException;
import com.epam.hw1.exception.UserNotFoundException;
import com.epam.hw1.exception.UsersAreNotFriendsException;
import com.epam.hw1.model.NoteBean;
import com.epam.hw1.model.TimelineBean;
import com.epam.hw1.service.TimelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Gives REST API for manipulation timeline entities.
 *
 * @author Yevhen_Larikov
 */
@Controller
public class TimelineController {
    @Autowired
    private TimelineService timelineService;

    /**
     * Adds note to passed user's timeline.
     * @param username the username
     * @param noteBean the note with author
     * @throws UserNotFoundException is thrown when user wasn't found.
     */
    @RequestMapping(method = RequestMethod.POST,
            value = "/user/{username}/timeline/",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addNote(@PathVariable String username, @RequestBody NoteBean noteBean) throws UserNotFoundException {
        timelineService.addNote(username, noteBean);
    }

    /**
     * Adds note to user's friends's timeline. Author of note is user.
     * @param username the username
     * @param friendUsername the friend username
     * @param noteBean the note bean
     * @throws UserNotFoundException is thrown when user wasn't found.
     * @throws UsersAreNotFriendsException is thrown when users aren't friends.
     */
    @RequestMapping(method = RequestMethod.POST,
            value = "/user/{username}/friend/{friendUsername}/timeline",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addNoteToFriendTimeline(@PathVariable String username,
                                        @PathVariable String friendUsername,
                                        @RequestBody NoteBean noteBean)
                                        throws UserNotFoundException, UsersAreNotFriendsException {
        timelineService.getFriendTimelineBean(username, friendUsername)
                .addNote(new NoteBean(noteBean.getNote(), username));
    }

    /**
     * Returns a timeline of passed user.
     * @param username the username timeline owner
     * @return the timeline bean
     * @throws UserNotFoundException is thrown when user wasn't found.
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/user/{username}/timeline/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public TimelineBean getTimeline(@PathVariable String username) throws UserNotFoundException {
        return timelineService.getTimelineBean(username);
    }

    /**
     * Returns a timeline of passed user's friend.
     * @param username the username
     * @param friendUsername the friend username
     * @return the timeline bean
     * @throws UserNotFoundException is thrown when user wasn't found.
     * @throws UsersAreNotFriendsException is thrown when users aren't friends.
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "/user/{username}/friend/{friendUsername}/timeline",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public TimelineBean getFriendTimeline(@PathVariable String username,
                                          @PathVariable String friendUsername) throws EpambookException {
        return timelineService.getFriendTimelineBean(username, friendUsername);
    }
}
