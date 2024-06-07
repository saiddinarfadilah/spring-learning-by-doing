package com.learning.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @GetMapping(path = "/")
    public String hello() {
        return "Hello everyone, I am Learning Spring Boot";
    }
}
