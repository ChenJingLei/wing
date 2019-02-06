package com.chinatel.caur2cdsecurity.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        return "Hello World";
    }

    @RequestMapping(value = "/foo", method = RequestMethod.GET)
    public String foo() {
        return "foo";
    }

    @RequestMapping(value = "/bar", method = RequestMethod.GET)
    public String bar() {
        return "bar";
    }

    @RequestMapping(value = "/spam", method = RequestMethod.GET)
    public String spam() {
        return "spam";
    }
}
