package com.hempreet.service;

import com.hempreet.configuration.TelegramConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
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

    public void sendReplyKeyboard() {

        String url = "https://api.telegram.org/bot" + config.getToken() + "/sendMessage";

        Map<String, Object> body = new HashMap<>();
        body.put("chat_id", config.getChat_id());
        body.put("text", "ContentPilot Control Panel");

        Map<String, Object> replyMarkup = new HashMap<>();

        replyMarkup.put("keyboard", List.of(
                List.of("📝 Generate Tech", "🌍 Generate Generic"),
                List.of("🎲 Random State", "📋 Current State"),
                List.of("➡ Topic", "➡ Format", "➡ Angle")
        ));

        replyMarkup.put("resize_keyboard", true);
        replyMarkup.put("one_time_keyboard", false);
        replyMarkup.put("is_persistent", true);

        body.put("reply_markup", replyMarkup);

        restTemplate.postForObject(url, body, String.class);
    }
}
