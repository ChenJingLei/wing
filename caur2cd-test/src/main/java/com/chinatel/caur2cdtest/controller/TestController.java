package com.chinatel.caur2cdtest.controller;

import com.chinatel.caur2cdtest.service.AudioDownlaodService;
import com.chinatel.caur2cdtest.storage.StorageProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    private final static Log log = LogFactory.getLog(TestController.class);

    @Autowired
    private AudioDownlaodService audioDownlaodService;

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download() {

        audioDownlaodService.download("http://www.170mv.com/kw/sb.sycdn.kuwo.cn/resource/n3/1/86/775567289.mp3", "aaa\\775567289.mp3");
    }
}
