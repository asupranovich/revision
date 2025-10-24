package com.asupranovich.revision.java.base;

public class ThrowableTest {

    public static void main(String[] args) throws Throwable {
        try {
            throw new Throwable("Throwable");
        } catch (Throwable e) {
            System.out.println("error!");
        }
    }

}
