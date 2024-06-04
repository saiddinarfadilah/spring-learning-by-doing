package com.learning.route;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1")
public class GreetingRoute {

    @GetMapping
    public String hello() {
        return "Hello everyone, I am Learning Spring Boot";
    }
}
