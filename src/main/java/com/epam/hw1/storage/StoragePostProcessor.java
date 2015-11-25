package com.epam.hw1.storage;

import com.epam.hw1.storage.impl.StorageImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;

/**
 * Class handles <code>Storage</code> post processing event.
 *
 * @author Yevhen_Larikov
 */
public class StoragePostProcessor implements BeanPostProcessor {
    private static final Logger LOG = Logger.getLogger(StorageImpl.class);

    private String entitiesFilePath;

    /**
     * Injects filePath where serialized <code>Map</code> of entities is stored.
     *
     * @param filePath the filePath
     */
    public void setEntitiesFilePath(String filePath) {
        URL resource = getClass().getClassLoader().getResource(filePath);
        entitiesFilePath = resource != null ? resource.getFile() : null;
    }

    /**
     * In case of passing bean is instance of <code>Storage</code> fills it with
     * entities stored in Json file.
     *
     * @param bean the bean
     * @param beanName the name of the bean
     * @return bean
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        if (bean instanceof Storage) {
            LOG.info("Filling storage from file: " + entitiesFilePath);
            fillStorage((Storage) bean);
        }
        return bean;
    }

    private void fillStorage(Storage storage) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(entitiesFilePath));
            storage.fillStorageFromJson(scanner.nextLine());
        } catch (FileNotFoundException e) {
            LOG.error("Cannot find file: " + entitiesFilePath, e);
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }

}