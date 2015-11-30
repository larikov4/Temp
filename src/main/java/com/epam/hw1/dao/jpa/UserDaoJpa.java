package com.epam.hw1.dao.jpa;

import com.epam.hw1.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

/**
 * @author Yevhen_Larikov
 */
public interface UserDaoJpa extends CrudRepository<User, Long>{

}
