package com.epam.hw1.dao.impl;

import com.epam.hw1.dao.UserAccountDao;
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
public class UserAccountDaoImpl implements UserAccountDao {
    private static final Logger LOG = Logger.getLogger(UserAccountDaoImpl.class);

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
            return jdbcTemplate.queryForObject("SELECT * FROM accounts WHERE userId = ?", mapper, userId);
        } catch (DataAccessException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(e);
            }
        }
        return null;
    }

    @Override
    public boolean refillAccount(long userId, double amount) {
        try {
            UserAccount account = jdbcTemplate.queryForObject("SELECT * FROM accounts WHERE userId = ?", mapper, userId);
            account.setBalance(account.getBalance() + amount);
            SqlParameterSource beanPropertySqlParameterSource = new BeanPropertySqlParameterSource(account);
            namedParamJdbcTemplate.update("UPDATE accounts SET balance=:balance WHERE id=:id", beanPropertySqlParameterSource);
            return true;
        } catch (DataAccessException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(e);
            }
        }
        return false;
    }

    @Override
    public boolean withdraw(long userId, double amount) {
        try {
            UserAccount account = jdbcTemplate.queryForObject("SELECT * FROM accounts WHERE userId = ?", mapper, userId);
            account.setBalance(account.getBalance() - amount);
            if(account.getBalance()>=0) {
                SqlParameterSource beanPropertySqlParameterSource = new BeanPropertySqlParameterSource(account);
                namedParamJdbcTemplate.update("UPDATE accounts SET id=:id, userId=:userId, balance=:balance WHERE id=:id", beanPropertySqlParameterSource);
                return true;
            }
        } catch (DataAccessException e) {
            if (LOG.isDebugEnabled()) {
                LOG.debug(e);
            }
        }
        return false;
    }
}
