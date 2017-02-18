package com.example.controller;

import com.example.component.ServiceApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
public class MainApi {

    private static final Logger logger = LoggerFactory.getLogger(MainApi.class);

    @Autowired
    private ServiceApi serviceApi;

    @RequestMapping(value = {"/m1", "/m1/"}, method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<Integer> m1(
            @RequestParam(value = "x", required = true) Integer x,
            @RequestParam(value = "y", required = true) Integer y) {

        logger.info("input params for m1: x = {}, y = {}", x, y);

        return serviceApi.m1(x, y);

    }

    @RequestMapping(value = {"/m2", "/m2/"}, method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<Integer> m2(
            @RequestParam(value = "x", required = true) Integer x,
            @RequestParam(value = "y", required = true) Integer y) {

        logger.info("input params for m2: x = {}, y = {}", x, y);

        return serviceApi.m2(x, y);

    }

}
