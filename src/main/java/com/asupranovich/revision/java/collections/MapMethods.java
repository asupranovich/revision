package com.asupranovich.revision.java.collections;

import java.util.HashMap;
import java.util.Map;

public class MapMethods {

    public static void main(String[] args) {

        Map<String, Integer> map = new HashMap<>();

        map.put("One", 1);
        Integer one = map.putIfAbsent("One", 2); //

        map.getOrDefault("Two", 2); //2
        map.replace("One", -1); // replaces only if key exists in map
        map.replace("Three", 3); // does nothing, returns null;

    }
    
    
}
