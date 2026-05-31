package com.hempreet.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hempreet.dto.aiconfig.ModelInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Configuration
@Setter
@Getter
@RequiredArgsConstructor
public class PropertiesLoader {

    private final ObjectMapper objectMapper;

    public ModelInfo loadAiConfig() throws IOException {
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
