package com.learning.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Slf4j
public class ResourceService {

    public Object fetchResource(String resourceName) {
        Map<String, Object> resource = new LinkedHashMap<>();
        if (resourceName.equals("api")) {
            resource.put("resource", "api");
            log.info("outgoing response : {}", resource);
            return resource;
        } else {
            resource.put("resource", "database");
            log.info("outgoing response : {}", resource);
            return resource;
        }
    }
}
