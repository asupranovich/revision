package com.asupranovich.revision.sandbox;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest {

    public static void main(String[] args) {

        LoadBalancer loadBalancer = new LoadBalancer();

    }

    static class LoadBalancer {

        private final List<String> hosts = new ArrayList<>();
        private final AtomicInteger counter = new AtomicInteger(0);

        private final ReadWriteLock lock = new ReentrantReadWriteLock();
        private final Lock readLock = lock.readLock();
        private final Lock writeLock = lock.writeLock();

        public void addHost(String host) {
            writeLock.lock();
            try {
                hosts.add(host);
            } finally {
                writeLock.unlock();
            }
        }

        public String getHost() {
            readLock.lock();
            try {
                if (hosts.isEmpty()) {
                    return null;
                }
                return hosts.get(Math.abs(counter.getAndIncrement() % hosts.size()));
            } finally {
                readLock.unlock();
            }
        }

        public void deleteHost(int index) {
            writeLock.lock();
            try {
                hosts.remove(index);
            } finally {
                writeLock.unlock();
            }
        }
    }

}
