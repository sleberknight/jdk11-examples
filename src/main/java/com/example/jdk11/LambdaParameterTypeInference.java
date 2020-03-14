package com.example.jdk11;

import javax.annotation.Nonnull;
import java.util.List;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toUnmodifiableList;

@SuppressWarnings({"squid:S106", "squid:S1192", "squid:S1612"})
public class LambdaParameterTypeInference {

    @SuppressWarnings("Convert2MethodRef")
    public static void main(String[] args) {
        var people = List.of(
                new Person("Alice", "Jones"),
                new Person("Bob", "Smith"),
                new Person("Carlos", "Smith"),
                new Person("Diane", "Johnson")
        );
        var smithFullNames = people.stream()
                .filter((@Nonnull var person) -> "Smith".equals(person.getLastName()))
                .map(Person::getFullName)
                .sorted()
                .collect(toUnmodifiableList());
        System.out.println("smithFullNames = " + smithFullNames);


        var colors = List.of("red", "orange", "yellow", "green", "blue", "indigo", "violet");
        var colorsByLength = colors
                .stream()
                .filter((@Nonnull var color) -> color.length() > 3)
                .map(String::toUpperCase)
                .collect(groupingBy((@Nonnull var s) -> s.length()));
        System.out.println("colorsByLength = " + colorsByLength);
    }
}
