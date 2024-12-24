package com.asupranovich.revision.java.base;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ComparatorTest {

    public static void main(String[] args) {
        compareInteger();
        chainCompare();
    }

    private static void compareInteger() {
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

        List<List<Integer>> list4 = new ArrayList<>(List.of(List.of(1, 3), List.of(1, 2), List.of(2, 4)));
        list4.sort(Comparator.comparingInt((List<Integer> a) -> a.get(0)).thenComparingInt(a -> a.get(1)));
        System.out.println(list4);
    }

    private static void chainCompare() {
        List<Integer> integers = new ArrayList<>(List.of(1, 2, 3, 4, 5));
        integers.sort(Comparator.comparingInt(Integer::intValue));

        List<User> users = List.of(new User("A", "B"), new User("A", "A"), new User("B", "A"), new User("B", "Z"), new User("B", "Q"));
        List<User> usersToSort = new ArrayList<>(users);
        usersToSort.sort(Comparator.comparing(User::firstName).thenComparing(User::lastName));
        System.out.println(usersToSort);
    }

    record User (String firstName, String lastName){}
}
