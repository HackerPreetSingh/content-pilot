package com.hempreet.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@RequiredArgsConstructor
@ToString
public class Counter {
    private int topicIndex, formatIndex, angleIndex, postCount;
}
