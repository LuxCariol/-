package com.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CampusZeroWorkApplication {
    public static void main(String[] args) {
        SpringApplication.run(CampusZeroWorkApplication.class, args);
    }
}
