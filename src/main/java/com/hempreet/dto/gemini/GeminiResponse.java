package com.hempreet.dto.gemini;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GeminiResponse {

    private List<Candidate> candidates;
}
