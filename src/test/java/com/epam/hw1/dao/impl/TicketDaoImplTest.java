package com.epam.hw1.dao.impl;

import com.epam.hw1.dao.EventDao;
import com.epam.hw1.dao.TicketDao;
import com.epam.hw1.dao.UserDao;
import com.epam.hw1.model.Event;
import com.epam.hw1.model.Ticket;
import com.epam.hw1.model.User;
import com.epam.hw1.model.impl.EventBean;
import com.epam.hw1.model.impl.TicketBean;
import com.epam.hw1.model.impl.UserBean;
import com.epam.hw1.storage.Storage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Yevhen_Larikov
 */
@RunWith(MockitoJUnitRunner.class)
public class TicketDaoImplTest {
    private static final long TICKET_ID = 1L;
    private static final long EVENT_ID = 2L;
    private static final long USER_ID = 3L;
    private static final String TICKET_ID_WITH_PREFIX = TicketDaoImpl.TICKET_PREFIX + TICKET_ID;
    private static final int PAGE_SIZE = 2;
    private static final int PAGE_NUM = 1;
    private static final int ANOTHER_EVENT_ID = 0;
    private static final int ANOTHER_USER_ID = 0;
    private static final int TICKET_PLACE = 1;
    private static final int CYCLES_AMOUNT = 1000;
    private static final String USER_EMAIL = "user@email.com";

    @Mock
    private Storage storage;

    @Mock
    private Ticket ticket;

    @Mock
    private UserDao userDao;

    @Mock
    private EventDao eventDao;

    @InjectMocks
    private TicketDao ticketDao = new TicketDaoImpl();

    @Captor
    private ArgumentCaptor<String> idCaptor;

    @Captor
    private ArgumentCaptor<Ticket> ticketCaptor;

    private Event event;

    private User user;

    @Before
    public void setUp() {
        event = new EventBean();
        event.setId(EVENT_ID);
        event.setDate(new Date());
        user = new UserBean();
        user.setId(USER_ID);
        user.setEmail(USER_EMAIL);
    }

    @Test
    public void shouldCreateSeveralTickets() {
        for (int i = 0; i < CYCLES_AMOUNT; i++) {
            ticketDao.bookTicket(USER_ID, EVENT_ID, TICKET_PLACE + i, Ticket.Category.STANDARD);
        }
        verify(storage, times(CYCLES_AMOUNT)).put(idCaptor.capture(), ticketCaptor.capture());
        Set<String> set = new HashSet<>(idCaptor.getAllValues());
        assertEquals(CYCLES_AMOUNT, set.size());
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionWhenBookingTicketWithBookedPlace() {
        when(storage.getAll()).thenReturn(spy(generateTickets(1)));
        ticketDao.bookTicket(USER_ID, EVENT_ID, TICKET_PLACE, Ticket.Category.STANDARD);
    }

    @Test
    public void shouldReturnFullListWhenUserTicketsIsEnough() {
        Map<String, Object> tickets = generateTickets(PAGE_SIZE * PAGE_NUM);
        when(storage.getAll()).thenReturn(spy(tickets));
        when(eventDao.getEventById(anyInt())).thenReturn(event);

        assertEquals(PAGE_SIZE, ticketDao.getBookedTickets(user, PAGE_SIZE, PAGE_NUM).size());
    }

    @Test
    public void shouldReturnNotFullListWhenUserTicketsIsNotEnough() {
        Map<String, Object> tickets = generateTickets(PAGE_SIZE * PAGE_NUM - PAGE_SIZE / 2);
        when(storage.getAll()).thenReturn(spy(tickets));

        assertEquals(PAGE_SIZE / 2, ticketDao.getBookedTickets(user, PAGE_SIZE, PAGE_NUM).size());
    }

    @Test
    public void shouldReturnEmptyListWhenNoUserTicketsWereFound() {
        Map<String, Object> tickets = new HashMap<>();
        when(storage.getAll()).thenReturn(spy(tickets));

        assertEquals(Collections.emptyList(), ticketDao.getBookedTickets(user, PAGE_SIZE, PAGE_NUM));
    }

    @Test
    public void shouldReturnFullListWhenEventTicketsIsEnough() {
        Map<String, Object> tickets = generateTickets(PAGE_SIZE * PAGE_NUM);
        when(storage.getAll()).thenReturn(spy(tickets));
        when(userDao.getUserById(anyInt())).thenReturn(user);

        assertEquals(PAGE_SIZE, ticketDao.getBookedTickets(event, PAGE_SIZE, PAGE_NUM).size());
    }

    @Test
    public void shouldReturnNotFullListWhenEventTicketsIsNotEnough() {
        Map<String, Object> tickets = generateTickets(PAGE_SIZE * PAGE_NUM - PAGE_SIZE / 2);
        when(storage.getAll()).thenReturn(spy(tickets));

        assertEquals(PAGE_SIZE / 2, ticketDao.getBookedTickets(event, PAGE_SIZE, PAGE_NUM).size());
    }

    @Test
    public void shouldReturnEmptyListWhenNoEventTicketsWereFound() {
        Map<String, Object> tickets = new HashMap<>();
        when(storage.getAll()).thenReturn(spy(tickets));

        assertEquals(Collections.emptyList(), ticketDao.getBookedTickets(event, PAGE_SIZE, PAGE_NUM));
    }

    @Test
    public void shouldRemoveTicketFromStorage() {
        when(storage.remove(TICKET_ID_WITH_PREFIX)).thenReturn(true);

        ticketDao.cancelTicket(TICKET_ID);
        verify(storage).remove(TICKET_ID_WITH_PREFIX);
    }

    private Map<String, Object> generateTickets(int amount) {
        Map<String, Object> tickets = new HashMap<>();
        for (int i = 1; i <= amount; i++) {
            tickets.put(TicketDaoImpl.TICKET_PREFIX + i, generateTicket(i, EVENT_ID, USER_ID));
        }
        addInvalidTickets(tickets);
        return tickets;
    }

    private Map<String, Object> addInvalidTickets(Map<String, Object> tickets) {
        int k = 0;
        tickets.put(TicketDaoImpl.TICKET_PREFIX + --k, generateTicket(k, ANOTHER_EVENT_ID, ANOTHER_USER_ID));
        return tickets;
    }

    private Ticket generateTicket(int number, long eventId, long userId) {
        Ticket ticket = new TicketBean();
        ticket.setId(number);
        ticket.setEventId(eventId);
        ticket.setUserId(userId);
        ticket.setPlace(number);
        return ticket;
    }
}
