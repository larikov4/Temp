package com.epam.hw1.model;

/**
 * @author Yevhen_Larikov
 */
public interface UserAccount {
    /**
     * UserAccount Id. UNIQUE.
     * @return UserAccount Id.
     */
    long getId();
    void setId(long id);
    long getUserId();
    void setUserId(long id);
    /**
     * Amount of prepaid money.
     * @return money amount
     */
    double getBalance();
    void setBalance(double money);
}
