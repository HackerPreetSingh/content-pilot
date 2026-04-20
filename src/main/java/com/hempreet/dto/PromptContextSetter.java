package com.hempreet.dto;

import java.util.List;

public record PromptContextSetter(List<String> topics, List<String> formats, List<String> angles) {
}
