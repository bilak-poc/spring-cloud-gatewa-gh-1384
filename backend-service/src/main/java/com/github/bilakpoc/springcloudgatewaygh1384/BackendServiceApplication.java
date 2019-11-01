package com.github.bilakpoc.springcloudgatewaygh1384;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
            return currentDataWithZone();
        }

        @GetMapping("/slow/{delayInSeconds}")
        public String slowResponse(@PathVariable Integer delayInSeconds) throws InterruptedException {
            log.info("Calling slow with delay {}", delayInSeconds);
            final LocalDateTime start = LocalDateTime.now();
            TimeUnit.SECONDS.sleep(delayInSeconds);
            final LocalDateTime end = LocalDateTime.now();
            return "Start " + start + " | end: " + end + " | zone:" + zone;
        }

        @GetMapping("/test-retry/{status}")
        public ResponseEntity testRetry(@PathVariable Integer status) {
            log.info("Calling test retry with response status {}", status);
            return ResponseEntity.status(status).build();
        }

        @PostMapping("/test-retry-post")
        public String testPostWithBody(@RequestBody PostBodyRequest request) {
            log.info("Calling retry post with request {}", request);
            return currentDataWithZone();
        }

        @PutMapping("/test-retry-put")
        public String testPutWithBody(@RequestBody PutRequestBody request) {
            log.info("Calling retry put with request {}", request);
            return currentDataWithZone();
        }

        private String currentDataWithZone() {
            return LocalDateTime.now() + " zone: " + zone;
        }

    }


    @Data
    static class PostBodyRequest {

        private String field;
    }

    @Data
    static class PutRequestBody {

        private String field;
    }
}
