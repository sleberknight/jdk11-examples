package com.example.jdk11;

import javax.annotation.Nonnull;
import java.util.List;

import static java.util.stream.Collectors.groupingBy;

/**
 * Run using -classpath. Must have JAR containing @Nonnull in classpath, e.g. jsr305 annotations JAR.
 *
 * java -classpath /Users/sleberkn/Projects/jdk11-examples/target/classes:/usr/local/user/share/mvn_repo/com/google/code/findbugs/jsr305/3.0.2/jsr305-3.0.2.jar etc/Colors.java
 */
public class Colors {

    public static void main(String[] args) {
        var colors = List.of("red", "orange", "yellow", "green", "blue", "indigo", "violet");
        var colorsByLength = colors
                .stream()
                .filter((@Nonnull var color) -> color.length() > 3)
                .map(String::toUpperCase)
                .collect(groupingBy((@Nonnull var s) -> s.length()));
        System.out.println("colorsByLength = " + colorsByLength);
    }
}
