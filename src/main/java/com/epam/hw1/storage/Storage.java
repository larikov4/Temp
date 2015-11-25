package com.epam.hw1.storage;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Storage interface. It can be used by Dao classes to store entities.
 *
 * @author Yevhen_Larikov
 */
public interface Storage {
    /**
     * Retries value from inner storage by given key.
     * @param key key
     * @param <T> type of value
     * @return value associated with key.
     */
    <T> T get(String key);

    /**
     * Retries copy of inner storage.
     * @return map
     */
    Map<String, Object> getAll();

    /**
     * Puts value into inner storage by given key.
     * @param key key
     * @param value value
     * @param <T> type of value
     * @return inserted value
     */
    <T> T put(String key, T value);

    /**
     * Removes value by given key.
     * @param key key
     * @return boolean whether key was deleted or not
     */
    boolean remove(String key);

    /**
     * Fill inner storage by parsing given Json string and inserting all entries to storage.
     * @param json json string
     */
    void fillStorageFromJson(String json);
}
