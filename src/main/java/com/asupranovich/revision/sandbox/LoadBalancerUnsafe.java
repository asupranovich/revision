package com.asupranovich.revision.sandbox;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class LoadBalancerUnsafe {

    private final int maxCapacity;
    private final List<String> hosts;
    private final Function<List<String>, String> strategy;

    public LoadBalancerUnsafe(int capacity, Function<List<String>, String> strategy) {
        this.maxCapacity = capacity;
        this.hosts = new ArrayList<>(capacity);
        this.strategy = strategy;
    }

    public boolean register(String host) {
        if (host == null || host.isBlank()) {
            throw new IllegalArgumentException("Host should not be null or blank");
        }
        if (hosts.size() >= maxCapacity) {
            return false;
        }
        if (hosts.contains(host)) {
            return false;
        }
        return hosts.add(host);
    }

    public boolean deregister(String host) {
        return hosts.remove(host);
    }

    public String get() {
        if (hosts.isEmpty()) {
            return null;
        }
        return strategy.apply(hosts);
    }

    public static LoadBalancerUnsafe roundRobin(int maxCapacity) {
        return new LoadBalancerUnsafe(maxCapacity, new RoundRobin());
    }

    static class RoundRobin implements Function<List<String>, String> {

        private int counter = 0;

        @Override
        public String apply(List<String> hosts) {
            final int hostIndex = counter++ % hosts.size();
            if (counter == Integer.MAX_VALUE) {
                counter = 0;
            }
            return hosts.get(hostIndex);
        }
    }
}
