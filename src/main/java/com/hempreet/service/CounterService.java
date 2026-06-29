package com.hempreet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hempreet.dto.CurrentState;
import com.hempreet.utils.AppConstants;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Slf4j
public class CounterService {

    private final ObjectMapper objectMapper;
    private static final File CURRENT_STATE_FILE = new File("/tmp/CurrentState.json");
    private final TelegramService telegramService;

    @PostConstruct
    public void setup() throws IOException {
        log.info("Setup Counter Service. File: {}", CURRENT_STATE_FILE.getCanonicalPath());
        if (!CURRENT_STATE_FILE.exists()) {
            log.info("Current state file does not exist. Creating new one.");
            objectMapper.writeValue(CURRENT_STATE_FILE, new CurrentState());
        }
    }

    public CurrentState getNextPromptState() throws IOException {
        CurrentState currentState = objectMapper.readValue(CURRENT_STATE_FILE, CurrentState.class);

        String topic = AppConstants.topics.get(currentState.getTopicIndex());
        String format = AppConstants.formats.get(currentState.getFormatIndex());
        String angle = AppConstants.angles.get(currentState.getAngleIndex());

        currentState.setTopicName(topic);
        currentState.setFormatName(format);
        currentState.setAngleName(angle);

        log.info("Current State of Counter: {}, topic: {}, format: {}, angles: {}", currentState, topic, format, angle);

        if (currentState.getTopicIndex() + 1 >= AppConstants.topics.size()) {
            currentState.setTopicIndex(0);
            currentState.setFormatIndex(currentState.getFormatIndex() + 1);
        }
        if (currentState.getFormatIndex() + 1 >= AppConstants.formats.size()) {
            currentState.setFormatIndex(0);
            currentState.setAngleIndex(currentState.getAngleIndex() + 1);
        }
        if (currentState.getAngleIndex() + 1 >= AppConstants.angles.size()) {
            currentState.setAngleIndex(0);
        }

        currentState.setTopicIndex(currentState.getTopicIndex() + 1);

        objectMapper.writeValue(CURRENT_STATE_FILE, currentState);
        return currentState;
    }

    public CurrentState getCurrentContentState() throws IOException {
        CurrentState currentState = objectMapper.readValue(CURRENT_STATE_FILE, CurrentState.class);
        telegramService.sendMessage("Getting Current State: \n" + currentState);
        return currentState;
    }

    public String setCurrentContentState(CurrentState currentState) throws IOException {
        currentState.setTopicName(AppConstants.topics.get(currentState.getTopicIndex()));
        currentState.setFormatName(AppConstants.formats.get(currentState.getFormatIndex()));
        currentState.setAngleName(AppConstants.angles.get(currentState.getAngleIndex()));
        objectMapper.writeValue(CURRENT_STATE_FILE, currentState);
        String message = "Current State set to: \n" + currentState;
        telegramService.sendMessage(message);
        return message;
    }

    public String setRandomCurrentState() throws IOException {
        CurrentState currentState = new CurrentState();

        currentState.setTopicIndex(ThreadLocalRandom.current().nextInt(AppConstants.topics.size()));
        currentState.setTopicName(AppConstants.topics.get(currentState.getTopicIndex()));

        currentState.setFormatIndex(ThreadLocalRandom.current().nextInt(AppConstants.formats.size()));
        currentState.setFormatName(AppConstants.formats.get(currentState.getFormatIndex()));

        currentState.setAngleIndex(ThreadLocalRandom.current().nextInt(AppConstants.angles.size()));
        currentState.setAngleName(AppConstants.angles.get(currentState.getAngleIndex()));

        objectMapper.writeValue(CURRENT_STATE_FILE, currentState);
        String message = "Random State set to: \n" + currentState;
        telegramService.sendMessage(message);
        return message;
    }

    public String changeTopic() throws IOException {
        CurrentState currentState = objectMapper.readValue(CURRENT_STATE_FILE, CurrentState.class);
        currentState.setTopicIndex((currentState.getTopicIndex() + 1) % AppConstants.topics.size());
        currentState.setTopicName(AppConstants.topics.get(currentState.getTopicIndex()));
        objectMapper.writeValue(CURRENT_STATE_FILE, currentState);
        String message = "Current State set to: \n" + currentState;
        telegramService.sendMessage(message);
        return message;
    }

    public String changeFormat() throws IOException {
        CurrentState currentState = objectMapper.readValue(CURRENT_STATE_FILE, CurrentState.class);
        currentState.setFormatIndex((currentState.getFormatIndex() + 1) % AppConstants.formats.size());
        currentState.setFormatName(AppConstants.formats.get(currentState.getFormatIndex()));
        objectMapper.writeValue(CURRENT_STATE_FILE, currentState);
        String message = "Format changed. Current State set to: \n" + currentState;
        telegramService.sendMessage(message);
        return message;
    }

    public String changeAngle() throws IOException {
        CurrentState currentState = objectMapper.readValue(CURRENT_STATE_FILE, CurrentState.class);
        currentState.setAngleIndex((currentState.getAngleIndex() + 1) % AppConstants.angles.size());
        currentState.setAngleName(AppConstants.angles.get(currentState.getAngleIndex()));
        objectMapper.writeValue(CURRENT_STATE_FILE, currentState);
        String message = "Angle changed. Current State set to: \n" + currentState;
        telegramService.sendMessage(message);
        return message;
    }
}
