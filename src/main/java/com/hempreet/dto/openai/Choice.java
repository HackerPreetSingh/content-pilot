package com.hempreet.dto.openai;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class Choice {

    private Message message;
}

