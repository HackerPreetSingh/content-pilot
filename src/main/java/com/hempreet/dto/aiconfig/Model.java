package com.hempreet.dto.aiconfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Model {

    private String name;
    private String provider;
    private String modelId;
    private int priority;
    private boolean enabled;
    List<String> tags;
}
