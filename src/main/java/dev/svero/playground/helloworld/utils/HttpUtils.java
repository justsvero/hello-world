package dev.svero.playground.helloworld.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class HttpUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);

    private final SSLContext sslContext;

    public HttpUtils() {
        this.sslContext = null;
    }

    public HttpUtils(SSLContext sslContext) {
        this.sslContext = sslContext;
    }

    public String postRequest(final String url, final String requestData) {
        boolean hasBody = StringUtils.isNotBlank(requestData);

        String result = null;

        try {
            HttpClient client;

            if (sslContext == null) {
                client = HttpClient.newBuilder().build();
            } else {
                client = HttpClient.newBuilder().sslContext(sslContext).build();
            }

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .headers("Content-Type", "application/x-www-form-urlencoded")
                    .POST(hasBody ? HttpRequest.BodyPublishers.ofString(requestData)
                            : HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                result = response.body();
            } else {
                LOGGER.error("HTTP request returned unexpected status: {}", response.statusCode());
                throw new RuntimeException("Unexpected status code received while processing POST request");
            }
        } catch (URISyntaxException | IOException | InterruptedException e) {
            LOGGER.error("Could not process the HTTP request successfully", e);
            throw new RuntimeException("Error while processing the request");
        }

        return result;
    }
}
