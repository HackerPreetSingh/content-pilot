package com.hempreet.controller;

import com.hempreet.dto.CurrentState;
import com.hempreet.service.AiService;
import com.hempreet.service.ContentService;
import com.hempreet.service.CounterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class ContentController {

    private final ContentService contentService;

    private final AiService aiService;

    private final CounterService counterService;


    @GetMapping("/test")
    public String testDeepSeek() throws Exception {

        return contentService.setPromptAndMakeApiCall();
    }

    @GetMapping("/getAutomatedContent")
    ResponseEntity<?> getAutomatedContent() throws Exception {
        return ResponseEntity.ok(contentService.setPromptAndMakeApiCall());
    }

    @PostMapping("/getCustomContent")
    ResponseEntity<?> getCustomContent(@RequestBody CurrentState currentState) throws Exception {
        return ResponseEntity.ok(contentService.makeApiCall(currentState.getTopicName(), currentState.getFormatName(), currentState.getAngleName(), "generic"));
    }

    @GetMapping("/getCurrentState")
    ResponseEntity<?> getCurrentState() throws Exception {
        return ResponseEntity.ok(counterService.getCurrentContentState());
    }

    @PostMapping("/setCurrentState")
    ResponseEntity<?> setCurrentState(@RequestBody CurrentState currentState) throws Exception {
        return ResponseEntity.ok(counterService.setCurrentContentState(currentState));
    }

    @GetMapping("/setRandomState")
    ResponseEntity<?> setRandomState() throws Exception {
        return ResponseEntity.ok(counterService.setRandomCurrentState());
    }

    @GetMapping("/changeTopic")
    ResponseEntity<?> changeTopic() throws Exception {
        return ResponseEntity.ok(counterService.changeTopic());
    }

    @GetMapping("/changeFormat")
    ResponseEntity<?> changeFormat() throws Exception {
        return ResponseEntity.ok(counterService.changeFormat());
    }

    @GetMapping("/changeAngle")
    ResponseEntity<?> changeAngle() throws Exception {
        return ResponseEntity.ok(counterService.changeAngle());
    }

}
