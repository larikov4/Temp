package com.epam.hw1.web.controller;

import com.epam.hw1.facade.BookingFacade;
import com.epam.hw1.model.User;
import com.epam.hw1.model.impl.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author Yevhen_Larikov
 */
@Controller
@RequestMapping("/users")
public class UserController {

    private BookingFacade facade;

    @Autowired
    public void setFacade(BookingFacade facade) {
        this.facade = facade;
    }

    @RequestMapping(method = RequestMethod.GET, params = "id",
            produces = "application/json")
    @ResponseBody
    public User getUserById(@RequestParam("id") long id) {
        return facade.getUserById(id);
    }

    @RequestMapping(method = RequestMethod.GET, params = "email",
            produces = "application/json")
    @ResponseBody
    public User getUserByEmail(@RequestParam("email") String email) {
        return facade.getUserByEmail(email);
    }

    @RequestMapping(method = RequestMethod.GET, params = "name",
            produces = "application/json")
    @ResponseBody
    public List<User> getUsersByName(@RequestParam("name") String name,
                                        @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
                                        @RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {
        return facade.getUsersByName(name, pageSize, pageNum);
    }

    @RequestMapping(method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public User createUser(@RequestBody UserBean user) {
        return facade.createUser(user);
    }

    @RequestMapping(method = RequestMethod.PUT,
            produces = "application/json")
    @ResponseBody
    public User updateUser(@RequestBody UserBean user) {
        return facade.updateUser(user);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE,
            produces = "application/json")
    @ResponseBody
    public boolean deleteUser(@PathVariable long userId) {
        return facade.deleteUser(userId);
    }
}
