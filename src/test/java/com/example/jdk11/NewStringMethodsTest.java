package com.example.jdk11;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("New JDK 11 String methods")
class NewStringMethodsTest {

    private static final String A_STRING = "  this  is  a    string    ";

    @Test
    void shouldStripLeadingAndTrailing() {
        var stripped = A_STRING.strip();

        assertThat(stripped).isEqualTo("this  is  a    string");
    }

    @Test
    void shouldStripLeadingOnly() {
        var stripped = A_STRING.stripLeading();

        assertThat(stripped).isEqualTo("this  is  a    string    ");
    }

    @Test
    void shouldStripTrailingOnly() {
        var stripped = A_STRING.stripTrailing();

        assertThat(stripped).isEqualTo("  this  is  a    string");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", " \t\n", "  \t  \n \t\t "})
    void shouldTestIsBlank(String value) {
        assertThat(value.isBlank()).isTrue();
    }

    @Test
    void shouldExtractLinesFromString() {
        var string = "Line 1\nLine 2\r\nLine 3\rLine 4";

        assertThat(string.lines())
                .containsExactly(
                        "Line 1",
                        "Line 2",
                        "Line 3",
                        "Line 4"
                );
    }

    @Test
    void shouldRepeat() {
        assertThat("Ho! ".repeat(3).stripTrailing())
                .isEqualTo("Ho! Ho! Ho!");
    }
}
