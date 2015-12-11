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

import javax.ws.rs.core.MediaType;
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


    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    public Ticket bookTicket(@RequestParam long userId,
                             @RequestParam long eventId,
                             @RequestParam int place,
                             @RequestParam Ticket.Category category) {
        return facade.bookTicket(userId, eventId, place, category);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, params = "userId", produces = MediaType.APPLICATION_JSON)
    public List<Ticket> getBookedTicketsForUser(@RequestParam long userId,
                                                @RequestParam(defaultValue = "5") int pageSize,
                                                @RequestParam(defaultValue = "1") int pageNum) {
        return facade.getBookedTickets(new UserBean(userId), pageSize, pageNum);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, params = "eventId", produces = MediaType.APPLICATION_JSON)
    public List<Ticket> getBookedTicketsForEvent(@RequestParam long eventId,
                                                 @RequestParam(defaultValue = "5") int pageSize,
                                                 @RequestParam(defaultValue = "1") int pageNum) {
        return facade.getBookedTickets(new EventBean(eventId), pageSize, pageNum);
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON)
    public boolean cancelTicket(@PathVariable long id) {
        return facade.cancelTicket(id);
    }
}
