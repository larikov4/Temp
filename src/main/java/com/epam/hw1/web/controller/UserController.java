package com.epam.hw1.web.controller;

import com.epam.hw1.facade.BookingFacade;
import com.epam.hw1.model.User;
import com.epam.hw1.model.impl.UserBean;
import org.apache.log4j.Logger;
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
    private static Logger LOG = Logger.getLogger(UserController.class);
    protected static final String ENTITIES_LIST_VIEW = "entitiesListView";
    protected static final String USERS_ATTRIBUTE_NAME = "users";
    private BookingFacade facade;

    @Autowired
    public void setFacade(BookingFacade facade) {
        this.facade = facade;
    }

    @RequestMapping(method = RequestMethod.GET, params = "id",
            produces = "application/json")
    @ResponseBody
    public User getUserById(@RequestParam long id) {
        return facade.getUserById(id);
    }

    @RequestMapping(method = RequestMethod.GET, params = "email",
            produces = "application/json")
    @ResponseBody
    public User getUserByEmail(@RequestParam String email) {
        return facade.getUserByEmail(email);
    }

    @RequestMapping(method = RequestMethod.GET, params = "name",
            produces = "application/json")
    @ResponseBody
    public List<User> getUsersByName(@RequestParam String name,
                                     @RequestParam(defaultValue = "5") int pageSize,
                                     @RequestParam(defaultValue = "1") int pageNum) {
        return facade.getUsersByName(name, pageSize, pageNum);
    }

    @RequestMapping(method = RequestMethod.GET, params = {"visual=true", "name"})
    public ModelAndView getUsersByNameVisual(@RequestParam String name,
                                             @RequestParam(defaultValue = "5") int pageSize,
                                             @RequestParam(defaultValue = "1") int pageNum) {
        List<User> users = facade.getUsersByName(name, pageSize, pageNum);
        return new ModelAndView(ENTITIES_LIST_VIEW, new ModelMap(USERS_ATTRIBUTE_NAME, users));
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

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE,
            produces = "application/json")
    @ResponseBody
    public boolean deleteUser(@PathVariable long id) {
        return facade.deleteUser(id);
    }
}
