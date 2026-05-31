package com.hempreet.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hempreet.dto.aiconfig.ModelInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Configuration
@Setter
@Getter
@RequiredArgsConstructor
@Slf4j
public class PropertiesLoader {

    private final ObjectMapper objectMapper;
    private final Environment env;

    public ModelInfo loadAiConfig() throws IOException {

        try {
            String configJson = env.getProperty("MODEL_INFO_JSON");

            if (configJson != null && !configJson.isBlank()) {
                return objectMapper.readValue(configJson, ModelInfo.class);
            }
        } catch (Exception e) {
            log.warn("Failed to load AI config from env variable. Falling back to local file.", e);
        }

        return objectMapper.readValue(
                this.getClass().getClassLoader().getResourceAsStream("model-info.json"),
                ModelInfo.class);
    }

    public String getPrompt(String promptType) {
        try(InputStream inputStream  = getClass().getClassLoader().getResourceAsStream("prompts/" + promptType + "-prompt.txt")) {

            return new String(
                    inputStream.readAllBytes(),
                    StandardCharsets.UTF_8);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
