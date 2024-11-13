package com.asupranovich.revision.java.base;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ComparatorTest {

    public static void main(String[] args) {

        List<Integer> list = List.of(5, 6, 2, 3, 8, 9, 10, 44);

        List<Integer> list1 = new ArrayList<>(list);
        list1.sort(Integer::compare);
        System.out.println(list1);

        List<Integer> list2 = new ArrayList<>(list);
        list2.sort(Integer::compareTo);
        System.out.println(list2);

        List<Integer> list3 = new ArrayList<>(list);
        list3.sort(Comparator.naturalOrder());
        System.out.println(list3);
    }

}
