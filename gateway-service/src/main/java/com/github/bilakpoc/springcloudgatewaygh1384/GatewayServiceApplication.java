package com.github.bilakpoc.springcloudgatewaygh1384;

import java.time.Duration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Service;
import reactor.netty.http.HttpResources;
import reactor.tools.agent.ReactorDebugAgent;

/**
 * @author Lukáš Vasek
 */
@SpringBootApplication
public class GatewayServiceApplication {

    public static void main(String[] args) {
        ReactorDebugAgent.init();
        SpringApplication.run(GatewayServiceApplication.class, args);
    }

    @Service
    public class GracefulShutdown implements ApplicationListener<ContextClosedEvent> {
        @Override
        public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
            HttpResources.disposeLoopsAndConnectionsLater().delaySubscription(Duration.ofSeconds(40)).block();
        }
    }
}
