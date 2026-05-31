package com.hempreet.dto.aiconfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelInfo {

    private Map<String, ProviderConfig> providers;

    private List<Model> models;
}
