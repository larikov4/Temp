package com.epam.hw1.service.impl;

import com.epam.hw1.dao.UserAccountDao;
import com.epam.hw1.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Yevhen_Larikov
 */
@Service
@Transactional(readOnly = true)
public class UserAccountServiceImpl implements UserAccountService {
    private UserAccountDao userAccountDao;

    @Autowired
    public void setUserAccountDao(UserAccountDao userAccountDao) {
        this.userAccountDao = userAccountDao;
    }

    @Override
    @Transactional
    public boolean refillAccount(long userId, double amount) {
        return userAccountDao.refillAccount(userId, amount);
    }

    @Override
    @Transactional
    public boolean withdraw(long userId, double amount) {
        return userAccountDao.withdraw(userId, amount);
    }
}
