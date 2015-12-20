package com.epam.hw1.service.impl;

import com.epam.hw1.dao.TicketDao;
import com.epam.hw1.model.Event;
import com.epam.hw1.model.Ticket;
import com.epam.hw1.model.User;
import com.epam.hw1.service.oxm.OxmManager;
import com.epam.hw1.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <code>TicketService</code> implementation.
 *
 * @author Yevhen_Larikov
 */
@Service
@Transactional(readOnly = true)
public class TicketServiceImpl implements TicketService {
    private TicketDao ticketDao;
    private OxmManager oxmManager;

    /**
     * Injects <code>TicketDao</code> into Service.
     *
     * @param ticketDao TicketDao
     */
    @Autowired
    public void setTicketDao(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    @Autowired
    public void setOxmManager(OxmManager oxmManager) {
        this.oxmManager = oxmManager;
    }

    @Override
    @Transactional
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        return ticketDao.bookTicket(userId, eventId, place, category);
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        return ticketDao.getBookedTickets(user, pageSize, pageNum);
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        return ticketDao.getBookedTickets(event, pageSize, pageNum);
    }

    @Override
    @Transactional
    public boolean cancelTicket(long ticketId) {
        return ticketDao.cancelTicket(ticketId);
    }

    @Override
    @Transactional
    public boolean insertTicketsFromXml(String filename) {
        return ticketDao.insertTickets(oxmManager.unmarshalTickets(filename));
    }
}
