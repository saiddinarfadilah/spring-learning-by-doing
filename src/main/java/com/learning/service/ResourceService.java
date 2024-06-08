package com.learning.service;

import com.learning.client.RestClient;
import com.learning.entity.Resource;
import com.learning.exception.APIException;
import com.learning.exception.DatabaseException;
import com.learning.exception.GeneralException;
import com.learning.repository.impl.ResourceRepositoryImpl;
import com.learning.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

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

    public String createSource() {
        Resource resource = new Resource();
        resource.setSourceName("database");

        try {
            resourceRepository.save(resource);
            return "source save successfully";
        } catch (DatabaseException e) {
            log.error(e.getMessage());
        }

        return null;
    }

    public Object fetchResource(String resourceName) {
        if (resourceName.equals("api")) {
            try {
                Object resource = restClient.callAPI("something", apiUrl, "GET", null);
                log.info("Outgoing Response : {}", stringUtil.toString(resource));
                return resource;
            } catch (APIException | GeneralException e) {
                log.error(e.getMessage());
            }
        } else {
            try {
                Resource resource = resourceRepository.find();
                log.info("Outgoing Response : {}", stringUtil.toString(resource));
                return resource;
            } catch (DatabaseException | GeneralException e) {
                log.error(e.getMessage());
            }
        }
        return null;
    }
}