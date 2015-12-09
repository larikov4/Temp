package com.epam.hw1.web.controller;

import com.epam.hw1.facade.BookingFacade;
import com.epam.hw1.model.Event;
import com.epam.hw1.model.Ticket;
import com.epam.hw1.model.User;
import com.epam.hw1.model.impl.EventBean;
import com.epam.hw1.model.impl.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller is responsible for delegating HTTP requests to facade for
 * querying Ticket beans.
 *
 * @author Yevhen_Larikov
 */
@Controller
@RequestMapping("/tickets")
public class TicketController {

    private BookingFacade facade;

    @Autowired
    public void setFacade(BookingFacade facade) {
        this.facade = facade;
    }


    @RequestMapping(method = RequestMethod.POST,
            produces = "application/json")
    @ResponseBody
    public Ticket bookTicket(@RequestParam("userId") long userId,
                           @RequestParam("eventId") long eventId,
                           @RequestParam("place") int place,
                           @RequestParam("category") Ticket.Category category) {
        return facade.bookTicket(userId, eventId, place, category);
    }

    @RequestMapping(method = RequestMethod.GET, params = "userId",
            produces = "application/json")
    @ResponseBody
    public List<Ticket> getBookedTicketsForUser(@RequestParam("userId") long userId,
                                       @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
                                       @RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {
        return facade.getBookedTickets(new UserBean(userId), pageSize, pageNum);
    }

    @RequestMapping(method = RequestMethod.GET, params = "eventId",
            produces = "application/json")
    @ResponseBody
    public List<Ticket> getBookedTicketsForEvent(@RequestParam("eventId") long eventId,
                                      @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
                                      @RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {
        return facade.getBookedTickets(new EventBean(eventId), pageSize, pageNum);
    }

    @RequestMapping(value = "/{ticketId}", method = RequestMethod.DELETE,
            produces = "application/json")
    @ResponseBody
    public boolean cancelTicket(@PathVariable long ticketId) {
        return facade.cancelTicket(ticketId);
    }
}
