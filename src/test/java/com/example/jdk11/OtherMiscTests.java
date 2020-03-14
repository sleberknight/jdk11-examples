package com.example.jdk11;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

@DisplayName("Other misc. JDK 11 new APIs")
class OtherMiscTests {

    @Test
    void shouldConvertTimeUnitToDuration() {
        // TODO

        TimeUnit unit = TimeUnit.of(ChronoUnit.HOURS);
        System.out.println("unit = " + unit);

        Duration duration = Duration.ofDays(45);
        System.out.println("duration = " + duration);

        long amount = unit.convert(duration);
        System.out.println("amount = " + amount);

        // TimeUnit.convert
    }

    @Test
    void shouldAllowNotPredicate() {
        // TODO
    }
}
