package com.epam.hw1.service.version;

/**
 * Provides interface to work with versions through the whole application.
 *
 * @author Yevhen_Larikov on 06.01.2016.
 */
public interface VersionService {
    /**
     * Returns actual version for another event to store into repository.
     * But be aware that before saving event to repository the version of event
     * should be committed using {@link #compareAndSwapActualVersion(long)}.
     *
     * @return actual version
     */
    long getActualVersion();

    /**
     * In case passed version is still actual commits it and increments actual version.
     * Actual version is version that wasn't committed therefore wasn't used for saving
     * any events to repositories.
     *
     * @param version actual version
     * @return boolean whether version is committed
     */
    boolean compareAndSwapActualVersion(long version);
}
