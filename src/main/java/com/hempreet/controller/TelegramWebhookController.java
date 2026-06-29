package com.hempreet.controller;

import com.hempreet.dto.telegram.TelegramUpdate;
import com.hempreet.service.ContentService;
import com.hempreet.service.CounterService;
import com.hempreet.service.TelegramService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/telegram")
@RequiredArgsConstructor
public class TelegramWebhookController {

    private final ContentService contentService;
    private final CounterService counterService;
    private final TelegramService telegramService;

    @GetMapping("/showKeyboard")
    public ResponseEntity<?> showKeyboard() {
        telegramService.sendReplyKeyboard();
        return ResponseEntity.ok("Keyboard sent");
    }

    @PostMapping("/webhook")
    public void webhook(@RequestBody TelegramUpdate update) throws Exception {

        if (update == null
                || update.getMessage() == null
                || update.getMessage().getText() == null) {
            return;
        }

        String text = update.getMessage().getText();

        switch (text) {

            case "📝 Generate Tech" ->
                    contentService.setPromptAndMakeApiCall();

            case "🌍 Generate Generic" ->
                    contentService.makeApiCall(
                            "Java",
                            "LinkedIn Post",
                            "Beginner Friendly",
                            "generic");

            case "🎲 Random State" ->
                    counterService.setRandomCurrentState();

            case "📋 Current State" ->
                    counterService.getCurrentContentState();

            case "➡ Topic" ->
                    counterService.changeTopic();

            case "➡ Format" ->
                    counterService.changeFormat();

            case "➡ Angle" ->
                    counterService.changeAngle();

            default -> {
                // Ignore unknown commands
            }
        }
    }
}
