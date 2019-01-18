package com.chinatel.caur2cdtest.controller;


import com.chinatel.caur2cdtest.model.AudioAddress;
import com.chinatel.caur2cdtest.repository.AudioAddressRepository;
import com.chinatel.caur2cdtest.service.AudioDownlaodService;
import com.chinatel.caur2cdtest.service.OOSCtyunService;
import com.chinatel.caur2cdtest.storage.StorageService;
import io.micrometer.core.instrument.util.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/audio-address")
public class AudioAddressController {

    private static final Log log = LogFactory.getLog(AudioAddressController.class);

    @Autowired
    private AudioAddressRepository audioAddressRepository;

    @Autowired
    private AudioDownlaodService audioDownlaodService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private OOSCtyunService oosCtyunService;

    @RequestMapping(value = "/getall", method = RequestMethod.GET)
    public List<AudioAddress> getAll() {
        return audioAddressRepository.findAll();
    }


    @RequestMapping(value = "/push", method = RequestMethod.POST)
    public String push(@RequestBody AudioAddress audioAddress) {

        try {

            if (StringUtils.isNotBlank(audioAddress.getVoiceAddress()) &&
                    StringUtils.isNotBlank(audioAddress.getChargeNbr())) {

                String filename = audioDownlaodService.download(
                        audioAddress.getVoiceAddress(),
                        audioAddress.getChargeNbr());
                //Test
//                String filename = audioDownlaodService.download(
//                        "http://www.170mv.com/kw/sb.sycdn.kuwo.cn/resource/n3/1/86/775567289.mp3",
//                        "resource");
                if (StringUtils.isNotBlank(filename)) {
                    audioAddress.setFilename(filename);
                    log.info(audioAddress.toString());
                    audioAddressRepository.save(audioAddress);

                    //upload file to storage

                    // if

                    boolean isSuccess = oosCtyunService.upload(storageService.loadAsResource(filename), filename);
                    if (!isSuccess) {
                        throw new Exception("upload file to OOS is error");
                    }


                } else {
//                    log.error("download file is error");
                    throw new Exception("download file is error");
                }

            } else {
//                log.error("voiceAddress is empty");
                throw new Exception("voiceAddress is empty");
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            return "fail";
        }

        return "success";
    }

    @GetMapping(value = "/download/files/**")
    public ResponseEntity<Resource> download(HttpServletRequest request) {
        String filename = extractPathFromPattern(request);
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    private static String extractPathFromPattern(
            final HttpServletRequest request) {
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        return new AntPathMatcher().extractPathWithinPattern(bestMatchPattern, path);
    }

}
