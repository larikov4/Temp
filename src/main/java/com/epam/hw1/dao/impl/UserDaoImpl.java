package com.epam.hw1.dao.impl;

import com.epam.hw1.dao.UserDao;
import com.epam.hw1.model.User;
import com.epam.hw1.model.impl.UserBean;
import com.epam.hw1.storage.Storage;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static final Logger LOG = Logger.getLogger(TicketDaoImpl.class);
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
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?", mapper, userId);
    }

    @Override
    public User getUserByEmail(String email) {
        if (email == null) {
            LOG.warn("Passed parameter were null.");
            return null;
        }
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email = ?", mapper, email);
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
                .addValue("offset", (pageNum-1) * pageSize)
                .addValue("size", pageSize);
        return namedParamJdbcTemplate.query("SELECT * FROM users WHERE name=:name LIMIT :offset, :size", params, mapper);
    }

    @Override
    public User createUser(User user) {
        if (user == null) {
            LOG.warn("Passed parameter were null.");
            return null;
        }
        SqlParameterSource beanPropertySqlParameterSource = new BeanPropertySqlParameterSource(user);
        namedParamJdbcTemplate.update("INSERT INTO users VALUES (:id, :name, :email)", beanPropertySqlParameterSource);
        return user;
    }

    @Override
    public User updateUser(User user) {
        if (user == null) {
            LOG.warn("Passed parameter were null.");
            return null;
        }
        SqlParameterSource beanPropertySqlParameterSource = new BeanPropertySqlParameterSource(user);
        namedParamJdbcTemplate.update("UPDATE users SET id=:id, name=:name, email=:email WHERE id=:id", beanPropertySqlParameterSource); //TODO cut string, fields, queries
        return user;
    }

    @Override
    public boolean deleteUser(long userId) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", userId)!=0;
    }
}
