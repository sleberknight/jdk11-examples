package com.example.jdk11;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit5.DropwizardClientExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

@DisplayName("JDK 11 HttpClient")
@ExtendWith({DropwizardExtensionsSupport.class, SoftAssertionsExtension.class})
class NewHttpClientTest {

    private static final ObjectMapper MAPPER = Jackson.newMinimalObjectMapper();

    @Path("/")
    public static class PingResource {
        @GET
        @Path("/ping")
        @Produces(MediaType.APPLICATION_JSON)
        public Response ping(@QueryParam("name") @DefaultValue("random internet user") String name) {
            var entity = Map.of(
                    "name", name,
                    "message", "pong!"
            );
            return Response.ok(entity).build();
        }
    }

    private static final DropwizardClientExtension CLIENT_EXTENSION =
            new DropwizardClientExtension(new PingResource());

    private String baseUri;

    @BeforeEach
    void setUp() {
        baseUri = CLIENT_EXTENSION.baseUri().toString();
    }

    @Test
    void shouldAllowSynchronousGETRequests(SoftAssertions softly)
            throws IOException, InterruptedException {

        var httpRequest = buildGETRequest();
        var httpClient = HttpClient.newHttpClient();
        var response = httpClient.send(httpRequest, BodyHandlers.ofString());

        softly.assertThat(response.statusCode()).isEqualTo(200);

        softly.assertThat(response.headers().allValues("Content-Type"))
                .containsExactly("application/json");

        var body = response.body();
        readAndAssertPingResponse(body);
    }

    @Test
    void shouldAllowAsynchronousGETRequests()
            throws InterruptedException, ExecutionException, TimeoutException, JsonProcessingException {

        var httpRequest = buildGETRequest();
        var httpClient = HttpClient.newHttpClient();
        var completableFuture = httpClient
                .sendAsync(httpRequest, BodyHandlers.ofString())
                .thenApply(HttpResponse::body);

        var body = completableFuture.get(5, TimeUnit.SECONDS);
        readAndAssertPingResponse(body);
    }

    private HttpRequest buildGETRequest() {
        var pingUri = URI.create(baseUri + "/ping");

        return HttpRequest.newBuilder(pingUri)
                .GET() // optional, is the default
                .header("X-Ping-Custom", "someValue")
                .timeout(Duration.ofSeconds(2))
                .build();
    }

    private void readAndAssertPingResponse(String body) throws JsonProcessingException {
        var responseEntity = MAPPER.readValue(body, new TypeReference<Map<String, Object>>() {
        });
        assertThat(responseEntity).containsOnly(
                entry("name", "random internet user"),
                entry("message", "pong!")
        );
    }
}
