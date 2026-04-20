package com.hempreet.service;

import com.hempreet.configuration.TelegramConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramService {
    private final TelegramConfig config;

    private final RestTemplate restTemplate;

    public void sendMessage(String message) {
        String url = "https://api.telegram.org/bot" + config.getToken() + "/sendMessage";

        Map<String, String> body = new HashMap<>();
        body.put("chat_id", config.getChat_id());
        body.put("text", message);

        restTemplate.postForObject(url, body, String.class);
    }
}
