package com.asupranovich.revision.java.concurrency.fizzbuzz;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class FizzBuzzLock {

    public static void main(String[] args) throws Exception {
        final FizzBuzzPrinter fizzBuzzPrinter = new FizzBuzzPrinter();
        fizzBuzzPrinter.printConcurrently(20);
    }

    static class FizzBuzzPrinter {

        private volatile int current = 1;
        private final ReentrantLock lock = new ReentrantLock();
        private final Condition waitCondition = lock.newCondition();

        public void printConcurrently(int n) throws InterruptedException {

            Thread fizzPrinter = new Thread(() -> {
                while (true) {
                    try {
                        lock.lock();
                        while (!(current % 3 == 0 && current % 5 != 0) && current < n) {
                            await();
                        }
                        if (current > n) {
                            break;
                        }
                        System.out.println("Fizz!");
                        sleep(500);
                        current++;
                    } finally {
                        waitCondition.signalAll();
                        lock.unlock();
                    }
                }
                System.out.println("fizzPrinter completed");
            });

            Thread buzzPrinter = new Thread(() -> {
                while (true) {
                    try {
                        lock.lock();
                        while (!(current % 3 != 0 && current % 5 == 0) && current < n) {
                            await();
                        }
                        if (current > n) {
                            waitCondition.signalAll();
                            break;
                        }
                        System.out.println("Buzz!");
                        sleep(500);
                        current++;
                    } finally {
                        waitCondition.signalAll();
                        lock.unlock();
                    }
                }
                System.out.println("buzzPrinter completed");
            });

            Thread fizzBuzzPrinter = new Thread(() -> {
                while (true) {
                    try {
                        lock.lock();
                        while (!(current % 3 == 0 && current % 5 == 0) && current < n) {
                            await();
                        }
                        if (current > n) {
                            break;
                        }
                        System.out.println("FizzBuzz!");
                        sleep(500);
                        current++;
                    } finally {
                        waitCondition.signalAll();
                        lock.unlock();
                    }
                }
                System.out.println("fizzBuzzPrinter completed");
            });

            Thread numberPrinter = new Thread(() -> {
                while (true) {
                    try {
                        lock.lock();
                        while (!(current % 3 != 0 && current % 5 != 0) && current < n) {
                            await();
                            System.out.println("numberPrinter got released");
                        }
                        if (current > n) {
                            break;
                        }
                        System.out.println(current);
                        sleep(500);
                        current++;
                    } finally {
                        waitCondition.signalAll();
                        lock.unlock();
                    }
                }
                System.out.println("numberPrinter completed");
            });

            fizzPrinter.setName("fizzPrinter");
            buzzPrinter.setName("buzzPrinter");
            fizzBuzzPrinter.setName("fizzBuzzPrinter");
            numberPrinter.setName("numberPrinter");

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

        private void await() {
            try {
                System.out.println(Thread.currentThread().getName() + " awaits");
                waitCondition.await();
                System.out.println(Thread.currentThread().getName() + " released");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
