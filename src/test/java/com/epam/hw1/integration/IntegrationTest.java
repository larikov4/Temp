package com.epam.hw1.integration;

import com.epam.hw1.dao.jpa.UserDaoJpa;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-spring-config.xml")
@Transactional
public class IntegrationTest {
    private static final int USER_ID = 10;
    private static final int ANOTHER_USER_ID = 8;
    private static final int DEFAULT_ENTITY_ID = 5;
    private static final int EVENT_ID = 2;
    private static final int TICKET_PLACE = 4;
    private static final String USER_EMAIL = "user@user.com";
    private static final int PAGE_SIZE = 1;
    private static final int FIRST_PAGE_NUM = 1;
    private static final int SECOND_PAGE_NUM = 2;
    public static final int TICKETS_IN_BATCH = 3;

    private User user;
    private Event event;
    private BookingFacade facade;

    @Autowired
    private UserDaoJpa userDaoJpa;

    @Autowired
    public void setFacade(BookingFacade facade) {
        this.facade = facade;
    }

    @Before
    public void setUp() throws ParseException {
        user = new UserBean();
        user.setId(USER_ID);
        user.setName("");
        user.setEmail(USER_EMAIL);

        event = new EventBean();
        event.setId(EVENT_ID);
        event.setTitle("");
        event.setPrice(10);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        event.setDate(simpleDateFormat.parse("2016-01-10"));
    }

    @Test
    public void shouldBookAndCancelTicket() {
        Ticket ticket = facade.bookTicket(USER_ID, EVENT_ID, TICKET_PLACE, Ticket.Category.BAR);

        assertEquals(ticket.getId(), facade.getBookedTickets(user, PAGE_SIZE, SECOND_PAGE_NUM).get(0).getId());
        facade.cancelTicket(ticket.getId());
        assertTrue(facade.getBookedTickets(user, PAGE_SIZE, SECOND_PAGE_NUM).isEmpty());
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

    @Test
    public void shouldReturnDefaultUser(){
        User defaultUser = new UserBean();
        defaultUser.setId(DEFAULT_ENTITY_ID);
        facade.setDefaultUser(defaultUser);
        List<Ticket> tickets = facade.getBookedTickets(user, PAGE_SIZE, FIRST_PAGE_NUM);
        assertEquals(DEFAULT_ENTITY_ID, tickets.get(0).getUserId());
    }

    @Test
    public void shouldReturnDefaultEvent(){
        Event defaultEvent = new EventBean();
        defaultEvent.setId(DEFAULT_ENTITY_ID);
        facade.setDefaultEvent(defaultEvent);
        List<Ticket> tickets = facade.getBookedTickets(event, PAGE_SIZE, FIRST_PAGE_NUM);
        assertEquals(DEFAULT_ENTITY_ID, tickets.get(0).getEventId());
    }

    @Test
    public void shouldCommitUnmarshaledTickets(){
        user.setId(ANOTHER_USER_ID);
        int initialSize = facade.getBookedTickets(user, TICKETS_IN_BATCH + PAGE_SIZE, FIRST_PAGE_NUM).size();
        facade.insertTicketsFromXml(getClass().getClassLoader().getResource("oxm/tickets.xml").getFile());
        assertEquals(initialSize + TICKETS_IN_BATCH, facade.getBookedTickets(user, TICKETS_IN_BATCH + PAGE_SIZE, FIRST_PAGE_NUM).size());
    }

    @Test
    public void shouldRollbackUnmarshaledInvalidTickets(){
        user.setId(ANOTHER_USER_ID);
        int initialSize = facade.getBookedTickets(user, TICKETS_IN_BATCH + PAGE_SIZE, FIRST_PAGE_NUM).size();
        facade.insertTicketsFromXml(getClass().getClassLoader().getResource("oxm/invalidTickets.xml").getFile());
        assertEquals(initialSize, facade.getBookedTickets(user, TICKETS_IN_BATCH + PAGE_SIZE, FIRST_PAGE_NUM).size());
    }
}
