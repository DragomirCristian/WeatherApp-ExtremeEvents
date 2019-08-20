package com.dragomircristian.extremeevents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ExtremeEventsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExtremeEventsApplication.class, args);
    }

}
