package com.asupranovich.revision.sandbox;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

class LoadBalancerUnsafeTest {

    @RepeatedTest(100)
    @Execution(ExecutionMode.CONCURRENT)
    void testConcurrency() {

        final LoadBalancerUnsafe loadBalancer = LoadBalancerUnsafe.roundRobin(5);
        loadBalancer.register("A");
        loadBalancer.register("B");
        loadBalancer.register("C");
        loadBalancer.register("D");
        loadBalancer.register("E");

        final ExecutorService executorService = Executors.newFixedThreadPool(5);

        List<Future<String>> futures = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            final Future<String> future = executorService.submit(() -> {
                String host = loadBalancer.get();
                System.out.println(host);
                return host;
            });
            futures.add(future);
        }

        Set<String> hosts = futures.stream()
            .map(f -> {
                try {
                    return f.get(1, TimeUnit.SECONDS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }).collect(Collectors.toSet());
        Assertions.assertEquals(5, hosts.size());
    }

}