package com.asupranovich.revision.java.concurrency;

public class SingletonExample {

    static class Singleton {

        private static volatile Singleton instance;
        private final String property;

        private Singleton() {
            this.property = "Hello, Singleton";
        }

        public static Singleton getInstance() {
            if (instance == null) {
                synchronized (Singleton.class) {
                    if (instance == null) {
                        Singleton singleton = new Singleton();
                        instance = singleton;
                    }
                }
            }
            return instance;
        }
    }
}
