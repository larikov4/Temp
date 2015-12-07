package com.epam.hw1.web.controller;

import com.epam.hw1.facade.BookingFacade;
import com.epam.hw1.model.Event;
import com.epam.hw1.model.User;
import com.epam.hw1.model.impl.EventBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author Yevhen_Larikov
 */
@Controller
@RequestMapping("/events")
public class EventController {

    private BookingFacade facade;

    @Autowired
    public void setFacade(BookingFacade facade) {
        this.facade = facade;
    }

    @RequestMapping(method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public Event getEventById(@RequestParam("id") long id) {
        return facade.getEventById(id);
    }

    @RequestMapping(method = RequestMethod.GET, params = "title",
            produces = "application/json")
    @ResponseBody
    public List<Event> getEventsByTitle(@RequestParam("title") String title,
                                        @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
                                        @RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {
        return facade.getEventsByTitle(title, pageSize, pageNum);
    }

    @RequestMapping(method = RequestMethod.GET, params = "day",
            produces = "application/json")
    @ResponseBody
    public List<Event> getEventsForDay(@RequestParam("day") @DateTimeFormat(pattern="yyyy-MM-dd") Date day,
                                        @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
                                        @RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {
        return facade.getEventsForDay(day, pageSize, pageNum);
    }

    @RequestMapping(method = RequestMethod.POST,
            produces = "application/json")
    @ResponseBody
    public Event createEvent(@RequestBody EventBean event) {
        return facade.createEvent(event);
    }

    @RequestMapping(method = RequestMethod.PUT,
            produces = "application/json")
    @ResponseBody
    public Event updateEvent(@RequestBody EventBean event) {
        return facade.updateEvent(event);
    }

    @RequestMapping(value = "/{eventId}", method = RequestMethod.DELETE,
            produces = "application/json")
    @ResponseBody
    public boolean deleteEvent(@PathVariable long eventId) {
        return facade.deleteEvent(eventId);
    }
}


