package com.hempreet.controller;

import com.hempreet.dto.PromptParams;
import com.hempreet.service.AiService;
import com.hempreet.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ContentController {

    private final ContentService contentService;

    private final AiService aiService;

    @GetMapping("/getAutomatedContent")
    ResponseEntity<?> getAutomatedContent() throws Exception {
        return ResponseEntity.ok(contentService.setPromptAndMakeApiCall());
    }

    @PostMapping("/getCustomContent")
    ResponseEntity<?> getCustomContent(@RequestBody PromptParams params) throws Exception {
        return ResponseEntity.ok(contentService.makeApiCall(params.topic(), params.format(), params.angle(), "generic"));
    }

    @GetMapping("/test")
    public String testDeepSeek() throws Exception {

        return contentService.setPromptAndMakeApiCall();
    }
}
