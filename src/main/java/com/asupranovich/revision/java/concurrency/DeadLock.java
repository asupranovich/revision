package com.asupranovich.revision.java.concurrency;

public class DeadLock {

    private final static Object lock1 = new Object();
    private final static Object lock2 = new Object();

    public static void main(String[] args) throws Exception {

        final Thread thread1 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println(Thread.currentThread().getName() + " got locked on lock1");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " attemps to acquire lock2");
                synchronized (lock2) {
                    System.out.println(Thread.currentThread().getName() + " got locked on lock2");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        final Thread thread2 = new Thread(() -> {
            synchronized (lock2) {
                System.out.println(Thread.currentThread().getName() + " got locked on lock2");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " attemps to acquire lock1");
                synchronized (lock1) {
                    System.out.println(Thread.currentThread().getName() + " got locked on lock1");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

    }

}
