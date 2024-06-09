package com.learning.service;

import com.learning.client.RestClient;
import com.learning.entity.Resource;
import com.learning.exception.APIException;
import com.learning.exception.DatabaseException;
import com.learning.exception.GeneralException;
import com.learning.model.application.Response;
import com.learning.repository.impl.ResourceRepositoryImpl;
import com.learning.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ResourceService {

    @Value("${url.api}")
    private String apiUrl;

    private final ResourceRepositoryImpl resourceRepository;
    private final RestClient restClient;
    private final StringUtil stringUtil;

    public ResourceService(ResourceRepositoryImpl resourceRepository, RestClient restClient, StringUtil stringUtil) {
        this.resourceRepository = resourceRepository;
        this.restClient = restClient;
        this.stringUtil = stringUtil;
    }

    public ResponseEntity<Response> createResource() {
        Resource resource = new Resource();
        resource.setSourceName("database");
        try {
            resourceRepository.save(resource);
            return ResponseEntity.ok(Response.builder()
                    .data("resource save successfully")
                    .responseCode("00")
                    .build());
        } catch (DatabaseException e) {
            log.error(e.getMessage());
            return ResponseEntity.ok(Response.builder()
                    .data(null)
                    .responseCode("03")
                    .build());
        }
    }

    public ResponseEntity<Response> fetchResource(String resourceName) {
        if (resourceName.equals("api")) {
            try {
                Object resource = restClient.callAPI("something", apiUrl, "GET", null);
                log.info("Outgoing Response : {}", stringUtil.toString(resource));
                return ResponseEntity.ok(Response.builder()
                        .data(resource)
                        .responseCode("00")
                        .build());
            } catch (APIException | GeneralException e) {
                log.error(e.getMessage());
                return switchException(e.getMessage());
            }
        } else {
            try {
                Resource resource = resourceRepository.find();
                log.info("Outgoing Response : {}", stringUtil.toString(resource));
                return ResponseEntity.ok(Response.builder()
                        .data(resource)
                        .responseCode("00")
                        .build());
            } catch (DatabaseException | GeneralException e) {
                log.error(e.getMessage());
                return switchException(e.getMessage());
            }
        }
    }

    private ResponseEntity<Response> switchException(String message) {
        if (message.contains("(System) Database Error")) {
            return ResponseEntity.ok(Response.builder()
                    .data(null)
                    .responseCode("03")
                    .build());
        }
        return ResponseEntity.ok(Response.builder()
                .data(null)
                .responseCode("04")
                .build());
    }
}