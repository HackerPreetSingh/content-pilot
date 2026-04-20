package com.hempreet.contentpilot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.hempreet.*")
public class ContentPilotApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContentPilotApplication.class, args);
    }

}
