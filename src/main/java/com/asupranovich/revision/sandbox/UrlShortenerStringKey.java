package com.asupranovich.revision.sandbox;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Supplier;

public class UrlShortenerStringKey {

    private final Map<String, String> urlStorage = new UrlStorage(100);
    private final Supplier<String> keySupplier = new KeySupplier(2);

    private final ReadWriteLock lock = new ReentrantReadWriteLock(true);
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    public String get(String key) {
        try {
            readLock.lock();
            return urlStorage.get(key);
        } finally {
            readLock.unlock();
        }
    }

    public void add(String url) {
        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException("Url should not be null or blank");
        }
        try {
            writeLock.lock();
            String key = keySupplier.get();
            while (urlStorage.containsKey(key)) {
                key = keySupplier.get();
            }
            urlStorage.put(key, url);
        } finally {
            writeLock.unlock();
        }
    }

    static class UrlStorage extends LinkedHashMap<String, String> {
        private final int maxCapacity;

        public UrlStorage(int maxCapacity) {
            super((int) (maxCapacity * 1.5), 0.75f, true);
            this.maxCapacity = maxCapacity;
        }

        @Override
        protected boolean removeEldestEntry(Entry<String, String> eldest) {
            return size() >= maxCapacity;
        }
    }

    static class KeySupplier implements Supplier<String> {

        private final static String KEY_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        private final static Random RANDOM = new Random();
        private final int keyLength;

        public KeySupplier(int keyLength) {
            this.keyLength = keyLength;
        }

        @Override
        public String get() {
            StringBuilder keyBuilder = new StringBuilder();
            for (int i = 0; i < keyLength; i++) {
                int charIndex = RANDOM.nextInt(KEY_CHARS.length());
                keyBuilder.append(KEY_CHARS.charAt(charIndex));
            }
            return keyBuilder.toString();
        }
    }
}
