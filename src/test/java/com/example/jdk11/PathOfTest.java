package com.example.jdk11;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.net.URI;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("New Path.of methods")
class PathOfTest {

    @Test
    void shouldCreateFromAbsoluteFileURI() {
        var path = Path.of(URI.create("file:///data/src/project/script.sh"));

        assertThat(path).asString().isEqualTo("/data/src/project/script.sh");
    }

    @ParameterizedTest
    @ValueSource(strings = {"data", "/data"})
    void shouldCreateFromSingleString(String value) {
        var path = Path.of(value);

        assertThat(path).asString()
                .describedAs("should be equal to itself")
                .isEqualTo(value);
    }

    @Test
    void shouldCreateFromMultipleStrings() {
        assertAll(
                () -> assertThat(Path.of("data", "src", "project", "script.sh"))
                        .asString()
                        .isEqualTo("data/src/project/script.sh"),
                () -> assertThat(Path.of("/data", "src", "project", "script.sh"))
                        .asString()
                        .isEqualTo("/data/src/project/script.sh")
        );
    }

    @Test
    void shouldIgnoreLeadingAndTrailingSlashesInEachPathComponent() {
        var path = Path.of("/data/", "/src/", "project/", "/script.sh");

        assertThat(path).asString().isEqualTo("/data/src/project/script.sh");
    }
}
