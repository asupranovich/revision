package com.asupranovich.revision.sandbox;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class LastRecentlyUsedCache extends LinkedHashMap<String, String> {
    private final int maxCapacity;
    public LastRecentlyUsedCache(int limit) {
        super(limit, 0.75f, true);
        this.maxCapacity = limit;
    }

    @Override
    protected boolean removeEldestEntry(Entry<String, String> eldest) {
        return size() > maxCapacity;
    }
}
