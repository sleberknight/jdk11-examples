package com.example.jdk11;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

@SuppressWarnings("squid:S106")
public class HttpClientExample {

    public static void main(String[] args) throws InterruptedException, IOException {
        var client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .version(HttpClient.Version.HTTP_2)
                .build();

        var request = HttpRequest.newBuilder()
                .uri(URI.create("https://twitter.com/sleberknight"))
                .timeout(Duration.ofSeconds(5))
                .build();

        // Make a synchronous request
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        System.out.println("Headers:");
        System.out.println(response.headers());

        System.out.println("Body:");
        System.out.println(response.body());
    }
}
