package com.epam.hw1.dao.impl;

import com.epam.hw1.dao.UserDao;
import com.epam.hw1.model.User;
import com.epam.hw1.model.impl.UserBean;
import com.epam.hw1.storage.Storage;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * <code>UserDao</code> implementation.
 *
 * @author Yevhen_Larikov
 */
@Repository
public class UserDaoImpl implements UserDao {
    private static final Logger LOG = Logger.getLogger(UserDaoImpl.class);
    public static final String USER_PREFIX = "user";
    private Storage storage;

    private RowMapper<User> mapper = (rs, rowNum) -> {
        User user = new UserBean();
        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        return user;
    };

    private NamedParameterJdbcTemplate namedParamJdbcTemplate;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setNamedParamJdbcTemplate(NamedParameterJdbcTemplate namedParamJdbcTemplate) {
        this.namedParamJdbcTemplate = namedParamJdbcTemplate;
    }

    /**
     * Injects storage into Dao.
     *
     * @param storage Storage
     */
    @Autowired
    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    @Override
    public User getUserById(long userId) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?", mapper, userId);
        } catch (DataAccessException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(e);
            }
        }
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        if (email == null) {
            LOG.warn("Passed parameter were null.");
            return null;
        }
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email = ?", mapper, email);
        } catch (DataAccessException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(e);
            }
        }
        return null;
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        if (name == null || pageNum < 1 || pageSize < 1) {
            LOG.warn("Some of parameters were invalid. name:" + name +
                    ", pageNum:" + pageNum + ", pageSize:" + pageSize);
            return Collections.emptyList();
        }
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", name)
                .addValue("offset", (pageNum - 1) * pageSize)
                .addValue("size", pageSize);
        try {
            return namedParamJdbcTemplate.query("SELECT * FROM users WHERE name=:name LIMIT :offset, :size", params, mapper);
        } catch (DataAccessException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(e);
            }
        }
        return Collections.emptyList();
    }

    @Override
    public User createUser(User user) {
        if (user == null) {
            LOG.warn("Passed parameter were null.");
            return null;
        }
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource beanPropertySqlParameterSource = new BeanPropertySqlParameterSource(user);
        try {
            namedParamJdbcTemplate.update("INSERT INTO users (name, email) VALUES (:name, :email)", beanPropertySqlParameterSource, keyHolder);
            user.setId(keyHolder.getKey().longValue());
            return user;
        } catch (DataAccessException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(e);
            }
        }
        return null;
    }

    @Override
    public User updateUser(User user) {
        if (user == null) {
            LOG.warn("Passed parameter were null.");
            return null;
        }
        SqlParameterSource beanPropertySqlParameterSource = new BeanPropertySqlParameterSource(user);
        try {
            namedParamJdbcTemplate.update("UPDATE users SET id=:id, name=:name, email=:email WHERE id=:id", beanPropertySqlParameterSource); //TODO cut string, fields, queries
            return user;
        } catch (DataAccessException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(e);
            }
        }
        return null;
    }

    @Override
    public boolean deleteUser(long userId) {
        try {
            return jdbcTemplate.update("DELETE FROM users WHERE id=?", userId) != 0;
        } catch (DataAccessException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(e);
            }
        }
        return false;
    }
}
