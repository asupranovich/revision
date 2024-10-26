package com.asupranovich.revision.java.concurrency.fizzbuzz;

import java.util.concurrent.CyclicBarrier;

public class FizzBuzzCyclicBarrier {

    public static void main(String[] args) throws Exception {
        final FizzBuzzPrinter fizzBuzzPrinter = new FizzBuzzPrinter();
        fizzBuzzPrinter.printConcurrently(20);
    }

    static class FizzBuzzPrinter {

        private volatile int current = 1;

        public void printConcurrently(int n) throws InterruptedException {

            CyclicBarrier barrier = new CyclicBarrier(4);

            Thread fizzPrinter = new Thread(() -> {
                while (current < n) {
                    if (current % 3 == 0 && current % 5 > 0) {
                        System.out.println("Fizz!");
                        sleep(500);
                        current++;
                    }
                    await(barrier);
                }
                System.out.println("fizzPrinter completed");
            });

            Thread buzzPrinter = new Thread(() -> {
                while (current < n) {
                    if (current % 3 > 0 && current % 5 == 0) {
                        System.out.println("Buzz!");
                        sleep(500);
                        current++;
                    }
                    await(barrier);
                }
                System.out.println("buzzPrinter completed");
            });

            Thread fizzBuzzPrinter = new Thread(() -> {
                while (current < n) {
                    if (current % 3 == 0 && current % 5 == 0) {
                        System.out.println("FizzBuzz!");
                        sleep(500);
                        current++;
                    }
                    await(barrier);
                }
                System.out.println("fizzBuzzPrinter completed");
            });

            Thread numberPrinter = new Thread(() -> {
                while (current < n) {
                    if (current % 3 > 0 && current % 5 > 0) {
                        System.out.println(current);
                        sleep(500);
                        current++;
                    }
                    await(barrier);
                }
                System.out.println("numberPrinter completed");
            });

            fizzPrinter.start();
            buzzPrinter.start();
            fizzBuzzPrinter.start();
            numberPrinter.start();

            fizzPrinter.join();
            buzzPrinter.join();
            fizzBuzzPrinter.join();
            numberPrinter.join();

            System.out.println("Completed");
        }

        private void sleep(long millis) {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void await(CyclicBarrier barrier) {
            try {
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
