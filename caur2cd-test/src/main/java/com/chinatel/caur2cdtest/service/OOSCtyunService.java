package com.chinatel.caur2cdtest.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class OOSCtyunService {

    private static final Log log = LogFactory.getLog(OOSCtyunService.class);

    private final AmazonS3 oos;

    @Autowired
    public OOSCtyunService(AmazonS3 oos) {
        this.oos = oos;
    }

    @PostConstruct
    public void init() {
        log.info("oos-ctyun init......");
        log.info("Listing buckets");
        for (Bucket bucket : oos.listBuckets()) {
            log.info(" * " + bucket.getName());
        }
    }




    @PreDestroy
    public void destory() {
        log.info("oos-ctyun destory......");
    }

}

