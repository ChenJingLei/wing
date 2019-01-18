package com.chinatel.caur2cdtest.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.chinatel.caur2cdtest.storage.StorageProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class OOSCtyunService {

    private static final Log log = LogFactory.getLog(OOSCtyunService.class);

    private final AmazonS3 oos;
    private final StorageProperties storageProperties;

    private String bucketName;

    @Autowired
    public OOSCtyunService(AmazonS3 oos, StorageProperties storageProperties) {
        this.oos = oos;
        this.storageProperties = storageProperties;
    }

    @PostConstruct
    public void init() {
        log.info("oos-ctyun init......");

        String location = storageProperties.getLocation();
        String[] ls = location.split("/");
        for (String l : ls) {
            if (!l.contains(":")) {
                Matcher matcher = Pattern.compile("[0-9a-z][0-9a-z\\-]+[0-9a-z]").matcher(l.toLowerCase());
                if (matcher.find()) {
                    String prefix = matcher.group(0);
                    log.info("bucket will be consist of " + prefix);
                    bucketName = prefix + "-" + UUID.randomUUID().toString() + "-" + new Date().getTime();
                    oos.createBucket(bucketName);
                    break;
                } else {
                    log.info("location is error: " + l);
                    bucketName = "temp" + "-" + UUID.randomUUID().toString() + "-" + new Date().getTime();
                    oos.createBucket(bucketName);
                    break;
                }
            }
        }
        log.info("Listing buckets");
        for (Bucket bucket : oos.listBuckets()) {
            log.info(" * " + bucket.getName());
        }
    }


    public boolean upload(Resource file, String filename) {
        try {
            oos.putObject(bucketName, filename, file.getFile());
            log.info("upload is successful");
            return true;
        } catch (Exception e) {
            log.warn("PUT is error " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    @PreDestroy
    public void destory() {
        log.info("oos-ctyun destory......");
    }

}

