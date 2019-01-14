package com.chinatel.caur2cdtest.storage;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;


@PropertySource("classpath:storage.properties")
@Data
@ConfigurationProperties(ignoreUnknownFields = false, prefix = "storage")
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location;

}
