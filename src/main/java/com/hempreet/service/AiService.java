package com.hempreet.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiService {

    private final Environment env;
    private final ModelRequestService modelRequestService;

    public String generate(String baseUrl, String modelName, String responseType, String apiKeyEnvVarName, String prompt) {

            return switch(responseType) {

                case "OPENAI" -> modelRequestService.generateOpenAiResponse(baseUrl, modelName, apiKeyEnvVarName, prompt);

                case "GEMINI" -> modelRequestService.generateGeminiResponse(baseUrl, modelName, apiKeyEnvVarName, prompt);

                default -> throw new RuntimeException("Incompatible Model Type");
            };
        }
}
