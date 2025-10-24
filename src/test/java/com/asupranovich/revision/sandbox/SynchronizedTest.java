package com.asupranovich.revision.sandbox;

import java.util.concurrent.CountDownLatch;

public class SynchronizedTest {

    public static void main(String[] args) throws Exception {
        Restaurant restaurant = new Restaurant();
        CountDownLatch latch = new CountDownLatch(4);
        Thread visitor1 = new Thread(() -> {
            try {
                restaurant.getOrder("pizza");
                latch.countDown();
            } catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
            }
        });
        visitor1.start();

        Thread visitor2 = new Thread(() -> {
            try {
                restaurant.getOrder("pasta");
                latch.countDown();
            } catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
            }
        });
        visitor2.start();

        Thread visitor3 = new Thread(() -> {
            try {
                restaurant.getOrder("stake");
                latch.countDown();
            } catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
            }
        });
        visitor3.start();

        Thread cook = new Thread(() -> {
            try {
                Thread.sleep(3000L);
                restaurant.notifyOfOrder("stake");
                Thread.sleep(3000L);
                restaurant.notifyOfOrder("pasta");
                Thread.sleep(3000L);
                restaurant.notifyOfOrder("pizza");
                latch.countDown();
            } catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
            }
        });
        cook.start();
        latch.await();
        System.out.println("Terminating");
    }

    static class Restaurant {

        private volatile String readyOrder;
        private final Object lock = new Object();

        void getOrder(String order) throws Exception {
            synchronized (lock) {
                while (!order.equals(readyOrder)) {
                    System.out.println("Waiting for order " + order);
                    lock.wait();
                }
                System.out.println("Got the order " + order);
            }
        }

        void notifyOfOrder(String order) {
            synchronized (lock) {
                System.out.println("Order " + order + " is ready");
                readyOrder = order;
                lock.notifyAll();
            }
        }
    }

}
