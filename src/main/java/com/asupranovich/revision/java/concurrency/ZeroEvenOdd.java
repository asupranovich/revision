package com.asupranovich.revision.java.concurrency;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ZeroEvenOdd {

    private static volatile int counter = 0;
    private static boolean numberPrinted = true;

    public static void main(String[] args) throws Exception {

        ReentrantLock lock = new ReentrantLock(true);
        Condition zeroCondition = lock.newCondition();
        Condition numberCondition = lock.newCondition();

        Thread zero = new Thread(() -> {
            while (counter < 10) {
                lock.lock();
                try {
                    while (!numberPrinted) {
                        System.out.println("zero await");
                        zeroCondition.await();
                    }
                    Thread.sleep(500);
                    System.out.println(0);
                    numberPrinted = false;
                    counter++;
                    numberCondition.signalAll();
                } catch (Exception e) {
                    // do nothing
                } finally {
                    lock.unlock();
                }
            }
        });

        Thread odd = new Thread(() -> {
            while (counter < 10) {
                lock.lock();
                try {
                    while (numberPrinted || counter % 2 == 0) {
                        System.out.println("odd await");
                        numberCondition.await();
                    }
                    Thread.sleep(500);
                    System.out.println(counter);
                    numberPrinted = true;
                    zeroCondition.signal();
                } catch (Exception e) {
                    // do nothing
                } finally {
                    lock.unlock();
                }
            }
        });

        Thread even = new Thread(() -> {
            while (counter < 10) {
                lock.lock();
                try {
                    while (numberPrinted || counter % 2 != 0) {
                        System.out.println("even await");
                        numberCondition.await();
                    }
                    Thread.sleep(500);
                    System.out.println(counter);
                    numberPrinted = true;
                    zeroCondition.signal();
                } catch (Exception e) {
                    // do nothing
                } finally {
                    lock.unlock();
                }
            }
        });

        zero.start();
        even.start();
        odd.start();

        zero.join();
        even.join();
        odd.join();
    }
}
