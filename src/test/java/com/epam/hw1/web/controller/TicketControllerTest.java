package com.epam.hw1.web.controller;

import com.epam.hw1.facade.BookingFacade;
import com.epam.hw1.model.Ticket;
import com.epam.hw1.model.impl.EventBean;
import com.epam.hw1.model.impl.TicketBean;
import com.epam.hw1.model.impl.UserBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

import static org.mockito.Mockito.verify;

/**
 * @author Yevhen_Larikov
 */
@RunWith(MockitoJUnitRunner.class)
public class TicketControllerTest {
    private static final int TICKET_ID = 1;
    private static final int USER_ID = 2;
    private static final int EVENT_ID = 3;
    private static final int PLACE = 4;
    private static final Ticket.Category CATEGORY = Ticket.Category.BAR;
    private static final int PAGE_SIZE = 1;
    private static final int PAGE_NUM = 1;

    @Mock
    private BookingFacade bookingFacade;

    @InjectMocks
    private TicketController controller;

    @Mock
    private TicketBean ticket = new TicketBean(TICKET_ID);

    @Test
    public void shouldInvokeFacadeGetTicketsForUser(){
        controller.getBookedTicketsForUser(USER_ID, PAGE_SIZE, PAGE_NUM);

        verify(bookingFacade).getBookedTickets(new UserBean(USER_ID), PAGE_SIZE, PAGE_NUM);
    }

    @Test
    public void shouldInvokeFacadeGetTicketsForEvent(){
        controller.getBookedTicketsForEvent(EVENT_ID, PAGE_SIZE, PAGE_NUM);

        verify(bookingFacade).getBookedTickets(new EventBean(EVENT_ID), PAGE_SIZE, PAGE_NUM);
    }

    @Test
    public void shouldInvokeFacadeBookTicket(){
        controller.bookTicket(USER_ID, EVENT_ID, PLACE, CATEGORY);

        verify(bookingFacade).bookTicket(USER_ID, EVENT_ID, PLACE, CATEGORY);
    }

    @Test
    public void shouldInvokeFacadeCancelTicket(){
        controller.cancelTicket(TICKET_ID);

        verify(bookingFacade).cancelTicket(TICKET_ID);
    }
}
