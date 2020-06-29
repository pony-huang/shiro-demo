package com.ponking.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Peng
 * @date 2020/6/29--13:07
 **/
@Component
@Data
public class GlobalPropertiesConfig {

    @Value("${audience.clientId}")
    private String clientId;

    @Value("${audience.base64Secret}")
    private String base64Secret;

    @Value("${audience.name}")
    private String name;

    @Value("${audience.expiresSecond}")
    private int expiresSecond;
}
