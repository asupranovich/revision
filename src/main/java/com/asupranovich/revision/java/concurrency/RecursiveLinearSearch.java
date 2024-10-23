package com.asupranovich.revision.java.concurrency;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

public class RecursiveLinearSearch {

    public static void main(String[] args) {
        final Solution solution = new Solution();
        System.out.println(solution.find(IntStream.range(-10, 330).toArray(), 160));
    }

    static class Solution {

        public int find(int[] array, int element) {
            final ForkJoinPool forkJoinPool = new ForkJoinPool(3);
            return forkJoinPool.invoke(new ElementSearchTask(array, 0, array.length, element));
        }

        class ElementSearchTask extends RecursiveTask<Integer> {

            private final int[] array;
            private final int start;
            private final int end;
            private final int element;

            ElementSearchTask(int[] array, int start, int end, int element) {
                this.array = array;
                this.start = start;
                this.end = end;
                this.element = element;
            }

            @Override
            protected Integer compute() {

                System.out.println("Computation by " + Thread.currentThread().getName() + ", start: " + start + ", end: " + end);

                if (end - start <= 10) {
                    for (int i = start; i < end; i++) {
                        if (array[i] == element) {
                            return i;
                        }
                    }
                    return -1;
                }

                int mid = start + (end - start) / 2;

                ElementSearchTask left = new ElementSearchTask(array, start, mid, element);
                ElementSearchTask right = new ElementSearchTask(array, mid, end, element);

                left.fork();
                right.fork();

                final Integer leftResult = left.join();
                final Integer rightResult = right.join();

                return Math.max(leftResult, rightResult);
            }
        }
    }

}
