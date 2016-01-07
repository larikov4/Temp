package com.epam.hw1.service.impl;

import com.epam.hw1.exception.UserNotFoundException;
import com.epam.hw1.model.UserBean;
import com.epam.hw1.repository.UserRepository;
import com.epam.hw1.service.UserService;
import com.epam.hw1.service.version.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * {@link UserService} implementation.
 *
 * @author Yevhen_Larikov on 20.12.2015.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VersionService versionService;

    @Override
    public void addUser(UserBean userBean){
        long actualVersion;
        do{
            actualVersion = versionService.getActualVersion();
        } while(!versionService.compareAndSwapActualVersion(actualVersion));
        userRepository.addUser(actualVersion, userBean);
    }

    @Override
    public UserBean getUser(String username) throws UserNotFoundException {
        userRepository.checkUserExistence(username);
        return userRepository.getUser(username);
    }
}
