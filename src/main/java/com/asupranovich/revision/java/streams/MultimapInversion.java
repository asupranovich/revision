package com.asupranovich.revision.java.streams;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MultimapInversion {

    public static void main(String[] args) {

        Map<Integer, List<Long>> multimap = new HashMap<>();
        multimap.put(1, List.of(2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L));
        multimap.put(2, List.of(10L, 11L, 12L, 13L, 14L, 15L));

        Map<Long, Integer> map = multimap.entrySet().stream()
            .flatMap(entry -> entry.getValue().stream().map(value -> new SimpleEntry<>(value, entry.getKey())))
            .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));

        System.out.println(map);
    }

}
