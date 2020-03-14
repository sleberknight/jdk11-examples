package com.example.jdk11;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.nonNull;

@SuppressWarnings("squid:S106")
public class HttpClientAsyncExample {

    public static void main(String[] args) throws InterruptedException {
        new HttpClientAsyncExample().makeRequest();

        // Sleep to allow time for async request to complete
        TimeUnit.SECONDS.sleep(5);
    }

    private void makeRequest() {
        var client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .version(HttpClient.Version.HTTP_2)
                .build();

        var request = HttpRequest.newBuilder()
                .uri(URI.create("https://github.com/sleberknight"))
                .timeout(Duration.ofSeconds(5))
                .build();

        // Make asynchronous request; process response body asynchronously
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .whenCompleteAsync(this::processResponseBody);
    }

    private void processResponseBody(String body, Throwable error) {
        printMessage("Process response body or error");

        if (nonNull(error)) {
            printMessage("Handle error");
            error.printStackTrace(System.err);
            return;
        }

        printMessage("Handle response body");
        System.out.println(body);
    }

    private void printMessage(String message) {
        System.out.printf("[%s] %s%n", Thread.currentThread().getName(), message);
    }
}
