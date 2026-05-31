package com.hempreet.dto.openai;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class OpenAiResponse {

    private List<Choice> choices;
}

