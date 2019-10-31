package com.github.bilakpoc.springcloudgatewaygh1384;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Lukáš Vasek
 */
@SpringBootApplication
@EnableDiscoveryClient
public class BackendServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendServiceApplication.class, args);
    }

    @RestController
    @Slf4j
    public static class SimpleController {

        @Value("${EUREKA_DEFAULT_ZONE}")
        private String zone;

        @GetMapping("/fast")
        public String fastResponse() {
            log.info("Calling fast");
            return LocalDateTime.now().toString() + " zone:" + zone;
        }

        @GetMapping("/slow/{delayInSeconds}")
        public String slowResponse(@PathVariable Integer delayInSeconds) throws InterruptedException {
            final LocalDateTime start = LocalDateTime.now();
            TimeUnit.SECONDS.sleep(delayInSeconds);
            final LocalDateTime end = LocalDateTime.now();
            return "Start " + start + " | end: " + end + " | zone:" + zone;
        }

    }
}
