package com.epam.hw1.dao.impl;

import com.epam.hw1.dao.UserAccountDao;
import com.epam.hw1.dao.annotation.JdbcImpl;
import com.epam.hw1.model.UserAccount;
import com.epam.hw1.model.impl.UserAccountBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 * @author Yevhen_Larikov
 */
@Repository
@JdbcImpl
public class UserAccountDaoImpl implements UserAccountDao {
    private static final Logger LOG = Logger.getLogger(UserAccountDaoImpl.class);
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM accounts WHERE userId = ?";
    private static final String SQL_UPDATE_ACCOUNT = "UPDATE accounts SET userId=:userId, balance=:balance WHERE id=:id";
    private static final String SQL_UPDATE_BALANCE = "UPDATE accounts SET balance=:balance WHERE id=:id";

    private RowMapper<UserAccount> mapper = (rs, rowNum) -> {
        UserAccount userAccount = new UserAccountBean();
        userAccount.setId(rs.getLong("id"));
        userAccount.setUserId(rs.getLong("userId"));
        userAccount.setBalance(rs.getDouble("balance"));
        return userAccount;
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
    public UserAccount getUserAccount(long userId) {
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
    public boolean refillAccount(long userId, double amount) {
        try {
            UserAccount account = getUserAccount(userId);
            account.setBalance(account.getBalance() + amount);
            SqlParameterSource beanPropertySqlParameterSource = new BeanPropertySqlParameterSource(account);
            namedParamJdbcTemplate.update(SQL_UPDATE_BALANCE, beanPropertySqlParameterSource);
            return true;
        } catch (DataAccessException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Exception while working with DB is occurred: ", e);
            }
        }
        return false;
    }

    @Override
    public boolean withdraw(long userId, double amount) {
        try {
            UserAccount account = getUserAccount(userId);
            account.setBalance(account.getBalance() - amount);
            if(account.getBalance()>=0) {
                SqlParameterSource beanPropertySqlParameterSource = new BeanPropertySqlParameterSource(account);
                namedParamJdbcTemplate.update(SQL_UPDATE_ACCOUNT, beanPropertySqlParameterSource);
                return true;
            }
        } catch (DataAccessException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Exception while working with DB is occurred: ", e);
            }
        }
        return false;
    }
}
