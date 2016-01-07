package com.epam.hw1.service.version.impl;

import com.epam.hw1.service.version.VersionService;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * {@link VersionService} implementation.
 *
 * @author Yevhen_Larikov on 06.01.2016.
 */
@Service
public class VersionServiceImpl implements VersionService {
    private long actualVersion = 1;
    private Lock lock = new ReentrantLock();

    @Override
    public long getActualVersion() {
        return actualVersion;
    }

    @Override
    public boolean compareAndSwapActualVersion(long version) {
        if (version != actualVersion) {
            return false;
        }
        try {
            lock.lock();
            if (version == actualVersion) {
                actualVersion++;
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }
}
