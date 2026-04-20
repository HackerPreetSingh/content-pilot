package com.hempreet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hempreet.configuration.PropertiesLoader;
import com.hempreet.dto.Counter;
import com.hempreet.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContentService {

    private final AiService aiService;
    private final ObjectMapper objectMapper;
    private final PropertiesLoader loader;
    private final TelegramService telegramService;
    File f = new File("/tmp/counter.json");

    @PostConstruct
    public void setup() throws IOException {
        if (!f.exists()) {
            objectMapper.writeValue(f,new Counter());
        }
    }

    public String setPromptAndMakeApiCall() throws IOException {

        Counter indexes = objectMapper.readValue(f, Counter.class);

        String topic = AppConstants.topics.get(indexes.getTopicIndex());
        String format = AppConstants.formats.get(indexes.getFormatIndex());
        String angle = AppConstants.angles.get(indexes.getAngleIndex());

        log.info("Current State of Counter: {}, topic: {}, format: {}, angles: {}", indexes, topic, format, angle);

        if (indexes.getTopicIndex() + 1 >= AppConstants.topics.size()) {
            indexes.setTopicIndex(0);
            indexes.setFormatIndex(indexes.getFormatIndex()+1);
        }
        if (indexes.getFormatIndex() + 1 >= AppConstants.formats.size()) {
            indexes.setFormatIndex(0);
            indexes.setAngleIndex(indexes.getAngleIndex()+1);
        }
        if (indexes.getAngleIndex() + 1 >= AppConstants.angles.size()) {
            indexes.setAngleIndex(0);
        }

        indexes.setTopicIndex(indexes.getTopicIndex()+1);

        objectMapper.writeValue(f, indexes);

        return makeApiCall(topic, format, angle, false);
    }

    public String makeApiCall(String topic, String format, String angle, boolean isCustomCall) {
        String formatedPrompt = loader.getPrompt().formatted(topic, format, angle);
        String response = aiService.callModel(formatedPrompt);
        if (isCustomCall) {
            telegramService.sendMessage(response);
        }
        return response;
    }
}
