package com.epam.hw1.model.impl;

import com.epam.hw1.model.UserAccount;

/**
 * @author Yevhen_Larikov
 */
public class UserAccountBean implements UserAccount{
    private double balance;

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public void setBalance(double amount) {
        this.balance = amount;
    }
}
