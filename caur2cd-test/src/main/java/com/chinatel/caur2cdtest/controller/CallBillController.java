package com.chinatel.caur2cdtest.controller;

import com.chinatel.caur2cdtest.model.CallBill;
import com.chinatel.caur2cdtest.repository.CallBillRepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("call-bill")
public class CallBillController {

    private static final Log log = LogFactory.getLog(CallBillController.class);

    @Autowired
    private CallBillRepository callBillRepository;

    @RequestMapping(value = "/push", method = RequestMethod.POST)
    public String push(@RequestBody CallBill callBill) {

        try {

            log.info(callBill.toString());
            callBillRepository.save(callBill);


        } catch (Exception e) {

            return "fail";
        }

        return "success";
    }

}
