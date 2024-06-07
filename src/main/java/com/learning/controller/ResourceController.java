package com.learning.controller;

import com.learning.exception.DatabaseException;
import com.learning.exception.GeneralException;
import com.learning.model.application.Response;
import com.learning.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1")
@Slf4j
public class ResourceController {

    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping(path = "/resource/create")
    public Object create() throws DatabaseException {
        Object result = resourceService.createSource();
        return Response.builder()
                .data(result)
                .responseCode("00")
                .build();
    }

    @GetMapping(path = "/fetch/{resource}")
    public Object fetchResource(@PathVariable String resource) throws DatabaseException, GeneralException {
        Object result = resourceService.fetchResource(resource);
        return Response.builder()
                .data(result)
                .responseCode("00")
                .build();
    }
}
