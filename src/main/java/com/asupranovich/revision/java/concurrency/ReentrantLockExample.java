package com.asupranovich.revision.java.concurrency;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample {

    public static void main(String[] args) throws InterruptedException {

        final MessageQueue messageQueue = new MessageQueue();

        Thread consumer = new Thread(() -> {
            while (true) {
                try {
                    messageQueue.poll();
                    Thread.sleep((long)(Math.random() * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread producer = new Thread(() -> {
            int messageCount = 0;
            while (true) {
                try {
                    messageQueue.push("Message " + ++messageCount);
                    Thread.sleep((long)(Math.random() * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        consumer.setDaemon(true);
        consumer.start();
        producer.setDaemon(true);
        producer.start();
        Thread.sleep(15000);
        System.out.println("Completed");
    }
}

class MessageQueue {
    private final static int MAX_CAPACITY = 10;
    private final Queue<String> queue;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition emptyQueue = lock.newCondition();
    private final Condition maxCapacity = lock.newCondition();

    public MessageQueue() {
        this.queue = new LinkedList<>();
    }

    public void push(String message) throws InterruptedException {
        try {
            lock.lock();
            sleepSafe();
            while (queue.size() > MAX_CAPACITY) { // prevent
                System.out.println("Waiting for space");
                maxCapacity.await();
            }
            System.out.println("Adding message: " + message);
            queue.offer(message);
            emptyQueue.signal();
        } finally {
            lock.unlock();
        }
    }

    public String poll() throws InterruptedException {
        try {
            lock.lock();
            sleepSafe();
            while (queue.isEmpty()) {
                System.out.println("Waiting for messages");
                emptyQueue.await();
            }

            final String message = this.queue.poll();
            maxCapacity.signal();
            System.out.println("Returning message: " + message);
            return message;
        } finally {
            lock.unlock();
        }
    }

    private void sleepSafe() {
        try {
            Thread.sleep((long)(Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
