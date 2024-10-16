package com.asupranovich.revision.sandbox;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public class UrlShortenerIntKey {

    private final Map<String, String> urlStorage = new ConcurrentHashMap<>();
    private final Supplier<String> keySupplier = new SequentialIntKeySupplier();

    public String get(String key) {
        return urlStorage.get(key);
    }

    public void add(String url) {
        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException("Url should not be null or blank");
        }
        String key = keySupplier.get();
        urlStorage.put(key, url);
    }

    static class SequentialIntKeySupplier implements Supplier<String> {

        private int counter;

        @Override
        public String get() {
            if (counter >= 99) {
                counter = 0;
            }
            return "%02d".formatted(++counter);
        }
    }
}
