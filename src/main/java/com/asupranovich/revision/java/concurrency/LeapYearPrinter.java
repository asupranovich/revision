package com.asupranovich.revision.java.concurrency;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LeapYearPrinter {

    private int year = 1980;

    private ReentrantLock lock = new ReentrantLock();
    private Condition nonLeapYearCondition = lock.newCondition();
    private Condition leapYearCondition = lock.newCondition();

    public static void main(String[] args) throws Exception {
        LeapYearPrinter leapYearPrinter = new LeapYearPrinter();
        leapYearPrinter.print();
    }

    public void print() throws Exception {
        Thread leapYearThread = new Thread(this::printLeapYear);
        leapYearThread.setDaemon(true);
        leapYearThread.start();

        Thread nonLeapYearThread = new Thread(this::printNonLeapYear);
        nonLeapYearThread.setDaemon(true);
        nonLeapYearThread.start();

        Thread.currentThread().sleep(20000L);
    }

    private void printLeapYear() {
        while (true) {
            try {
                lock.lock();
                while (year % 4 != 0) {
                    leapYearCondition.awaitUninterruptibly();
                }
                System.out.println("" + year + ": Leap Year");
                year++;
                Thread.sleep(500L);
                if (year % 4 == 0) {
                    nonLeapYearCondition.signalAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    private void printNonLeapYear() {
        while (true) {
            try {
                lock.lock();
                while (year % 4 == 0) {
                    nonLeapYearCondition.awaitUninterruptibly();
                }
                System.out.println("" + year + ": Non-Leap Year");
                year++;
                Thread.sleep(500L);
                leapYearCondition.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
