package com.example.controller;

import com.example.service.AsyncFacadeService;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
public class APIInvoker {

    private static final Logger logger = LoggerFactory.getLogger(APIInvoker.class);

    @Autowired
    AsyncFacadeService asyncFacadeService;

    @Autowired
    ListeningExecutorService listeningExecutorService;

    @RequestMapping(value = {"/result", "/result"}, method = RequestMethod.GET)
    public @ResponseBody DeferredResult<Integer> getResult(
            @RequestParam(value = "x", required = true) Integer x,
            @RequestParam(value = "y", required = true) Integer y) throws Exception {

        logger.info("input params for getResult: x = {}, y = {}", x, y);

        DeferredResult<Integer> deferredResult = new DeferredResult<>();

        ListenableFuture<Integer> future = asyncFacadeService.getResult(x, y);

        Futures.addCallback(future, new FutureCallback<Integer>() {
            @Override
            public void onSuccess(Integer result) {
                logger.info("total output in callback: {}", result);
                deferredResult.setResult(result);
            }

            @Override
            public void onFailure(Throwable t) {
                deferredResult.setErrorResult(t);
            }
        });

        logger.info("non blocking message?");

        return deferredResult;

    }


}
