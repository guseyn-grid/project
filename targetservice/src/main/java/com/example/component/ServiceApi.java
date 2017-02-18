package com.example.component;

import com.example.helper.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ServiceApi {

    private static final Logger logger = LoggerFactory.getLogger(ServiceApi.class);

    @Value("${process.executeTime}")
    private int executeTime;

    public ResponseEntity<Integer> m1(Integer x, Integer y) {

        makeHardWork();

        Integer result = MethodUtils.m1(x, y);

        logger.info("output from m1: {}", result);

        return ResponseEntity.ok(result);

    }

    public ResponseEntity<Integer> m2(Integer x, Integer y) {

        makeHardWork();

        Integer result = MethodUtils.m2(x, y);

        logger.info("output from m2: {}", result);

        return ResponseEntity.ok(result);

    }

    private void makeHardWork() {
        try {
            Thread.sleep(executeTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
