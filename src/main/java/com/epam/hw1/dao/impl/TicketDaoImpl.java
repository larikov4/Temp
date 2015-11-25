package com.epam.hw1.dao.impl;

import com.epam.hw1.dao.EventDao;
import com.epam.hw1.dao.TicketDao;
import com.epam.hw1.dao.UserDao;
import com.epam.hw1.model.Event;
import com.epam.hw1.model.Ticket;
import com.epam.hw1.model.User;
import com.epam.hw1.model.impl.TicketBean;
import com.epam.hw1.storage.Storage;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * <code>TicketDao</code> implementation.
 *
 * @author Yevhen_Larikov
 */
@Repository
public class TicketDaoImpl implements TicketDao {
    private static final Logger LOG = Logger.getLogger(TicketDaoImpl.class);
    public static final String TICKET_PREFIX = "ticket";
    private UserDao userDao;
    private EventDao eventDao;
    private Storage storage;

    /**
     * Injects storage into object.
     *
     * @param storage Storage
     */
    @Autowired
    public void setStorage(@Qualifier("storage")Storage storage) {
        this.storage = storage;
    }

    /**
     * Injects UserDao into object.
     *
     * @param userDao UserDao
     */
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Injects EventDao into object.
     *
     * @param eventDao EventDao
     */
    public void setEventDao(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Override
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        for (Ticket ticket : getTicketsByEventId(eventId)) {
            if (ticket.getPlace() == place) {
                throw new IllegalStateException("This place is already booked. EventId: " + eventId + ", place: " + place);
            }
        }
        Ticket ticket = new TicketBean();
        ticket.setId(generateUniqueId());
        ticket.setUserId(userId);
        ticket.setEventId(eventId);
        ticket.setPlace(place);
        ticket.setCategory(category);
        return storage.put(TICKET_PREFIX + ticket.getId(), ticket);
    }

    private long generateUniqueId() {
        long id;
        Random random = new Random();
        do {
            id = random.nextLong();
        }
        while (storage.get(TICKET_PREFIX + id) != null);
        return id;
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        if (user == null && pageNum < 1 || pageSize < 1) {
            LOG.warn("Some of parameters were invalid. user:" + user +
                    ", pageNum:" + pageNum + ", pageSize:" + pageSize);
            return Collections.emptyList();
        }
        List<Ticket> tickets = getTicketsByUserIdSortedByDate(user);
        List<List<Ticket>> pages = Lists.partition(tickets, pageSize);
        return pages.size() >= pageNum ? pages.get(pageNum - 1) : Collections.emptyList();
    }

    private List<Ticket> getTicketsByUserIdSortedByDate(User user) {
        return storage.getAll().entrySet().stream()
                .filter(entry -> entry.getKey().contains(TICKET_PREFIX))
                .map(entry -> (Ticket) entry.getValue())
                .filter(ticket -> user.getId() == (ticket.getUserId()))
                .sorted((t1, t2) -> eventDao.getEventById(t2.getUserId()).getDate()
                        .compareTo(eventDao.getEventById(t1.getUserId()).getDate()))
                .collect(toList());
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        if (event == null && pageNum < 1 || pageSize < 1) {
            LOG.warn("Some of parameters were invalid. event:" + event +
                    ", pageNum:" + pageNum + ", pageSize:" + pageSize);
            return Collections.emptyList();
        }
        List<Ticket> tickets = getTicketsByEventId(event.getId());
        Collections.sort(tickets, (t1, t2) -> userDao.getUserById(t1.getUserId()).getEmail()
                .compareTo(userDao.getUserById(t2.getUserId()).getEmail()));
        List<List<Ticket>> pages = Lists.partition(tickets, pageSize);
        return pages.size() >= pageNum ? pages.get(pageNum - 1) : Collections.emptyList();
    }

    private List<Ticket> getTicketsByEventId(long eventId) {
        return storage.getAll().entrySet().stream()
                .filter(entry -> entry.getKey().contains(TICKET_PREFIX))
                .map(entry -> (Ticket) entry.getValue())
                .filter(ticket -> eventId == ticket.getEventId())
                .collect(toList());
    }

    @Override
    public boolean cancelTicket(long ticketId) {
        return storage.remove(TICKET_PREFIX + ticketId);
    }
}
