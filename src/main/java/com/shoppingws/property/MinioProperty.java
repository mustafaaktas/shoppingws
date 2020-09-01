package com.shoppingws.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "minio")
@Getter
@Setter
public class MinioProperty {
    private String url;
    private String externalurl;
    private String accesskey;
    private String secretkey;
}
