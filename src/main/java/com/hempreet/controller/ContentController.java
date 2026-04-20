package com.hempreet.controller;

import com.hempreet.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class ContentController {

    private final ContentService service;

    @GetMapping("/getContent")
    ResponseEntity<?> getContent() throws IOException {
        return ResponseEntity.ok(service.setPromptAndMakeApiCall());
    }
}
