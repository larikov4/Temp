package com.epam.hw1.dao.jpa;

import com.epam.hw1.model.User;
import com.epam.hw1.model.impl.UserBean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Defines required methods for <code>UserDaoJpaImpl</code>.
 * Methods are implemented by Hibernate.
 *
 * @author Yevhen_Larikov
 */
@Repository
public interface UserDaoJpa extends CrudRepository<UserBean, Long>{

    /**
     * Finds user by his email.
     *
     * @param email user's email
     * @return found User
     */
    User findByEmail(String email);

    /**
     * Finds users by theirs names. Fetches user only for page number and page size
     * from given Pageable object.
     *
     * @param name User's name
     * @param pageable Pageable object contains page number and page size
     * @return list of found Users
     */
    List<User> findByName(String name, Pageable pageable);
}
