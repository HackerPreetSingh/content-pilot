package com.hempreet.dto.telegram;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TelegramMessage {

    @JsonProperty("message_id")
    private Long messageId;

    private TelegramChat chat;

    private String text;

}
