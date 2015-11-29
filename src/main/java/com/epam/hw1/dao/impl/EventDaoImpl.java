package com.epam.hw1.dao.impl;

import com.epam.hw1.dao.EventDao;
import com.epam.hw1.model.Event;
import com.epam.hw1.model.impl.EventBean;
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

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * <code>EventDao</code> implementation.
 *
 * @author Yevhen_Larikov
 */
@Repository
public class EventDaoImpl implements EventDao {
    private static final Logger LOG = Logger.getLogger(EventDaoImpl.class);
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParamJdbcTemplate;

    private RowMapper<Event> mapper = (rs, rowNum) -> {
        Event event = new EventBean();
        event.setId(rs.getLong("id"));
        event.setTitle(rs.getString("title"));
        event.setDate(rs.getDate("date"));
        event.setPrice(rs.getInt("price"));
        return event;
    };

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setNamedParamJdbcTemplate(NamedParameterJdbcTemplate namedParamJdbcTemplate) {
        this.namedParamJdbcTemplate = namedParamJdbcTemplate;
    }

    @Override
    public Event getEventById(long eventId) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM events WHERE id = ?", mapper, eventId);
        } catch (DataAccessException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(e);
            }
        }
        return null;
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        if (title == null || pageNum < 1 || pageSize < 1) {
            LOG.warn("Some of parameters were invalid. title:" + title +
                    ", pageNum:" + pageNum + ", pageSize:" + pageSize);
            return Collections.emptyList();
        }
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("title", title)
                .addValue("offset", (pageNum - 1) * pageSize)
                .addValue("size", pageSize);
        try {
            return namedParamJdbcTemplate.query("SELECT * FROM events WHERE title=:title LIMIT :offset, :size", params, mapper);
        } catch (DataAccessException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(e);
            }
        }
        return Collections.emptyList();
    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        if (day == null || pageNum < 1 || pageSize < 1) {
            LOG.warn("Some of parameters were invalid. day:" + day +
                    ", pageNum:" + pageNum + ", pageSize:" + pageSize);
            return Collections.emptyList();
        }
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("date", day)
                .addValue("offset", (pageNum - 1) * pageSize)
                .addValue("size", pageSize);
        try {
            return namedParamJdbcTemplate.query("SELECT * FROM events WHERE date=:date LIMIT :offset, :size", params, mapper);
        } catch (DataAccessException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(e);
            }
        }
        return Collections.emptyList();
    }

    @Override
    public Event createEvent(Event event) {
        if (event == null) {
            LOG.warn("Passed parameter were null.");
            return null;
        }
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource beanPropertySqlParameterSource = new BeanPropertySqlParameterSource(event);
        try {
            namedParamJdbcTemplate.update("INSERT INTO events (title, date, price) VALUES (:title, :date, :price)", beanPropertySqlParameterSource, keyHolder);
            event.setId(keyHolder.getKey().longValue());
            return event;
        } catch (DataAccessException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(e);
            }
        }
        return null;
    }

    @Override
    public Event updateEvent(Event event) {
        if (event == null) {
            LOG.warn("Passed parameter were null.");
            return null;
        }
        SqlParameterSource beanPropertySqlParameterSource = new BeanPropertySqlParameterSource(event);
        try {
            namedParamJdbcTemplate.update("UPDATE events SET id=:id, title=:title, date=:date, price=:price WHERE id=:id", beanPropertySqlParameterSource); //TODO cut string, fields, queries
            return event;
        } catch (DataAccessException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(e);
            }
        }
        return null;
    }

    @Override
    public boolean deleteEvent(long eventId) {
        try {
            return jdbcTemplate.update("DELETE FROM events WHERE id=?", eventId) != 0;
        } catch (DataAccessException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(e);
            }
        }
        return false;
    }
}
