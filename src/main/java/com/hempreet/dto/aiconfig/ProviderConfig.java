package com.hempreet.dto.aiconfig;

import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class ProviderConfig {

    private String baseUrl;

    private String apiKeyEnvVarName;

    private String responseType;
}

