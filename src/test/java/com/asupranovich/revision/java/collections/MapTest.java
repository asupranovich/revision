package com.asupranovich.revision.java.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MapTest {

    private Map<String, Integer> map;

    @BeforeEach
    void init() {
         map = new HashMap<>();
         map.put("One", 1);
         map.put("Two", 2);
    }

    @Test
    void putIfAbsent() {
        Integer one = map.putIfAbsent("One", -1); // returns existing value
        assertEquals(1, one);

        Integer three = map.putIfAbsent("Three", 3); // adds pair and returns null
        assertNull(three);
        assertEquals(3, map.get("Three"));
    }

    @Test
    void computeIfAbsent() {
        Integer one = map.computeIfAbsent("One", k -> k.hashCode()); // returns existing if key was added
        assertEquals(1, one);

        Integer three = map.computeIfAbsent("Three", k -> k.hashCode());
        assertNotNull(three);
    }

    @Test
    void computeIfPresent() {
        Integer one = map.computeIfPresent("One", (k, v) -> -v); // applied function to value and updates existing value
        assertEquals(-1, one);

        Integer two = map.computeIfPresent("Two", (k, v) -> null); // if key exists, deletes key if function returns null
        assertFalse(map.containsKey("Two"));
        assertNull(two);

        Integer three = map.computeIfPresent("Three", (k, v) -> -v); // if key doesn't exist, returns null
        assertNull(three);
    }

    @Test
    void compute() {
        Integer result1 = map.compute("One", (k, v) -> v == null ? null : v * 10); // updates value if exists
        assertEquals(10, result1);

        Integer result3 = map.compute("Three", (k, v) -> v == null ? 0 : v * 10); // adds new pair if function returns non-null
        assertEquals(0, result3);

        Integer result2 = map.compute("Two", (k, v) -> v == null || v < 5 ? null : v * 10); // removes the pair if function returns null
        assertNull(result2);
        assertFalse(map.containsKey("Two"));
    }

    @Test
    void merge() {
        Integer one = map.merge("One", -1, (v1, v2) -> v2);
        assertEquals(-1, one);

        Integer two = map.merge("Two", -2, (v1, v2) -> null);
        assertNull(two);

        Integer three = map.merge("Three", 3, (v1, v2) -> v2);
        assertEquals(3, three);
    }

    @Test
    void replaceKeyValue() {
        Integer one = map.replace("One", -1); // replaces value if key was added, returns previous value
        assertEquals(1, one);
        assertEquals(-1, map.get("One"));

        Integer three = map.replace("Three", -3); // does nothing if key was not added
        assertNull(three);
    }

    @Test
    void replaceKeyOldValueNewValue() {
        boolean wasOneReplaced = map.replace("One", 1, -1); // old value matches existing -> do the change
        assertTrue(wasOneReplaced);

        boolean wasTwoReplaced = map.replace("Two", -2, 2); // old doesn't match existing -> no change
        assertFalse(wasTwoReplaced);

        boolean wasThreeReplaced = map.replace("Three", 3, -3); // key was not added -> no change
        assertFalse(wasThreeReplaced);
    }
}
