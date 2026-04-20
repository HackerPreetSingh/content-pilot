package com.hempreet.service;

import com.hempreet.configuration.PropertiesLoader;
import com.hempreet.dto.AiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.google.genai.GoogleGenAiChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiService {

    private final ChatClient chatClient;
    private final PropertiesLoader loader;
    private final Environment env;

    public String callModel(String prompt) {

        List<String> models = loader.getModels();

        for (String model : models) {
            try {
                System.out.println("Trying to use Model: " + model);
                log.info("Trying to use Model: {}", model);

                if (Arrays.asList(env.getActiveProfiles()).contains("local")) {
                    return "Sample Response";
                }
                return makeApiCall(model, prompt);
            } catch (Exception e) {
                System.out.println("Model Limit Exhausted: " + model);
                log.warn("Model Limit Exhausted: {}", model);
            }
        }
        return "Both models exhausted";
    }


    public String makeApiCall(String model, String prompt) {
        GoogleGenAiChatOptions options = GoogleGenAiChatOptions.builder()
                .model(model)
                .build();

        return chatClient
                .prompt(prompt)
                .options(options)
                .call()
                .content();
    }
}
