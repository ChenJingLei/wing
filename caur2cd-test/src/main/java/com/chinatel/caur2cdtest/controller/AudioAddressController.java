package com.chinatel.caur2cdtest.controller;


import com.chinatel.caur2cdtest.model.AudioAddress;
import com.chinatel.caur2cdtest.repository.AudioAddressRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/audio-address")
public class AudioAddressController {

    private static final Log log = LogFactory.getLog(AudioAddressController.class);

    @Autowired
    private AudioAddressRepository audioAddressRepository;

    @RequestMapping(value = "/push", method = RequestMethod.POST)
    public String push(@RequestBody AudioAddress audioAddress) {

        try {

            log.info(audioAddress.toString());
            audioAddressRepository.save(audioAddress);

        } catch (Exception e) {
            return "fail";
        }

        return "success";
    }
}
