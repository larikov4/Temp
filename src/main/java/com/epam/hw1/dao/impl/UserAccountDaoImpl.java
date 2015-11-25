package com.epam.hw1.dao.impl;

import com.epam.hw1.dao.UserAccountDao;
import com.epam.hw1.service.UserAccountService;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * @author Yevhen_Larikov
 */
@Repository
public class UserAccountDaoImpl implements UserAccountDao {

    @Override
    public boolean refillAccount(int amount) {
        return false;
    }

    @Override
    public double getBalance() {
        return 0;
    }

    @Override
    public boolean withdraw(int amount) {
        return false;
    }
}
