package com.hempreet.service;

import com.hempreet.configuration.PropertiesLoader;
import com.hempreet.dto.CurrentState;
import com.hempreet.dto.aiconfig.Model;
import com.hempreet.dto.aiconfig.ModelInfo;
import com.hempreet.dto.aiconfig.ProviderConfig;
import com.hempreet.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContentService {

    private final AiService aiService;
    private final PropertiesLoader propertiesLoader;
    private final TelegramService telegramService;
    private final CounterService counterService;

    public String setPromptAndMakeApiCall() throws Exception {

        CurrentState currentState = counterService.getNextPromptState();

        return makeApiCall(currentState.getTopicName(), currentState.getFormatName(), currentState.getAngleName(), "tech");
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
