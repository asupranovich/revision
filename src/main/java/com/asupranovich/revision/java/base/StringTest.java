package com.asupranovich.revision.java.base;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import lombok.Getter;

public class StringTest {

    public static void main(String[] args) {
        String s1 = concatAndIntern("A", "B");
        String s2 = concatAndIntern("A", "B");
        String s3 = concatAndIntern("A", "B");

        System.out.println(s1 == s2);
        System.out.println(s2 == s3);
    }

    private static String concatAndIntern(String a, String b) {
        return (a + b).intern();
    }

    @Getter
    static class Interval {

        int start;
        int end;

        public Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    static class Solution {

        public List<Interval> merge(List<Interval> intervals) {
            List<Interval> mergedIntervals = new LinkedList<>();

            intervals.sort((a, b) -> {
                if (a.start == b.start) {
                    return Integer.compare(a.end, b.end);
                } else {
                    return Integer.compare(a.start, b.start);
                }
            });

            Interval first = intervals.get(0);
            Interval current = new Interval(first.start, first.end);

            for (int i = 1; i < intervals.size(); i++) {
                Interval interval = intervals.get(i);
                if (current.end < interval.start) {
                    mergedIntervals.add(current);
                    current = new Interval(interval.start, interval.end);
                } else {
                    current.end = Math.max(current.end, interval.end);
                }
            }

            mergedIntervals.add(current);

            return mergedIntervals;
        }
    }
}
