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
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
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
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    /**
     * Injects storage into Dao.
     *
     * @param storage Storage
     */
    @Autowired
    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public class UserMapper implements RowMapper<User> {
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new UserBean();
            user.setId(rs.getLong("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            return user;
        }
    }

    @Override
    public User getUserById(long userId) {
        return storage.get(USER_PREFIX + userId);
    }

    public User getUserByEmailDB(String email) {
//        jdbcTemplate.update("INSERT INTO users VALUES (?, ?, ?)", new Object[] { 11,
//                "Ivan11","ivan11@email.com"
//        });

//        return jdbcTemplate.queryForObject("select * from users where email = ?", new Object[]{email}, new UserMapper());

        UserBean user = new UserBean();
        user.setId(12);
        user.setName("Ivan12");
        user.setName("ivan12@email.com");
        SqlParameterSource beanPropertySqlParameterSource = new BeanPropertySqlParameterSource(user);

//        jdbcTemplate.update("INSERT INTO users( id, name, email) VALUES (:id, :name, :email)", beanPropertySqlParameterSource);
        jdbcTemplate.update("UPDATE users SET (:id, :name, :email)", beanPropertySqlParameterSource);
        return null;

    }

    @Override
    public User getUserByEmail(String email) {
        if (email == null) {
            LOG.warn("Passed parameter were null.");
            return null;
        }
        return storage.getAll().entrySet().stream().filter(entry -> entry.getKey().contains(USER_PREFIX))
                .map(entry -> (User) entry.getValue())
                .filter(user -> email.equals(user.getEmail()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        if (name == null || pageNum < 1 || pageSize < 1) {
            LOG.warn("Some of parameters were invalid. name:" + name +
                    ", pageNum:" + pageNum + ", pageSize:" + pageSize);
            return Collections.emptyList();
        }
        List<User> users = storage.getAll().entrySet().stream().filter(entry -> entry.getKey().contains(USER_PREFIX))
                .map(entry -> (User) entry.getValue())
                .filter(user -> user.getName() != null && name.contains(user.getName()))
                .collect(toList());

        List<List<User>> pages = Lists.partition(users, pageSize);
        return pages.size() >= pageNum ? pages.get(pageNum - 1) : Collections.emptyList();
    }

    @Override
    public User createUser(User user) {
        if (user == null) {
            LOG.warn("Passed parameter were null.");
            return null;
        }
        if (user.getEmail() != null && getUserByEmail(user.getEmail()) != null) {
            LOG.warn("User's e-mail should be unique. Duplicate email: " + user.getEmail());
            return null;
        }
        return storage.put(USER_PREFIX + user.getId(), user);
    }

    @Override
    public User updateUser(User user) {
        if (user == null) {
            LOG.warn("Passed parameter were null.");
            return null;
        }
        return storage.put(USER_PREFIX + user.getId(), user);
    }

    @Override
    public boolean deleteUser(long userId) {
        return storage.remove(USER_PREFIX + userId);
    }
}
