package com.example.controller;

import com.example.helper.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
public class MainApi {

    private static final Logger logger = LoggerFactory.getLogger(MainApi.class);

    @Value("${process.executeTime}")
    private int executeTime;

    @RequestMapping(value = {"/m1", "/m1/"}, method = RequestMethod.GET)
    public @ResponseBody
    DeferredResult<Integer> m1(
            @RequestParam(value = "x", required = true) Integer x,
            @RequestParam(value = "y", required = true) Integer y) {

        logger.info("input params for m1: x = {}, y = {}", x, y);

        final DeferredResult<Integer> result = new DeferredResult<>();

        new Thread(() -> {

            logger.info("Process in m1 started");

            try {
                Thread.sleep(executeTime);
            } catch (InterruptedException e) {
                logger.error("InterruptedException in the process m1 with error {}", e.getMessage());
            }

            logger.info("Process in m1 finished");

            result.setResult(MethodUtils.m1(x, y));

            logger.info("output from m1: {}", result.getResult());

        }).start();

        return result;

    }

    @RequestMapping(value = {"/m2", "/m2/"}, method = RequestMethod.GET)
    public @ResponseBody
    DeferredResult<Integer> m2(
            @RequestParam(value = "x", required = true) Integer x,
            @RequestParam(value = "y", required = true) Integer y) {

        logger.info("input params for m2: x = {}, y = {}", x, y);

        final DeferredResult<Integer> result = new DeferredResult<>();

        new Thread(() -> {

            logger.info("Process in m2 started");

            try {
                Thread.sleep(executeTime);
            } catch (InterruptedException e) {
                logger.error("InterruptedException in the process m2 with error {}", e.getMessage());
            }

            logger.info("Process in m2 finished");

            result.setResult(MethodUtils.m2(x, y));

            logger.info("output from m2: {}", result.getResult());

        }).start();

        return result;

    }

    @RequestMapping(value = {"/m3", "/m3/"}, method = RequestMethod.GET)
    public @ResponseBody
    DeferredResult<Integer> m3(
            @RequestParam(value = "m1", required = true) Integer m1,
            @RequestParam(value = "m2", required = true) Integer m2) {

        logger.info("input params for m3: m1 = {}, m2 = {}", m1, m2);

        final DeferredResult<Integer> result = new DeferredResult<>();

        new Thread(() -> {

            logger.info("Process in m3 started");

            try {
                Thread.sleep(executeTime);
            } catch (InterruptedException e) {
                logger.error("InterruptedException in the process m3 with error {}", e.getMessage());
            }

            logger.info("Process in m3 finished");

            result.setResult(MethodUtils.m3(m1, m2));

            logger.info("output from m3: {}", result.getResult());

        }).start();

        return result;

    }

}
