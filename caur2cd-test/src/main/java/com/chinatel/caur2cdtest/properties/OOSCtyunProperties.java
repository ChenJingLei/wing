package com.chinatel.caur2cdtest.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:oos-ctyun-credentials.properties")
@Data
@ConfigurationProperties(ignoreUnknownFields = false, prefix = "oos-ctyun")
public class OOSCtyunProperties {

    private String accessKeyID;

    private String accessSecretKey;

    private String endPoint;

}
