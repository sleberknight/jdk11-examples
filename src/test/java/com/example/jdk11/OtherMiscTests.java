package com.example.jdk11;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toUnmodifiableSet;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Other misc. JDK 11 new APIs")
@Slf4j
class OtherMiscTests {

    @Test
    void shouldConvertTimeUnitToDuration() {
        var unit = TimeUnit.of(ChronoUnit.HOURS);
        LOG.debug("TimeUnit: {}", unit);

        var duration = Duration.ofDays(3);
        LOG.debug("Duration: {}", duration);

        long amount = unit.convert(duration);
        LOG.debug("TimeUnit {} amount: {}", unit, amount);

        assertThat(amount).isEqualTo(72L);
    }

    @Test
    void shouldAllowNotPredicate() {
        Predicate<String> longerThanThreeChars = str -> str.length() > 3;

        var filteredNames = List.of("bob", "alice", "carlos", "diane")
                .stream()
                .filter(not(longerThanThreeChars))
                .collect(toUnmodifiableSet());

        assertThat(filteredNames).containsExactly("bob");
    }
}
