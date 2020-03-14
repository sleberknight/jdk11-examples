package com.example.jdk11;

import com.google.common.io.Resources;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("UnstableApiUsage")
@DisplayName("New JDK 11 Files Methods")
class NewFilesMethodsTest {

    @Test
    void shouldReadStringFromPath() throws IOException {
        var path = pathFromResourceName("sampleTextFile.txt");

        assertThat(Files.readString(path)).isEqualTo("I am a sample text file.");
    }

    @Test
    void shouldWriteStringToPath(@TempDir Path tempDir) throws IOException {
        var path = Paths.get(tempDir.toAbsolutePath().toString(), "sampleOutputFile.txt");

        var text = "Still throws a checked I/O exception, unfortunately";
        var outPath = Files.writeString(path, text);

        assertThat(outPath).exists();
        assertThat(outPath).isEqualTo(path);
        assertThat(Files.readString(outPath, StandardCharsets.UTF_8)).isEqualTo(text);
    }

    @Test
    void shouldAcceptWriteOptions(@TempDir Path tempDir) throws IOException {
        var path = Paths.get(tempDir.toAbsolutePath().toString(), "sampleOutputFile.txt");

        var text = "Still throws a checked I/O exception, unfortunately";
        var outPath = Files.writeString(path, text, StandardOpenOption.CREATE_NEW);

        assertThat(outPath).exists();
        assertThat(outPath).isEqualTo(path);
        assertThat(Files.readString(outPath, StandardCharsets.UTF_8)).isEqualTo(text);
    }

    @SuppressWarnings("SameParameterValue")
    private Path pathFromResourceName(String resourceName) {
        try {
            var uri = Resources.getResource(resourceName).toURI();
            return Path.of(uri);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
