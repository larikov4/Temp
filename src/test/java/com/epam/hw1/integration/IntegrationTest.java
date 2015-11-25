package com.epam.hw1.integration;

import com.epam.hw1.facade.BookingFacade;
import com.epam.hw1.model.Event;
import com.epam.hw1.model.Ticket;
import com.epam.hw1.model.User;
import com.epam.hw1.model.impl.EventBean;
import com.epam.hw1.model.impl.UserBean;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

public class IntegrationTest {
    private static final int USER_ID = 1;
    private static final int EVENT_ID = 2;
    private static final int TICKET_PLACE = 4;
    private static final String USER_EMAIL = "user@user.com";
    private static final int PAGE_SIZE = 1;
    private static final int PAGE_NUM = 1;

    private BookingFacade facade;
    private User user;
    private Event event;

    @Before
    public void setUp() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        facade = context.getBean(BookingFacade.class);

        user = new UserBean();
        user.setId(USER_ID);
        user.setEmail(USER_EMAIL);
        event = new EventBean();
        event.setId(EVENT_ID);
    }

    @Test
    public void shouldBookAndCancelTicket() {
        facade.bookTicket(user.getId(), event.getId(), TICKET_PLACE, Ticket.Category.BAR);

        Ticket ticket = facade.getBookedTickets(user, PAGE_SIZE, PAGE_NUM).get(0);
        assertEquals(event.getId(), ticket.getEventId());
        assertEquals(user.getId(), ticket.getUserId());

        facade.cancelTicket(ticket.getId());
        assertTrue(facade.getBookedTickets(user, PAGE_SIZE, PAGE_NUM).isEmpty());
    }

    @Test
    public void shouldCreateAndRemoveUser() {
        facade.createUser(user);

        assertEquals(user, facade.getUserByEmail(USER_EMAIL));

        facade.deleteUser(user.getId());
        assertNull(facade.getUserById(USER_ID));
    }

    @Test
    public void shouldCreateAndRemoveEvent() {
        facade.createEvent(event);

        assertEquals(event, facade.getEventById(EVENT_ID));

        facade.deleteEvent(event.getId());
        assertNull(facade.getEventById(EVENT_ID));
    }
}
