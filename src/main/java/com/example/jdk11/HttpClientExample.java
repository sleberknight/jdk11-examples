package com.example.jdk11;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@SuppressWarnings("squid:S106")
public class HttpClientExample {

    public static void main(String[] args)
            throws InterruptedException, TimeoutException, ExecutionException {
        
        var client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .version(HttpClient.Version.HTTP_2)
                .build();
        var request = HttpRequest.newBuilder()
                .uri(URI.create("https://twitter.com/sleberknight"))
                .timeout(Duration.ofSeconds(5))
                .build();
        var responseBodyFuture = client.sendAsync(request, BodyHandlers.ofString())
                .thenApply(HttpResponse::body);

        var body = responseBodyFuture.get(5, TimeUnit.SECONDS);

        System.out.println(body);
    }
}
