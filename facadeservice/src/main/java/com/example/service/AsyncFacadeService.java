package com.example.service;

import com.google.common.util.concurrent.ListenableFuture;
import org.springframework.web.context.request.async.DeferredResult;

import java.net.URISyntaxException;

public interface AsyncFacadeService {

    ListenableFuture<Integer> getResult(Integer x, Integer y) throws URISyntaxException;

}
