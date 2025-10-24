package com.asupranovich.revision.sandbox;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import org.junit.jupiter.api.Test;

public class ExecutorsTest {

    @Test
    void testSingleThreadExecutor() {
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        Future<String> future1 = singleThreadExecutor.submit(() -> {
            Thread.sleep(500L);
            System.out.println(Thread.currentThread().getName());
            return "one";
        });
        Future<String> future2 = singleThreadExecutor.submit(() -> {
            Thread.sleep(500L);
            System.out.println(Thread.currentThread().getName());
            return "two";
        });
        Future<String> future3 = singleThreadExecutor.submit(() -> {
            Thread.sleep(500L);
            System.out.println(Thread.currentThread().getName());
            return "three";
        });
        List.of(future1, future2, future3).forEach(f -> {
            try {
                System.out.println(f.get());
            } catch (Exception e) {
                System.out.println("Exception " + e.getMessage());
            }
        });
        singleThreadExecutor.shutdown();
    }

    @Test
    void testFixedThreadPoolExecutor() {
        ExecutorService fixedThreadPoolExecutor = Executors.newFixedThreadPool(3);
        Future<String> future1 = fixedThreadPoolExecutor.submit(() -> {
            Thread.sleep(500L);
            System.out.println(Thread.currentThread().getName());
            return "one";
        });
        Future<String> future2 = fixedThreadPoolExecutor.submit(() -> {
            Thread.sleep(500L);
            System.out.println(Thread.currentThread().getName());
            return "two";
        });
        Future<String> future3 = fixedThreadPoolExecutor.submit(() -> {
            Thread.sleep(500L);
            System.out.println(Thread.currentThread().getName());
            return "three";
        });
        List.of(future1, future2, future3).forEach(f -> {
            try {
                System.out.println(f.getClass() + " " + f.get());
            } catch (Exception e) {
                System.out.println("Exception " + e.getMessage());
            }
        });
        fixedThreadPoolExecutor.shutdown();
    }

    @Test
    void testCachedThreadPoolExecutor() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);
        ScheduledFuture<?> scheduled = scheduledExecutorService.schedule(() -> System.out.println(Thread.currentThread().getName() + " Scheduled to Now"), 4L, TimeUnit.SECONDS);
        try {
            scheduled.get(5L, TimeUnit.SECONDS);
        } catch (Exception e) {
            System.out.println("Got exception: " + e.getMessage());
        }
    }

    @Test
    void testForkJoinPool() {
        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            LockSupport.parkNanos(1000_000L);
            System.out.println("Thread " + Thread.currentThread().getName());
            return "Hello from CF1";
        });
        System.out.println(completableFuture1.whenComplete((r, e) -> System.out.println("Result: " + r + ", Exception: " + e)));

        ForkJoinPool forkJoinPool = new ForkJoinPool(3);
        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            LockSupport.parkNanos(1000_000L);
            System.out.println("Thread " + Thread.currentThread().getName());
            return "Hello from CF2";
        }, forkJoinPool);
        System.out.println(completableFuture2.whenComplete((r, e) -> System.out.println("Result: " + r + ", Exception: " + e)));
    }
}
