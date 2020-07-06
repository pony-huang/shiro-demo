package com.ponking.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Peng
 * @date 2020/6/29--13:07
 **/
@Component
@Data
@ConfigurationProperties(prefix = "audience")
public class GlobalPropertiesConfig {


    private String clientId;

    private String base64Secret;

    private String name;

    private int expiresSecond;
}
