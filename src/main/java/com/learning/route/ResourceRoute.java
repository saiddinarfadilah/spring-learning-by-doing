package com.learning.route;

import com.learning.model.application.Response;
import com.learning.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/v1")
@Slf4j
public class ResourceRoute {

    private final ResourceService resourceService;

    public ResourceRoute (ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping(path = "/fetch/{resource}")
    public Object fetchResource(@PathVariable String resource) {
        Object result = resourceService.fetchResource(resource);
        return Response.builder()
                .data(result)
                .statusCode("00")
                .build();
    }
}
