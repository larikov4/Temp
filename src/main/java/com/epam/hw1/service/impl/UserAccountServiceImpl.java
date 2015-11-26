package com.epam.hw1.service.impl;

import com.epam.hw1.dao.UserAccountDao;
import com.epam.hw1.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Yevhen_Larikov
 */
@Service
public class UserAccountServiceImpl implements UserAccountService {
    private UserAccountDao userAccountDao;

    @Autowired
    public void setUserAccountDao(UserAccountDao userAccountDao) {
        this.userAccountDao = userAccountDao;
    }

    @Override
    public boolean refillAccount(long userId, double amount) {
        return userAccountDao.refillAccount(userId, amount);
    }

    @Override
    public boolean withdraw(long userId, double amount) {
        return userAccountDao.withdraw(userId, amount);
    }
}
