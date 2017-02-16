package com.example.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class ServiceRequests {

    private static final Logger logger = LoggerFactory.getLogger(ServiceRequests.class);

    public static Integer baseRequest(String path, Map<String, Integer> params) {

        BaseGetRequest request = new BaseGetRequest("127.0.0.1", 8081, path, params);
        String response = "";

        try {
            response = request.getResponse();
        } catch (IOException e) {
            logger.error("Error while getting response: {}", e.getMessage());
        }

        return Integer.valueOf(response);
    }

}
