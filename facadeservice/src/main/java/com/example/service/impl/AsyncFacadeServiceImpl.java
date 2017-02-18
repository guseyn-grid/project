package com.example.service.impl;

import com.example.helper.asyncRestTemplate.ServiceRequests;
import com.example.utils.ListenableFutureTransformationUtils;
import com.example.service.AsyncFacadeService;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AsyncFacadeServiceImpl implements AsyncFacadeService {

    private static final Logger logger = LoggerFactory.getLogger(AsyncFacadeServiceImpl.class);

    @Value("${target-service.api.m1}")
    private String m1Path;

    @Value("${target-service.api.m2}")
    private String m2Path;

    @Autowired
    private ListeningExecutorService listeningExecutorService;

    @Autowired
    ServiceRequests serviceRequests;

    @Override
    public ListenableFuture<Integer> getResult(Integer x, Integer y) throws URISyntaxException {

        Map<String, Integer> params = new HashMap<String, Integer>(){{
            put("x", x);
            put("y", y);
        }};

        List<ListenableFuture<ResponseEntity<Integer>>> futures = new ArrayList<>();

        futures.add(ListenableFutureTransformationUtils
                .toGuava(serviceRequests.baseRequest(m1Path, params), listeningExecutorService));
        futures.add(ListenableFutureTransformationUtils
                .toGuava(serviceRequests.baseRequest(m2Path, params), listeningExecutorService));

        ListenableFuture<List<ResponseEntity<Integer>>> successfulList = Futures.allAsList(futures);

        AsyncFunction<List<ResponseEntity<Integer>>, Integer> queryFunction = this::getResultAsync;

        return Futures.transformAsync(successfulList, queryFunction);

    }

    private ListenableFuture<Integer> getResultAsync(List<ResponseEntity<Integer>> entities) {
        final List<Integer> results = entities.stream().map(HttpEntity::getBody).collect(Collectors.toList());
        return listeningExecutorService.submit(() -> calculateResult(results));
    }

    private Integer calculateResult(List<Integer> params) {

        Integer m1 = params.get(0);
        Integer m2 = params.get(1);

        logger.info("output from m1: {}", m1);
        logger.info("output from m2: {}", m2);

        Integer result = 2 * params.get(0) + 2 * params.get(1);

        logger.info("total result is {}", result);

        return result;
    }

}
