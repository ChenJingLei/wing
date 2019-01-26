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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@PropertySource("classpath:storage.properties")
public class AudioDownlaodService {

    private static final Log log = LogFactory.getLog(AudioDownlaodService.class);

    private final StorageService storageService;

    private final StorageProperties storageProperties;

    @Autowired
    public AudioDownlaodService(StorageService storageService, StorageProperties storageProperties) {
        this.storageService = storageService;
        this.storageProperties = storageProperties;
    }

    @PostConstruct
    public void init() {
        log.info("download init......");

        log.info(storageProperties.getLocation());
        storageService.init();

        storageService.loadAll();
    }

    /**
     * @param url       the download address
     * @param chargeNbr the rootfolder
     * @return filename
     */
    public String download(String url, String chargeNbr) {
        InputStream inputStream = null;
        String filename = null;
        RestTemplate restTemplate = new RestTemplate();
        try {
            HttpHeaders headers = new HttpHeaders();
            ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<byte[]>(headers), byte[].class);
            byte[] result = response.getBody();
            inputStream = new ByteArrayInputStream(result);

            // gen filename by url
            Matcher m = Pattern.compile(chargeNbr + "/.*").matcher(url);

            if (m.find()) {
                log.info("Path found value: " + m.group(0));
                filename = m.group(0);
                storageService.store(inputStream, filename);
            } else {
                log.error("Regex is error");
                throw new Exception("Regex is error");
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                log.error("数据流关闭异常！" + e.getMessage());
            }
        }
        return filename;
    }


    @PreDestroy
    public void destory() {
        log.info("download destory......");
    }

}
