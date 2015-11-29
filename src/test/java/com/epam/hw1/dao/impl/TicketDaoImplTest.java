package com.epam.hw1.dao.impl;

import com.epam.hw1.dao.TicketDao;
import com.epam.hw1.model.DefaultBeanHolder;
import com.epam.hw1.model.Event;
import com.epam.hw1.model.Ticket;
import com.epam.hw1.model.User;
import com.epam.hw1.model.impl.EventBean;
import com.epam.hw1.model.impl.TicketBean;
import com.epam.hw1.model.impl.UserBean;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Yevhen_Larikov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-spring-config.xml")
@Transactional
public class TicketDaoImplTest {
    private static final int EXISTING_TICKET_ID = 1;
    private static final int EXISTING_USER_ID = 1;
    private static final int EXISTING_EVENT_ID = 1;
    public static final int EXISTING_TICKET_PLACE = 10;
    public static final Ticket.Category EXISTING_TICKET_CATEGORY = Ticket.Category.STANDARD;
    private static final int PAGE_SIZE = 2;
    private static final int PAGE_NUM = 1;

    @Autowired
    private TicketDao ticketDao;

    private Ticket existingTicket;

    @Before
    public void setUp() {
        existingTicket = new TicketBean();
        existingTicket.setId(EXISTING_TICKET_ID);
        existingTicket.setUserId(EXISTING_USER_ID);
        existingTicket.setEventId(EXISTING_EVENT_ID);
        existingTicket.setCategory(EXISTING_TICKET_CATEGORY);
        existingTicket.setPlace(EXISTING_TICKET_PLACE);
    }

    @Test
    public void shouldBookTicket() {
        existingTicket.setPlace(existingTicket.getPlace() + 1);
        assertTrue(ticketDao.bookTicket(EXISTING_USER_ID, EXISTING_EVENT_ID, EXISTING_TICKET_PLACE + 1, EXISTING_TICKET_CATEGORY).getId() > EXISTING_TICKET_ID);
    }

    @Test
    public void shouldNotBookTicketWhenNotEnoughBalance() {
        existingTicket.setPlace(existingTicket.getPlace() + 1);
        assertTrue(ticketDao.bookTicket(EXISTING_USER_ID, EXISTING_EVENT_ID, EXISTING_TICKET_PLACE + 1, EXISTING_TICKET_CATEGORY).getId() > EXISTING_TICKET_ID);
        assertNull(ticketDao.bookTicket(EXISTING_USER_ID, EXISTING_EVENT_ID, EXISTING_TICKET_PLACE + 2, EXISTING_TICKET_CATEGORY));
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionWhenBookTicketWithExistingPlaceAndEventId() {
        ticketDao.bookTicket(EXISTING_USER_ID, EXISTING_EVENT_ID, EXISTING_TICKET_PLACE, Ticket.Category.BAR);
    }

    @Test
    public void shouldReturnListWhenTicketsByUserIdIsEnough() {
        User existingUser = new UserBean();
        existingUser.setId(EXISTING_USER_ID);
        List<Ticket> tickets = ticketDao.getBookedTickets(existingUser, PAGE_SIZE, PAGE_NUM);
        assertEquals(PAGE_SIZE, tickets.size());
        assertEquals(existingTicket, tickets.get(PAGE_SIZE - 1));
    }

    @Test
    public void shouldReturnEmptyListWhenTicketsByUserIdIsEnough() {
        User newUser = new UserBean();
        newUser.setId(Integer.MAX_VALUE);
        List<Ticket> tickets = ticketDao.getBookedTickets(newUser, PAGE_SIZE, PAGE_NUM);
        assertTrue(tickets.isEmpty());
    }

    @Test
    public void shouldReturnListWhenTicketsByEventIdIsEnough() {
        Event existingEvent = new EventBean();
        existingEvent.setId(EXISTING_EVENT_ID);
        List<Ticket> tickets = ticketDao.getBookedTickets(existingEvent, PAGE_SIZE, PAGE_NUM);
        assertEquals(PAGE_SIZE, tickets.size());
        assertEquals(existingTicket, tickets.get(0));
    }

    @Test
    public void shouldReturnEmptyListWhenTicketsByEventIdIsEnough() {
        Event newEvent = new EventBean();
        newEvent.setId(Integer.MAX_VALUE);
        List<Ticket> tickets = ticketDao.getBookedTickets(newEvent, PAGE_SIZE, PAGE_NUM);
        assertTrue(tickets.isEmpty());
    }

    @Test
    public void shouldCancelTicket() {
        User existingUser = new UserBean();
        existingUser.setId(EXISTING_USER_ID);
        int expectedSize = ticketDao.getBookedTickets(existingUser, PAGE_SIZE, PAGE_NUM).size();
        assertTrue(ticketDao.cancelTicket(EXISTING_TICKET_ID));
        assertEquals(expectedSize - 1, ticketDao.getBookedTickets(existingUser, PAGE_SIZE, PAGE_NUM).size());
    }
}
