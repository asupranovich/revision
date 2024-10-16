package com.asupranovich.revision.sandbox;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Function;

public class LoadBalancer {

    private final int maxCapacity;
    private final List<String> hosts;
    private final Function<List<String>, String> strategy;

    private final ReadWriteLock lock = new ReentrantReadWriteLock(true);
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    public LoadBalancer(int capacity, Function<List<String>, String> strategy) {
        this.maxCapacity = capacity;
        this.hosts = new ArrayList<>(capacity);
        this.strategy = strategy;
    }

    public boolean register(String host) {
        if (host == null || host.isBlank()) {
            throw new IllegalArgumentException("Host should not be null or blank");
        }
        try {
            writeLock.lock();
            if (hosts.size() >= maxCapacity) {
                return false;
            }
            if (hosts.contains(host)) {
                return false;
            }
            return hosts.add(host);
        } finally {
            writeLock.unlock();
        }
    }

    public boolean deregister(String host) {
        try {
            writeLock.lock();
            return hosts.remove(host);
        } finally {
            writeLock.unlock();
        }
    }

    public String get() {
        try {
            readLock.lock();
            if (hosts.isEmpty()) {
                return null;
            }
            return strategy.apply(hosts);
        } finally {
            readLock.unlock();
        }
    }

    public static LoadBalancer roundRobin(int maxCapacity) {
        return new LoadBalancer(maxCapacity, new RoundRobin());
    }

    static class RoundRobin implements Function<List<String>, String> {

        private final AtomicInteger counter = new AtomicInteger();

        @Override
        public String apply(List<String> hosts) {
            final int hostIndex = counter.getAndIncrement() % hosts.size();
            if (counter.get() == Integer.MAX_VALUE) {
                counter.compareAndSet(Integer.MAX_VALUE, 0);
            }
            return hosts.get(hostIndex);
        }
    }
}
