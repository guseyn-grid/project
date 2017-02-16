package com.example.helper;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;


public class BaseGetRequest {

    private HttpClient client;
    private HttpGet request;
    private HttpResponse response;

    private final String HTTP = "http";
    private final String BAD_RESPONSE = "bad response with code %s";

    private static final Logger logger = LoggerFactory.getLogger(BaseGetRequest.class);

    public BaseGetRequest(String host, int port, String restPath, Map<String, Integer> params) {

        this.client = HttpClientBuilder.create().build();

        try {
            this.request = new HttpGet(buildURL(host, port, restPath, params));
        } catch (URISyntaxException e) {
            logger.error("URISyntaxException in BaseGetRequest constructor with error {}", e.getMessage());
        }

        this.request.addHeader("accept", "application/json");


    }

    public String getResponse() throws IOException {

        response = client.execute(request);
        int responseCode = response.getStatusLine().getStatusCode();

        if (responseCode != HttpStatus.SC_OK) {
            throw new RuntimeException(String.format(BAD_RESPONSE, responseCode));
        }

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();

        String line = "";

        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        return result.toString();

    }

    private URI buildURL(String host, int port, String restPath, Map<String, Integer> params) throws URISyntaxException {

        URIBuilder builder = new URIBuilder();
        builder.setScheme(HTTP)
                .setHost(host)
                .setPort(port)
                .setPath(restPath);

        for (Map.Entry<String, Integer> entry: params.entrySet()) {
            builder.addParameter(entry.getKey(), String.valueOf(entry.getValue()));
        }

        return builder.build();

    }

}
