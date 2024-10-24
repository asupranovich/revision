package com.asupranovich.revision.java.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class MaxSearch {

    public static void main(String[] args) {
        final Random random = new Random();
        final int[] numbers = IntStream.generate(() -> random.nextInt(10000)).limit(1000).toArray();
        final ConcurrentMaxSearch maxSearch = new ConcurrentMaxSearch();
        System.out.println(maxSearch.findMax(numbers));
    }

    static class ConcurrentMaxSearch {

        private static final int NUMBERS_PER_THREAD = 100;

        public int findMax(int[] numbers) {

            int threadCount = numbers.length / NUMBERS_PER_THREAD;
            ExecutorService executorService = Executors.newFixedThreadPool(10);

            List<CompletableFuture<Integer>> futures = new ArrayList<>(threadCount);
            for (int i = 0; i < threadCount; i++) {
                int start = i * NUMBERS_PER_THREAD;
                int end = Math.min(start + NUMBERS_PER_THREAD, numbers.length);
                CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> findMax(numbers, start, end), executorService);
                futures.add(future);
            }

            Integer max = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(i -> {
                    System.out.println("Result thread: " + Thread.currentThread().getName());
                    return futures.stream().map(CompletableFuture::join).max(Integer::compare).orElse(Integer.MIN_VALUE);
                }).join();

            executorService.shutdown();

            return max;
        }

        private int findMax(int[] numbers, int start, int end) {
            int max = Integer.MIN_VALUE;
            for (int i = start; i < end; i++) {
                max = Math.max(max, numbers[i]);
            }
            System.out.println("Thread: " + Thread.currentThread().getName() + ", start: " + start + ", end: " + end + ", max: " + max);
            return max;
        }
    }

}
