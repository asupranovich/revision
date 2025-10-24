package com.asupranovich.revision.sandbox;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class SimpleLoadBalancer {

    private final List<String> hosts;
    private final AtomicInteger counter = new AtomicInteger(0);
    private final java.util.Random random = new java.util.Random();

    private SimpleLoadBalancer(List<String> hosts) {
        this.hosts = hosts;
    }

    public String nextRoundRobin() {
        return next(() -> hosts.get(Math.abs(counter.getAndIncrement() % hosts.size())));
    }

    public String nextRandom() {
        return next(() -> hosts.get(random.nextInt(hosts.size())));
    }

    public String next(Supplier<String> hostSupplier) {
        return hostSupplier.get();
    }
}
