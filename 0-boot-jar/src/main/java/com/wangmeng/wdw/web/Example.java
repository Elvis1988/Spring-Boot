package com.wangmeng.wdw.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@RequestMapping("wdw")
public class Example {

    @RequestMapping("/hello")
    String home() {
        return "Hello World!";
    }


}