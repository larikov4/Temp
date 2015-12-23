package com.epam.hw1.web.controller;

import com.epam.hw1.exception.EpambookException;
import com.epam.hw1.exception.UserNotFoundException;
import com.epam.hw1.model.NoteBean;
import com.epam.hw1.model.TimelineBean;
import com.epam.hw1.service.TimelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Yevhen_Larikov
 */
@Controller
public class TimelineController {
    @Autowired
    private TimelineService timelineService;

    @RequestMapping(method = RequestMethod.POST,
            value = "/user/{username}/timeline/",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addNote(@PathVariable String username, @RequestBody NoteBean noteBean) throws UserNotFoundException {
        timelineService.addNote(username, noteBean);
    }

    @RequestMapping(method = RequestMethod.POST,
            value = "/user/{username}/friend/{friendUsername}/timeline",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addNoteToFriendTimeline(@PathVariable String username,
                                        @PathVariable String friendUsername,
                                        @RequestBody NoteBean noteBean) throws UserNotFoundException {
        timelineService.getTimelineBean(friendUsername).addNote(new NoteBean(noteBean.getNote(), username));
    }

    @RequestMapping(method = RequestMethod.GET,
            value = "/user/{username}/timeline/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public TimelineBean getTimeline(@PathVariable String username) throws UserNotFoundException {
        return timelineService.getTimelineBean(username);
    }


    @RequestMapping(method = RequestMethod.GET,
            value = "/user/{username}/friend/{friendUsername}/timeline",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public TimelineBean getFriendTimeline(@PathVariable String username,
                                          @PathVariable String friendUsername) throws EpambookException {
        return timelineService.getFriendTimelineBean(username, friendUsername);
    }
}
