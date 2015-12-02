package com.epam.hw1.dao.impl;

import com.epam.hw1.dao.EventDao;
import com.epam.hw1.dao.TicketDao;
import com.epam.hw1.dao.UserAccountDao;
import com.epam.hw1.dao.annotation.JdbcImpl;
import com.epam.hw1.dao.parameter.SqlParameterSourceImpl;
import com.epam.hw1.model.DefaultBeanHolder;
import com.epam.hw1.model.Event;
import com.epam.hw1.model.Ticket;
import com.epam.hw1.model.User;
import com.epam.hw1.model.impl.TicketBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.Types;
import java.util.Collections;
import java.util.List;

/**
 * <code>TicketDao</code> implementation.
 *
 * @author Yevhen_Larikov
 */
@Repository
@JdbcImpl
public class TicketDaoImpl implements TicketDao {
    private static final Logger LOG = Logger.getLogger(TicketDaoImpl.class);

    private RowMapper<Ticket> mapper = (rs, rowNum) -> {
        Ticket ticket = new TicketBean();
        ticket.setId(rs.getLong("id"));
        ticket.setEventId(rs.getLong("eventId"));
        ticket.setUserId(rs.getLong("userId"));
        ticket.setCategory(Ticket.Category.valueOf(rs.getString("category")));
        ticket.setPlace(rs.getInt("place"));
        return ticket;
    };

    private NamedParameterJdbcTemplate namedParamJdbcTemplate;
    private JdbcTemplate jdbcTemplate;
    private UserAccountDao userAccountDao;
    private EventDao eventDao;
    private DefaultBeanHolder defaultBeanHolder;
    private PlatformTransactionManager transactionManager;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setNamedParamJdbcTemplate(NamedParameterJdbcTemplate namedParamJdbcTemplate) {
        this.namedParamJdbcTemplate = namedParamJdbcTemplate;
    }

    @Autowired
    public void setUserAccountDao(UserAccountDao userAccountDao) {
        this.userAccountDao = userAccountDao;
    }

    @Autowired
    public void setEventDao(com.epam.hw1.dao.EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Autowired
    public void setDefaultBeanHolder(DefaultBeanHolder defaultBeanHolder) {
        this.defaultBeanHolder = defaultBeanHolder;
    }

    @Autowired
    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        if (isTicketExists(eventId, place)) {
            throw new IllegalStateException("This place is already booked. EventId: " + eventId + ", place: " + place);
        }
        double price = eventDao.getEventById(eventId).getPrice();
        if (userAccountDao.withdraw(userId, price)) {
            return insertTicket(userId, eventId, place, category);
        }
        return null;
    }

    private boolean isTicketExists(long eventId, int place) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("eventId", eventId)
                .addValue("place", place);
        return !namedParamJdbcTemplate.query("SELECT * FROM tickets WHERE eventId = :eventId AND place = :place", params, mapper).isEmpty();
    }

    private Ticket getTicket(long userId, long eventId, int place, Ticket.Category category) {
        Ticket ticket = new TicketBean();
        ticket.setUserId(userId);
        ticket.setEventId(eventId);
        ticket.setPlace(place);
        ticket.setCategory(category);
        return ticket;
    }

    private Ticket insertTicket(long userId, long eventId, int place, Ticket.Category category) {
        Ticket ticket = getTicket(userId, eventId, place, category);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        BeanPropertySqlParameterSource beanPropertySqlParameterSource = new BeanPropertySqlParameterSource(ticket);
        beanPropertySqlParameterSource.registerSqlType("category", Types.VARCHAR);
        try {
            namedParamJdbcTemplate.update("INSERT INTO tickets (userId, eventId, category, place) VALUES (:userId, :eventId, :category, :place)", beanPropertySqlParameterSource, keyHolder);
            ticket.setId(keyHolder.getKey().longValue());
            return ticket;
        } catch (DataAccessException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(e);
            }
        }
        return null;
    }

    @Override
    public boolean insertTickets(List<TicketBean> tickets) {
        SqlParameterSource[] params = new SqlParameterSource[tickets.size()];
        for (int i = 0; i < tickets.size(); i++) {
            BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(tickets.get(i));
            source.registerSqlType("category", Types.VARCHAR);
            params[i] = source;
        }
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = transactionManager.getTransaction(definition);
        try {
            namedParamJdbcTemplate.batchUpdate("INSERT INTO tickets (userId, eventId, category, place) VALUES (:userId, :eventId, :category, :place)", params);
            transactionManager.commit(status);
            return true;
        } catch (DataAccessException e) {
            transactionManager.rollback(status);
            if (LOG.isDebugEnabled()) {
                LOG.debug(e);
            }
            return false;
        }
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        if (user == null && pageNum < 1 || pageSize < 1) {
            LOG.warn("Some of parameters were invalid. user:" + user +
                    ", pageNum:" + pageNum + ", pageSize:" + pageSize);
            return Collections.emptyList();
        }
        SqlParameterSourceImpl params = new SqlParameterSourceImpl(defaultBeanHolder)
                .addValue("userId", user.getId())
                .addValue("offset", (pageNum - 1) * pageSize)
                .addValue("size", pageSize);
        try {
            return namedParamJdbcTemplate.query("SELECT * FROM tickets INNER JOIN events ON tickets.eventId=events.id AND userId=:userId ORDER BY date DESC LIMIT :offset, :size;", params, mapper);
        } catch (DataAccessException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(e);
            }
        }
        return Collections.emptyList();
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        if (event == null && pageNum < 1 || pageSize < 1) {
            LOG.warn("Some of parameters were invalid. event:" + event +
                    ", pageNum:" + pageNum + ", pageSize:" + pageSize);
            return Collections.emptyList();
        }
        SqlParameterSourceImpl params = new SqlParameterSourceImpl(defaultBeanHolder)
                .addValue("eventId", event.getId())
                .addValue("offset", (pageNum - 1) * pageSize)
                .addValue("size", pageSize);
        try {
            return namedParamJdbcTemplate.query("SELECT * FROM tickets INNER JOIN users ON tickets.userId=users.id AND eventId=:eventId ORDER BY email LIMIT :offset, :size;", params, mapper);
        } catch (DataAccessException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(e);
            }
        }
        return null;
    }

    @Override
    public boolean cancelTicket(long ticketId) {
        try {
            return jdbcTemplate.update("DELETE FROM tickets WHERE id=?", ticketId) != 0;
        } catch (DataAccessException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(e);
            }
        }
        return false;
    }
}
