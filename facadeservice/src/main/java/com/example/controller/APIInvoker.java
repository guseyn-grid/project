package com.example.controller;

import com.example.helper.ServiceRequests;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

@RestController
public class APIInvoker {

    private static final Logger logger = LoggerFactory.getLogger(APIInvoker.class);
    private static final int THREAD_POOL = 2;

    @RequestMapping(value = {"/result", "/result/"}, method = RequestMethod.GET)
    public @ResponseBody
    Integer getResult(
            @RequestParam(value = "x", required = true) Integer x,
            @RequestParam(value = "y", required = true) Integer y) {

        logger.info("input params for getResult: x = {}, y = {}", x, y);

        final String[] PATHS_M1_M2 = new String[]{"/m1", "/m2"};
        final String PATH_M3 = "/m3";

        Map<String, Integer> paramsForM1AndM2 = new HashMap<String, Integer>(){{
            put("x", x);
            put("y", y);
        }};

        Map<String, String> paramsForM3;

        ListeningExecutorService pool = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(THREAD_POOL));

        for (final String path : PATHS_M1_M2) {

            final ListenableFuture<Integer> future = pool.submit(() -> ServiceRequests.baseRequest(path, paramsForM1AndM2));



        }

        return 0;

    }

}
