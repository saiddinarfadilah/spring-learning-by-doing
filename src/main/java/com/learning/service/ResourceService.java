package com.learning.service;

import com.learning.entity.Resource;
import com.learning.exception.DatabaseException;
import com.learning.exception.GeneralException;
import com.learning.repository.impl.ResourceRepositoryImpl;
import com.learning.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Slf4j
public class ResourceService {

    private final ResourceRepositoryImpl resourceRepository;
    private final StringUtil stringUtil;

    public ResourceService(ResourceRepositoryImpl resourceRepository, StringUtil stringUtil) {
        this.resourceRepository = resourceRepository;
        this.stringUtil = stringUtil;
    }

    public String createSource() throws DatabaseException {
        Resource resource = new Resource();
        resource.setSourceName("database");
        resourceRepository.save(resource);
        return "source save successfuly";
    }

    public Object fetchResource(String resourceName) throws DatabaseException, GeneralException {
        Map<String, Object> resource = new LinkedHashMap<>();
        if (resourceName.equals("api")) {
            resource.put("resource", "api");
            log.info("Outgoing Response : {}", resource);
            return resource;
        } else {
            Resource result = resourceRepository.find();
            log.info("Outgoing Response : {}", stringUtil.toString(result));
            return result;
        }
    }
}