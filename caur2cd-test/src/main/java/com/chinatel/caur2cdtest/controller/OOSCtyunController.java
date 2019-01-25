package com.chinatel.caur2cdtest.controller;


import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.chinatel.caur2cdtest.service.OOSCtyunService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/oos-ctyun")
public class OOSCtyunController {

    private static final Log log = LogFactory.getLog(OOSCtyunController.class);

    private final OOSCtyunService oosCtyunService;

    @Autowired
    public OOSCtyunController(OOSCtyunService oosCtyunService) {
        this.oosCtyunService = oosCtyunService;
    }

    @RequestMapping(value = "/listobjects", method = RequestMethod.POST)
    public ObjectListing listObjects(@RequestBody ListObjectsRequest listObjectsRequest, HttpServletResponse response) {
        try {
            ObjectListing objectListing = oosCtyunService.listObjects(listObjectsRequest);
            log.info("listObjects Success");
            return objectListing;
        } catch (AmazonServiceException ase) {
            int statusCode = ase.getStatusCode();
            if (statusCode != HttpServletResponse.SC_OK) {
                response.setStatus(ase.getStatusCode());
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            response.setHeader("Error Message", ase.toString());
            log.error("Error Message" + ase.toString());
            ase.printStackTrace();
            return null;
        } catch (AmazonClientException ace) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setHeader("Error Message", ace.toString());
            log.error("Error Message" + ace.toString());
            return null;
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setHeader("Error Message", e.toString());
            log.error("Error Message" + e.toString());
            e.printStackTrace();
            return null;
        }
    }
}
