package com.example.jdk11;

import lombok.Value;

@Value
public class Person {

    private String firstName;
    private String lastName;

    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }
}
