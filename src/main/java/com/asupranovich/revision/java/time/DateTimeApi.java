package com.asupranovich.revision.java.time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateTimeApi {

    public static void main(String[] args) {
        ZonedDateTime warsawNow = Instant.now().atZone(ZoneId.of("Europe/Warsaw"));
        ZonedDateTime londonNow = warsawNow.withZoneSameInstant(ZoneId.of("Europe/London"));
        System.out.println("Warsaw time: " + warsawNow);
        System.out.println("London warsawNow: " + londonNow);
    }
}
