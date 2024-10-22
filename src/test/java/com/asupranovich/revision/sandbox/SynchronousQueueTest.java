package com.asupranovich.revision.sandbox;

import com.asupranovich.revision.java.concurrency.SynchronousQueue;
import org.junit.jupiter.api.Test;

class SynchronousQueueTest {

    @Test
    void testExchange() throws Exception {

        final SynchronousQueue<String> queue = new SynchronousQueue<>();

        Thread consumer1 = new Thread(() -> {
            try {
                Thread.sleep(3000);
                System.out.println("Taking parcel1");
                final String parcel = queue.take();
                System.out.println("Got parcel1: " + parcel);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        consumer1.start();
        Thread consumer2 = new Thread(() -> {
            try {
                Thread.sleep(3000);
                System.out.println("Taking parcel2");
                final String parcel = queue.take();
                System.out.println("Got parcel2: " + parcel);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        consumer2.start();

        Thread producer1 = new Thread(() -> {
            System.out.println("Offering parcel1");
            queue.offer("Hello1");
            System.out.println("Have put the parcel1");
        });
        producer1.start();
        Thread producer2 = new Thread(() -> {
            System.out.println("Offering parcel2");
            queue.offer("Hello2");
            System.out.println("Have put the parcel2");
        });
        producer2.start();

        consumer1.join();
        producer1.join();
        consumer2.join();
        producer2.join();

        System.out.println("Completed");
    }

}