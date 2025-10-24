package com.asupranovich.revision.java.base;

public class InterfaceTest {

    interface Eatable {
        int i = 10;
        void eat();
        static void display() {

        }
        default void move() {
            System.out.println("Move!");
        }

    }

    interface Sleepable {
        void sleep();
        default void move() {
            System.out.println("Move!");
        }
    }

    class Person implements Eatable, Sleepable {

        @Override
        public void eat() {

        }

        @Override
        public void move() {
            Sleepable.super.move();
        }

        @Override
        public void sleep() {

        }



    }

    class Parent {
        protected Number calc(Number n) throws Exception {
            return null;
        }
    }

    class Child extends Parent {

        @Override
        public Integer calc(Number n) throws IllegalAccessError {
            return null;
        }
    }



}
