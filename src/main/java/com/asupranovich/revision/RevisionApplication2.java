package com.asupranovich.revision;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class RevisionApplication2 implements CommandLineRunner {

    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(RevisionApplication2.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Singleton1 singleton1 = applicationContext.getBean(Singleton1.class);
        Singleton2 singleton2 = applicationContext.getBean(Singleton2.class);
        System.out.println(singleton1.prototype == singleton2.prototype);
    }

    @Component
    static class Singleton1 {
        private final Prototype prototype;
        public Singleton1(Prototype prototype) {
            this.prototype = prototype;
        }
    }

    @Component
    static class Singleton2 {
        private final Prototype prototype;
        public Singleton2(Prototype prototype) {
            this.prototype = prototype;
        }
    }

    @Component
    @Scope("prototype")
    static class Prototype {
        public Prototype() {
            System.out.println("Prototype instance: " + System.currentTimeMillis());
        }
    }
}
