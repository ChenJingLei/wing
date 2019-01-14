package com.chinatel.caur2cdtest.service;


import com.chinatel.caur2cdtest.storage.StorageProperties;
import com.chinatel.caur2cdtest.storage.StorageService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;

@Service
@PropertySource("classpath:storage.properties")
public class AudioDownlaodService {

    private static final Log log = LogFactory.getLog(AudioDownlaodService.class);

    @Autowired
    private StorageService storageService;

    @Autowired
    private StorageProperties storageProperties;

    @PostConstruct
    public void init() {
        log.info("init......");

        log.info(storageProperties.getLocation());
        storageService.init();

        storageService.loadAll();
    }

    public void download(String url, String filename) {
        InputStream inputStream = null;

        RestTemplate restTemplate = new RestTemplate();
        try {
            HttpHeaders headers = new HttpHeaders();
            ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<byte[]>(headers), byte[].class);
            byte[] result = response.getBody();
            inputStream = new ByteArrayInputStream(result);
            storageService.store(inputStream, filename);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("数据流关闭异常！");
            }
        }
    }


    @PreDestroy
    public void destory() {
        log.info("destory......");
    }

}
