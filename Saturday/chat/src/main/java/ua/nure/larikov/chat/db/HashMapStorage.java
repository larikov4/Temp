package ua.nure.larikov.chat.db;

import ua.nure.larikov.chat.entities.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HashMapStorage {

    private static HashMapStorage instance;
    private static Map<String, User> storage;

    public static synchronized HashMapStorage getInstance() {
        if (instance == null) {
            instance = new HashMapStorage();
        }
        return instance;
    }

    private HashMapStorage() {
        storage = new HashMap<String, User>();
        storage.put("larikov", new User("larikov", "larikov"));
        storage.put("ivanov", new User("ivanov", "ivanov"));
    }


    public void createUser(User user) {
        storage.put(user.getLogin(), user);
    }


    public boolean isExistingUser(String login, String password) {
        return storage.containsKey(login)
                && storage.get(login).getPassword().equals(password);
    }


    public User findUserByLogin(String login) {
        return storage.get(login);
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<User>();
        list.addAll(storage.values());
        return list;
    }


    public void updateUser(User updatedUser) {
        storage.put(updatedUser.getLogin(), updatedUser);
    }


    public void deleteUser(String login) {
        storage.remove(login);
    }

}
