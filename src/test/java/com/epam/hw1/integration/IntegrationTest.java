package com.epam.hw1.integration;

import com.epam.hw1.facade.BookingFacade;
import com.epam.hw1.model.Event;
import com.epam.hw1.model.Ticket;
import com.epam.hw1.model.User;
import com.epam.hw1.model.impl.EventBean;
import com.epam.hw1.model.impl.UserBean;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-spring-config.xml")
@Transactional
public class IntegrationTest {
    private static final int USER_ID = 10;
    private static final int EVENT_ID = 2;
    private static final int TICKET_PLACE = 4;
    private static final String USER_EMAIL = "user@user.com";
    private static final int PAGE_SIZE = 1;
    private static final int PAGE_NUM = 2;

    private User user;
    private Event event;
    private BookingFacade facade;

    @Autowired
    public void setFacade(BookingFacade facade) {
        this.facade = facade;
    }

    @Before
    public void setUp() throws ParseException {
        user = new UserBean();
        user.setName("");
        user.setEmail(USER_EMAIL);

        event = new EventBean();
        event.setTitle("");
        event.setPrice(10);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        event.setDate(simpleDateFormat.parse("2016-01-10"));
    }

    @Test
    public void shouldBookAndCancelTicket() {
        User userWithId = new UserBean();
        userWithId.setId(USER_ID);
        Ticket ticket = facade.bookTicket(USER_ID, EVENT_ID, TICKET_PLACE, Ticket.Category.BAR);

        assertEquals(ticket.getId(), facade.getBookedTickets(userWithId, PAGE_SIZE, PAGE_NUM).get(0).getId());
        facade.cancelTicket(ticket.getId());
        assertTrue(facade.getBookedTickets(userWithId, PAGE_SIZE, PAGE_NUM).isEmpty());
    }

    @Test
    public void shouldCreateAndRemoveUser() {
        user = facade.createUser(user);

        assertEquals(user, facade.getUserByEmail(user.getEmail()));

        facade.deleteUser(user.getId());
        assertNull(facade.getUserById(user.getId()));
    }

    @Test
    public void shouldCreateAndRemoveEvent() {
        event = facade.createEvent(event);

        assertEquals(event, facade.getEventById(event.getId()));

        facade.deleteEvent(event.getId());
        assertNull(facade.getEventById(event.getId()));
    }
}
