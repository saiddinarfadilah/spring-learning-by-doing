package com.learning.route;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingRoute {

    @GetMapping(path = "/")
    public String hello() {
        return "Hello everyone, I am Learning Spring Boot";
    }
}
