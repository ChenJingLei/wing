package com.chinatel.caur2cdtest;

import com.chinatel.caur2cdtest.properties.OOSCtyunProperties;
import com.chinatel.caur2cdtest.storage.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({StorageProperties.class, OOSCtyunProperties.class})
public class Caur2cdTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(Caur2cdTestApplication.class, args);
    }

}

