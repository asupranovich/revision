package com.asupranovich.revision.java.concurrency;

import java.util.List;
import lombok.SneakyThrows;

public class RobotSteps {

    public static void main(String[] args) throws Exception {

        List<String> legs = List.of("First", "Second", "Third", "Four", "Five");
        RobotLeg.current = legs.get(0);
        for (int i = 0; i < legs.size(); i++) {
            String leg = legs.get(i);
            String nextLeg = legs.get((i + 1) % legs.size());
            Thread legThread = new Thread(new RobotLeg(leg, nextLeg));
            legThread.setDaemon(true);
            legThread.start();
        }

        Thread.sleep(20000);
    }

    static class RobotLeg implements Runnable {

        private final String name;
        private final String next;

        private static String current;
        private static final Object lock = new Object();

        public RobotLeg(String name, String next) {
            this.name = name;
            this.next = next;
        }

        @SneakyThrows
        @Override
        public void run() {
            while (true) {
                synchronized (lock) {
                    while (!name.equals(current)) {
                        lock.wait();
                    }
                    System.out.println("Leg: " + name);
                    Thread.sleep(1000);
                    current = next;
                    lock.notifyAll();
                }
            }
        }
    }
}
