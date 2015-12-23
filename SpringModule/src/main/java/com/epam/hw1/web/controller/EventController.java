package com.epam.hw1.web.controller;

import com.epam.hw1.facade.BookingFacade;
import com.epam.hw1.model.Event;
import com.epam.hw1.model.User;
import com.epam.hw1.model.impl.EventBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;

/**
 * Controller is responsible for delegating HTTP requests to facade for
 * querying Event beans.
 *
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

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public Event getEventById(@RequestParam long id) {
        return facade.getEventById(id);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, params = "title", produces = MediaType.APPLICATION_JSON)
    public List<Event> getEventsByTitle(@RequestParam String title,
                                        @RequestParam(defaultValue = "5") int pageSize,
                                        @RequestParam(defaultValue = "1") int pageNum) {
        return facade.getEventsByTitle(title, pageSize, pageNum);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, params = "day", produces = MediaType.APPLICATION_JSON)
    public List<Event> getEventsForDay(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date day,
                                       @RequestParam(defaultValue = "5") int pageSize,
                                       @RequestParam(defaultValue = "1") int pageNum) {
        return facade.getEventsForDay(day, pageSize, pageNum);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    public Event createEvent(@RequestBody EventBean event) {
        return facade.createEvent(event);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON)
    public Event updateEvent(@RequestBody EventBean event) {
        return facade.updateEvent(event);
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON)
    public boolean deleteEvent(@PathVariable long id) {
        return facade.deleteEvent(id);
    }
}


