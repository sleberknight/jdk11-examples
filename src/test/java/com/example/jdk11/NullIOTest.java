package com.example.jdk11;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

@DisplayName("Null I/O")
class NullIOTest {

    @Test
    void shouldReadNothingFromNullReader() {
        try (var reader = Reader.nullReader()) {
            assertThat(reader.read()).isEqualTo(-1);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void shouldReadNothingFromNullInputStream() {
        try (var inputStream = InputStream.nullInputStream()) {
            assertThat(inputStream.read()).isEqualTo(-1);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    // The following are just examples of Writer.nullWriter
    // and OutputStream.nullOutputStream, since there's no
    // way to verify the null writer and output streams.

    @Test
    void nullWriterExample() {
        try (var writer = Writer.nullWriter()) {
            writer.append('a')
                    .append('b')
                    .append('c')
                    .append("foo")
                    .append("bar")
                    .append("baz");

            writer.flush();
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void nullOutputStreamExample() {
        try (var outputStream = OutputStream.nullOutputStream()) {
            outputStream.write(1);
            outputStream.write(new byte[]{1, 0, 1, 0, 1});

            outputStream.flush();
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }
}
