package com.asupranovich.revision.sandbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class Tasks {

    /*
    1. Merge Two Sorted Lists
     create a method that accept heads of two sorted lists list1 and list2.
     Merge the two lists in a one sorted list.
     examples
     [1, 2, 5, 6] [3, 4, 7] -> [1, 2, 3, 4, 5, 6, 7]
     [1, 2, 3, 4, 8] [2, 5, 6, 9] -> [1, 2, 2, 3, 4, 5, 6, 8, 9]
     */
    @Test
    void mergeSortedLists() {
        int[] first = new int[] {1, 2, 5, 6};
        int[] second = new int[] {2, 3, 4, 7};

        int[] result = new int[first.length + second.length];
        int f = 0;
        int s = 0;
        int r = 0;
        while (r < result.length) {
            if (f == first.length) {
                result[r] = second[s++];
            } else if (s == second.length) {
                result[r] = first[f++];
            } else {
                int fVal = first[f];
                int sVal = second[s];
                if (fVal < sVal) {
                    result[r] = fVal;
                    f++;
                } else {
                    result[r] = sVal;
                    s++;
                }
            }
            r++;
        }
        System.out.println(Arrays.toString(result));
    }

    /*
    2. Remove element

    // Given an integer array nums and an integer val, remove all occurrences of val in nums in-place. The relative order of the elements may be changed.
    // You must instead have the result be placed in the first part of the array nums. More formally, if there are k elements after removing the duplicates, then the first k elements of nums should hold
    // the final result. It does not matter what you leave beyond the first k elements.
    // Return k after placing the final result in the first k slots of nums.
    // Do not allocate extra space for another array. You must do this by modifying the input array in-place with O(1) extra memory.
    // examples
    // 5 [1, 2, 5, 6, 7] -> 4 [1, 2, 6, 7, 0]
    // 3 [3, 2, 3, 3, 4, 5] -> 3 [2, 4, 5, 0, 0, 0]
     */
    @Test
    void removeElement() {
        int[] source = new int[] {5, 1, 2, 5, 5, 5, 6, 5, 7, 8, 9, 5};
        int target = 5;

        int targetIndex = -1;
        for (int i = 0; i < source.length; i++) {
            if (source[i] == target) {
                targetIndex = i;
                break;
            }
        }

        if (targetIndex < 0) {
            System.out.println("No target found");
            return;
        }

        int shiftIndex = targetIndex + 1;
        for (int i = targetIndex; i < source.length; i++) {
            while (shiftIndex < source.length && source[shiftIndex] == target) {
                shiftIndex++;
            }
            if (shiftIndex < source.length) {
                source[i] = source[shiftIndex];
            } else {
                source[i] = 0;
            }
            shiftIndex++;
        }

        System.out.println(Arrays.toString(source));
    }

    /*
    3. Implement strstr

    // Locate substring
    // Return an index to the first occurrence of str2 in str1, or a -1 pointer if str2 is not part of str1.
    // examples
    // "abcdefg" "def" -> 3
    // "abcdefg" "fgh" -> -1
     */
    @Test
    void stringIndexOf() {
        String source = "abcdefg";
        String target = "defga";

        for (int i = 0; i < source.length(); i++) {
            if (source.charAt(i) == target.charAt(0)) {
                boolean allMatch = true;
                for (int j = 1; j < target.length(); j++) {
                    if (i + j >= source.length() || source.charAt(i + j) != target.charAt(j)) {
                        allMatch = false;
                        break;
                    }
                }
                if (allMatch) {
                    System.out.println("Index: " + i);
                    return;
                }
            }
        }
        System.out.println("Index: -1");
    }

    /*
    7. Zero Sum Pairs
    // Create a method that accepts an integer array
    // and returns all pairs of numbers [a, b] such that a + b = 0
    // Array is not sorted and has any size. Do not count repetitions
    // What is the result algorithm complexity in big O notation?
    // examples
    // [1, 2, -1, -2, 3] -> [[1, -1], [2, -2]]
    // [1, 2, 3] -> [[]]
     */
    @Test
    void zeroSumPairs() {
        int[] arr = new int[] {1, 2, -1, -2, 0, 1, -1, 100, -100, 3};

        Set<Integer> negatives = new HashSet<>();
        int zeroCont = 0;
        for (int i : arr) {
            if (i == 0) {
                zeroCont++;
            } else if (i < 0) {
                negatives.add(i);
            }
        }
        if (negatives.size() == 0) {
            System.out.println("[]");
            return;
        }

        List<int[]> pairs = new ArrayList<>();
        if (zeroCont > 1) {
            pairs.add(new int[]{0, 0});
        }
        for (int i : arr) {
            if (i > 0 && negatives.remove(-i)) {
                pairs.add(new int[]{i, -i});
            }
        }
        pairs.forEach(p -> System.out.println(Arrays.toString(p)));
    }

    /*
    9. Array Cyclic Rotation
    // Create a method that accepts an integer array A and an integer K.
    // and rotate array A K times.
    // Rotation of the array means that each element is shifted right by one index, and the last element of the array is moved to the first place.
    // For example, the rotation of array A = [3, 8, 9, 7, 6] is [6, 3, 8, 9, 7]
    // That is, each element of A will be shifted to the right K times.
    // What is the result algorithm complexity in big O notation?
    // Examples
    // [3, 8, 9, 7, 6], 2 -> [9, 7, 6, 3, 8]
    // [0, 0, 0], 1 -> [0, 0, 0]
    // [1, 2, 3, 4], 4 -> [1, 2, 3, 4]
     */
    @Test
    void arrayCyclicRotation() {
        int[] array = new int[] {1, 2,3 ,4, 5,6,7, 8, 9,0};
        int k = 102;
        int rotations = k % array.length;
        int [] temp = new int[rotations];
        System.arraycopy(array, 0, temp, 0, rotations);
        System.arraycopy(array, rotations, array, 0, array.length - rotations);
        System.arraycopy(temp, 0, array, array.length - rotations, rotations);
        System.out.println(Arrays.toString(array));
    }
}
