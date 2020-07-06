package com.ponking.constant;

import com.ponking.config.GlobalPropertiesConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Peng
 * @date 2020/6/29--13:10
 **/
@Component
public class JwtAudienceConstant {


    public static String CLIENT_ID;
    public static String BASE64_SECRET;
    public static String NAME;
    public static int EXPIRES_SECOND;

    @Autowired
    public JwtAudienceConstant(GlobalPropertiesConfig globalPropertiesConfig) {
        CLIENT_ID = globalPropertiesConfig.getClientId();
        BASE64_SECRET = globalPropertiesConfig.getBase64Secret();
        NAME = globalPropertiesConfig.getName();
        EXPIRES_SECOND = globalPropertiesConfig.getExpiresSecond();
    }
}
