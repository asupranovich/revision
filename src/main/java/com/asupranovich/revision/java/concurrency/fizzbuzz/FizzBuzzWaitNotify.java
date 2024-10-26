package com.asupranovich.revision.java.concurrency.fizzbuzz;

public class FizzBuzzWaitNotify {

    public static void main(String[] args) throws Exception {
        final FizzBuzzPrinter fizzBuzzPrinter = new FizzBuzzPrinter();
        fizzBuzzPrinter.printConcurrently(20);
    }

    static class FizzBuzzPrinter {

        private volatile int current = 1;
        private final Object lock = new Object();

        public void printConcurrently(int n) throws InterruptedException {

            Thread fizzPrinter = new Thread(() -> {
                while (true) {
                    if (current % 3 == 0 && current % 5 > 0) {
                        System.out.println("Fizz!");
                    }
                    if (current >= n) {
                        break;
                    }
                    waitNextNumber();
                }
                System.out.println("fizzPrinter completed");
            });

            Thread buzzPrinter = new Thread(() -> {
                while (true) {
                    if (current % 3 > 0 && current % 5 == 0) {
                        System.out.println("Buzz!");
                    }
                    if (current >= n) {
                        break;
                    }
                    waitNextNumber();
                }
                System.out.println("buzzPrinter completed");
            });

            Thread fizzBuzzPrinter = new Thread(() -> {
                while (true) {
                    if (current % 3 == 0 && current % 5 == 0) {
                        System.out.println("FizzBuzz!");
                    }
                    if (current >= n) {
                        break;
                    }
                    waitNextNumber();
                }
                System.out.println("fizzBuzzPrinter completed");
            });

            Thread numberPrinter = new Thread(() -> {
                while (true) {
                    if (current % 3 > 0 && current % 5 > 0) {
                        System.out.println(current);
                    }
                    if (current >= n) {
                        break;
                    }
                    waitNextNumber();
                }
                System.out.println("numberPrinter completed");
            });

            fizzPrinter.start();
            buzzPrinter.start();
            fizzBuzzPrinter.start();
            numberPrinter.start();

            while (current < n) {
                Thread.sleep(100);
                synchronized (lock) {
                    lock.notifyAll();
                }
                current++;
            }

            fizzPrinter.join();
            buzzPrinter.join();
            fizzBuzzPrinter.join();
            numberPrinter.join();

            System.out.println("Completed");
        }

        private void waitNextNumber() {
            try {
                synchronized (lock) {
                    lock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
