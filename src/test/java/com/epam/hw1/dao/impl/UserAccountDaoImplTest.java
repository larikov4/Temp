package com.epam.hw1.dao.impl;

import com.epam.hw1.dao.UserAccountDao;
import com.epam.hw1.dao.UserDao;
import com.epam.hw1.model.User;
import com.epam.hw1.model.UserAccount;
import com.epam.hw1.model.impl.UserAccountBean;
import com.epam.hw1.model.impl.UserBean;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Yevhen_Larikov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-spring-config.xml")
@WebAppConfiguration
public class UserAccountDaoImplTest {
    private static final int EXISTING_USER_ID = 1;
    private static final int INITIAL_BALANCE = 10;
    public static final double PRECISION = 0.1;

    @Autowired
    private UserAccountDao userAccountDao;

    @Test
    @DirtiesContext
    public void shouldRefillAccount() {
        assertTrue(userAccountDao.refillAccount(EXISTING_USER_ID, INITIAL_BALANCE));
        assertEquals(INITIAL_BALANCE * 2, userAccountDao.getUserAccount(EXISTING_USER_ID).getBalance(), PRECISION);
    }

    @Test
    @DirtiesContext
    public void shouldWithdrawFromAccount() {
        assertTrue(userAccountDao.withdraw(EXISTING_USER_ID, INITIAL_BALANCE));
        assertEquals(0, userAccountDao.getUserAccount(EXISTING_USER_ID).getBalance(), PRECISION);
    }

    @Test
    public void shouldNotWithdrawWhenAmountIsBiggerThanInitialBalance() {
        assertFalse(userAccountDao.withdraw(EXISTING_USER_ID, INITIAL_BALANCE*2));
        assertEquals(INITIAL_BALANCE, userAccountDao.getUserAccount(EXISTING_USER_ID).getBalance(), PRECISION);
    }
}
