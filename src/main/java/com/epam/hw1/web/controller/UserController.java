package com.epam.hw1.web.controller;

import com.epam.hw1.facade.BookingFacade;
import com.epam.hw1.model.User;
import com.epam.hw1.model.impl.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


/**
 * Controller is responsible for delegating HTTP requests to facade for
 * querying User beans.
 *
 * @author Yevhen_Larikov
 */
@Controller
@RequestMapping("/users")
public class UserController {
    protected static final String ENTITIES_LIST_VIEW = "entitiesListView";
    protected static final String USERS_ATTRIBUTE_NAME = "users";
    private BookingFacade facade;

    @Autowired
    public void setFacade(BookingFacade facade) {
        this.facade = facade;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, params = "id", produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUserById(@RequestParam long id) {
        return facade.getUserById(id);
    }

    @RequestMapping(method = RequestMethod.GET, params = "email", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public User getUserByEmail(@RequestParam String email) {
        return facade.getUserByEmail(email);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, params = "name", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUsersByName(@RequestParam String name,
                                     @RequestParam(defaultValue = "5") int pageSize,
                                     @RequestParam(defaultValue = "1") int pageNum) {
        return facade.getUsersByName(name, pageSize, pageNum);
    }

    @RequestMapping(method = RequestMethod.GET, params = {"visual=true", "name"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getUsersByNameVisual(@RequestParam String name,
                                             @RequestParam(defaultValue = "5") int pageSize,
                                             @RequestParam(defaultValue = "1") int pageNum) {
        List<User> users = facade.getUsersByName(name, pageSize, pageNum);
        return new ModelAndView(ENTITIES_LIST_VIEW, new ModelMap(USERS_ATTRIBUTE_NAME, users));
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public User createUser(@RequestBody UserBean user) {
        return facade.createUser(user);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public User updateUser(@RequestBody UserBean user) {
        return facade.updateUser(user);
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean deleteUser(@PathVariable long id) {
        return facade.deleteUser(id);
    }
}
