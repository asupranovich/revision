package com.asupranovich.revision.java.collections.streams;

import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

public class StreamTest {

    @Test
    void StringsGroupByFirstCharacter() {
        List<String> strings = List.of("school", "car", "sandwich", "call", "smart", "alpha", "beta", "charley");
        Map<Character, List<String>> multimap = strings.stream().collect(Collectors.toMap(s -> s.charAt(0), List::of, (l1, l2) -> {
            List<String> result = new ArrayList<>();
            result.addAll(l1);
            result.addAll(l2);
            return result;
        }));
        System.out.println(multimap);

        multimap = strings.stream().filter(s -> s != null && !s.isEmpty()).collect(Collectors.groupingBy(s -> s.charAt(0)));
        System.out.println(multimap);
    }

    @Test
    void testReduce() {
        List<String> words = Arrays.asList("apple", "banana", "cherry", "date");

        String result = words.stream()
            .filter(w -> w.startsWith("a"))
            .map(String::toUpperCase)
            .reduce((a, b) -> a + " " + b).orElse("empty");

        System.out.println(result);
    }

    @Test
    void testGrouppingByLastName() {
        List<String> names = List.of("Alex Supranovich", "Alex Vasilyeu", "John Doe", "Yury Supranovich", "Wowa Supranovich", "Jane Doe", "Ronaldo");
        Map<String, List<String>> grouped = names.stream()
            .filter(n -> n.contains(" "))
            .map(n -> n.split("\\s"))
            .map(a -> new SimpleEntry<>(a[1], a[0]))
            .collect(Collectors.groupingBy(SimpleEntry::getKey, Collectors.mapping(SimpleEntry::getValue, Collectors.toList())));

        grouped.forEach((k, v) -> v.sort(Comparator.naturalOrder()));
        System.out.println(grouped);
    }

}
