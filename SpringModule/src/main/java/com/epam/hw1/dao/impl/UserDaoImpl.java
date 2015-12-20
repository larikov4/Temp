package com.epam.hw1.dao.impl;

import com.epam.hw1.dao.UserDao;
import com.epam.hw1.dao.annotation.JdbcImpl;
import com.epam.hw1.model.User;
import com.epam.hw1.model.impl.UserBean;
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
@JdbcImpl
public class UserDaoImpl implements UserDao {
    private static final Logger LOG = Logger.getLogger(UserDaoImpl.class);
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String SQL_SELECT_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
    private static final String SQL_SELECT_BY_NAME_WITH_LIMIT = "SELECT * FROM users WHERE name=:name LIMIT :offset, :size";
    private static final String SQL_INSERT_USER = "INSERT INTO users (name, email) VALUES ( :name, :email)";
    private static final String SQL_INSERT_ACCOUNT = "INSERT INTO accounts (userId, balance) VALUES ( :userId, :balance)";
    private static final String SQL_UPDATE = "UPDATE users SET name=:name, email=:email WHERE id=:id";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM users WHERE id=?";
    private static final String PASSED_PARAMETER_WAS_NULL = "Passed parameter was null.";

    private RowMapper<User> mapper = (rs, rowNum) -> {
        User user = new UserBean(rs.getLong("id"));
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

    @Override
    public User getUserById(long userId) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, mapper, userId);
        } catch (DataAccessException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Exception while working with DB is occurred: ", e);
            }
        }
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        if (email == null) {
            LOG.warn(PASSED_PARAMETER_WAS_NULL);
            return null;
        }
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_BY_EMAIL, mapper, email);
        } catch (DataAccessException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Exception while working with DB is occurred: ", e);
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
            return namedParamJdbcTemplate.query(SQL_SELECT_BY_NAME_WITH_LIMIT, params, mapper);
        } catch (DataAccessException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Exception while working with DB is occurred: ", e);
            }
        }
        return Collections.emptyList();
    }

    @Override
    public User createUser(User user) {
        if (user == null) {
            LOG.warn(PASSED_PARAMETER_WAS_NULL);
            return null;
        }
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", user.getName())
                .addValue("email", user.getEmail())
                .addValue("balance", 0);
        try {
            namedParamJdbcTemplate.update(SQL_INSERT_USER, params, keyHolder);
            params.addValue("userId", keyHolder.getKey().longValue());
            namedParamJdbcTemplate.update(SQL_INSERT_ACCOUNT, params);
            user.setId(keyHolder.getKey().longValue());
            return user;
        } catch (DataAccessException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Exception while working with DB is occurred: ", e);
            }
        }
        return null;
    }

    @Override
    public User updateUser(User user) {
        if (user == null) {
            LOG.warn(PASSED_PARAMETER_WAS_NULL);
            return null;
        }
        SqlParameterSource beanPropertySqlParameterSource = new BeanPropertySqlParameterSource(user);
        try {
            namedParamJdbcTemplate.update(SQL_UPDATE, beanPropertySqlParameterSource);
            return user;
        } catch (DataAccessException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Exception while working with DB is occurred: ", e);
            }
        }
        return null;
    }

    @Override
    public boolean deleteUser(long userId) {
        try {
            return jdbcTemplate.update(SQL_DELETE_BY_ID, userId) != 0;
        } catch (DataAccessException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Exception while working with DB is occurred: ", e);
            }
        }
        return false;
    }
}
