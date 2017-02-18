package com.example.helper.asyncRestTemplate;

import com.example.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.AsyncRestTemplate;

import java.net.URISyntaxException;
import java.util.Map;

@Service
public class ServiceRequests {

    @Value("${target-service.host}")
    private String targetHost;

    @Value("${target-service.port}")
    private Integer targetPort;

    @Autowired
    AsyncRestTemplate asyncRestTemplate;

    public ListenableFuture<ResponseEntity<Integer>> baseRequest(String endPointPath, Map<String, Integer> params) throws URISyntaxException {
        return asyncRestTemplate.getForEntity(HttpUtils.buildUri(targetHost, targetPort, endPointPath, params), Integer.class);
    }

}
