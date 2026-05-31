package com.hempreet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hempreet.configuration.PropertiesLoader;
import com.hempreet.dto.Counter;
import com.hempreet.dto.aiconfig.Model;
import com.hempreet.dto.aiconfig.ModelInfo;
import com.hempreet.dto.aiconfig.ProviderConfig;
import com.hempreet.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContentService {

    private final AiService aiService;
    private final ObjectMapper objectMapper;
    private final PropertiesLoader propertiesLoader;
    private final TelegramService telegramService;
    private static final File COUNTER_FILE = new File("/tmp/counter.json");

    @PostConstruct
    public void setup() throws IOException {
        if (!COUNTER_FILE.exists()) {
            objectMapper.writeValue(COUNTER_FILE, new Counter());
        }
    }

    public String setPromptAndMakeApiCall() throws Exception {

        Counter indexes = objectMapper.readValue(COUNTER_FILE, Counter.class);

        String topic = AppConstants.topics.get(indexes.getTopicIndex());
        String format = AppConstants.formats.get(indexes.getFormatIndex());
        String angle = AppConstants.angles.get(indexes.getAngleIndex());

        log.info("Current State of Counter: {}, topic: {}, format: {}, angles: {}", indexes, topic, format, angle);

        if (indexes.getTopicIndex() + 1 >= AppConstants.topics.size()) {
            indexes.setTopicIndex(0);
            indexes.setFormatIndex(indexes.getFormatIndex() + 1);
        }
        if (indexes.getFormatIndex() + 1 >= AppConstants.formats.size()) {
            indexes.setFormatIndex(0);
            indexes.setAngleIndex(indexes.getAngleIndex() + 1);
        }
        if (indexes.getAngleIndex() + 1 >= AppConstants.angles.size()) {
            indexes.setAngleIndex(0);
        }

        indexes.setTopicIndex(indexes.getTopicIndex() + 1);

        objectMapper.writeValue(COUNTER_FILE, indexes);

        return makeApiCall(topic, format, angle, "tech");
    }

    public String makeApiCall(String topic, String format, String angle, String promptType) throws Exception {
        String formattedPrompt = propertiesLoader.getPrompt(promptType).formatted(topic, format, angle);

        ModelInfo modelInfo = propertiesLoader.loadAiConfig();

        Map<String, ProviderConfig> providerConfigMap = modelInfo.getProviders();

        String response = AppConstants.EMPTY_RESPONSE;
        String statusMessage = "";

        for (Model model : modelInfo.getModels()) {

            ProviderConfig providerConfig = providerConfigMap.get(model.getProvider());

            if (providerConfig == null) {
                statusMessage = "Provider config not found for %s".formatted(model.getProvider());
                log.warn(statusMessage);
                telegramService.sendMessage(statusMessage);
                continue;
            }

            statusMessage = "Trying Provider: %s, Model Name: %s".formatted(model.getProvider(), model.getName());
            telegramService.sendMessage(statusMessage);

            try {

                response = aiService.generate(providerConfig.getBaseUrl(),
                        model.getModelId(),
                        providerConfig.getResponseType(),
                        providerConfig.getApiKeyEnvVarName(),
                        formattedPrompt);
                if (!AppConstants.EMPTY_RESPONSE.equals(response)) {
                        telegramService.sendMessage("Response Generated Successfully:");
                        telegramService.sendMessage(response);
                    return response;
                }
            } catch (WebClientResponseException.TooManyRequests e) {

                statusMessage = "Rate limit hit for Provider: %s, Model: %s, Error Message: %s".
                        formatted(model.getProvider(), model.getName(), e.getMessage());
                log.warn(statusMessage);
                telegramService.sendMessage(statusMessage);

            } catch (Exception e) {
                statusMessage = "Model failed for Provider: %s, Model Name: %s, Error Message: %s"
                        .formatted(model.getProvider(), model.getName(), e.getMessage());
                log.warn(statusMessage);
                telegramService.sendMessage(statusMessage);
            }

        }
        return response;
    }
}
