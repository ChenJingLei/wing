package com.chinatel.caur2cdtest;

import com.chinatel.caur2cdtest.storage.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class Caur2cdTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(Caur2cdTestApplication.class, args);
    }

}

