package com.example.utils;

import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class HttpUtils {

    private static final String HTTP = "http";

    public static URI buildUri(String host, int port, String restPath, Map<String, Integer> params) throws URISyntaxException {
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
