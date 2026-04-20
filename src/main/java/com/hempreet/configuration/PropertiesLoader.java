package com.hempreet.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "app.ai")
@Setter
@Getter
public class PropertiesLoader {
    private List<String> models;
    private String prompt;
}
