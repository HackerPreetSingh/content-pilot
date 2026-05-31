package com.hempreet.service;

import com.hempreet.dto.gemini.Content;
import com.hempreet.dto.gemini.GeminiRequest;
import com.hempreet.dto.gemini.GeminiResponse;
import com.hempreet.dto.gemini.Part;
import com.hempreet.dto.openai.Message;
import com.hempreet.dto.openai.OpenAiRequest;
import com.hempreet.dto.openai.OpenAiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ModelRequestService {

    private final WebClient webClient;
    private final Environment env;

    public String generateOpenAiResponse(String baseUrl, String modelName, String apiKeyEnvVarName, String prompt) {

        String apiKeyEnvVarValue = env.getProperty(apiKeyEnvVarName);

        OpenAiRequest request =
                new OpenAiRequest(
                        modelName,
                        List.of(
                                new Message(
                                        "user",
                                        prompt)));

        log.info("{} Request: {}", modelName, request);

        OpenAiResponse response =
                webClient
                        .post()
                        .uri(baseUrl + "/chat/completions")
                        .header("Authorization", "Bearer " + apiKeyEnvVarValue)
                        .header("Content-Type", "application/json")
                        .header("HTTP-Referer", "https://github.com/hempreet")
                        .header("X-Title", "Telegram Content Bot")
                        .bodyValue(request)
                        .retrieve()
                        .bodyToMono(OpenAiResponse.class)
                        .block();

        log.info("{} Response: {}", modelName, response);

        if (response == null ||
                response.getChoices() == null ||
                response.getChoices().isEmpty()) {

            return "Empty Response";
        }

        return response
                .getChoices()
                .getFirst()
                .getMessage()
                .getContent();
    }

    public String generateGeminiResponse(String baseUrl, String modelName, String apiKeyEnvVarName, String prompt) {
        String apiKeyEnvVarValue = env.getProperty(apiKeyEnvVarName);

        GeminiRequest request =
                new GeminiRequest(
                        List.of(
                                new Content(
                                        List.of(
                                                new Part(prompt)))));

        log.info("Gemini Request: {}", request);

        String url = baseUrl + "/v1beta/models/" + modelName + ":generateContent?key=" + apiKeyEnvVarValue;

        GeminiResponse response =
                webClient
                        .post()
                        .uri(url)
                        .header("Content-Type", "application/json")
                        .bodyValue(request)
                        .retrieve()
                        .bodyToMono(GeminiResponse.class)
                        .block();

        log.info("Gemini Response: {}", response);

        if (response == null ||
                response.getCandidates() == null ||
                response.getCandidates().isEmpty()) {

            return "Empty Response";
        }

        return response
                .getCandidates()
                .getFirst()
                .getContent()
                .getParts()
                .getFirst()
                .getText();

    }
}
