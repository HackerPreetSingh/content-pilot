package com.hempreet.controller;

import com.hempreet.dto.PromptParams;
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

    private final ContentService service;

    @GetMapping("/getAutomatedContent")
    ResponseEntity<?> getAutomatedContent() throws IOException {
        return ResponseEntity.ok(service.setPromptAndMakeApiCall());
    }

    @PostMapping("/getCustomContent")
    ResponseEntity<?> getCustomContent(@RequestBody PromptParams params) throws IOException {
        return ResponseEntity.ok(service.makeApiCall(params.topic(), params.format(), params.angle(), true));
    }
}
