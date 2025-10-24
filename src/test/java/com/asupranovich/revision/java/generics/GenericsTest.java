package com.asupranovich.revision.java.generics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;

public class GenericsTest {

    @Test
    void testUpperBound() {
        List<? extends Number> list = List.of(1, 2.5, 3.14);
        list.forEach(i -> System.out.println(i + ", class:" + i.getClass()));
    }

    @Test
    void testLowerBound() {
        List<? super Number> numbers = new ArrayList<>();
        numbers.add(5);
        numbers.add(10.00);
        Number num = Long.valueOf(1);
        numbers.add(num);
    }

    @Test
    void testListConversion() {
        List<Integer> integers = new ArrayList<>();
        integers.addAll(Arrays.asList(1, 2, 3, 4, 5));
        List<? extends Number> numbers = integers;
        List<Number> realNumbers = (List<Number>)numbers;
        realNumbers.add(Long.MAX_VALUE);

        System.out.println(realNumbers);
    }

    @Test
    void testInterfaceConversion() {
        Supplier<Integer> intSupplier = () -> 100;
        Supplier<? extends Number> numberSupplier = intSupplier;
        supply((Supplier<Number>)numberSupplier);
    }

    private void supply(Supplier<Number> numberSupplier) {

    }

    public void addAllShapes() {
        List<? super Shape> shapes = new ArrayList<>();
        addShape(shapes, new Circle());
        addShape(shapes, new Square());
    }

    public void addShape(List<? super Shape> list, Shape shape) {
        list.add(shape);
    }

    class Shape {

    }

    class Circle extends Shape {

    }

    class Square extends Shape {

    }

}
