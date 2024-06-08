package com.learning.repository.impl;

import com.learning.entity.Resource;
import com.learning.exception.DatabaseException;
import com.learning.repository.ResourceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ResourceRepositoryImpl {

    private final ResourceRepository resourceRepository;

    public ResourceRepositoryImpl(@Lazy ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public void save(Resource resource) throws DatabaseException {
        try {
            resourceRepository.save(resource);
        } catch (Exception e) {
            log.error("Error save() : {}", e.getMessage());
            throw new DatabaseException("(System) Database Error");
        }
    }

    public Resource find() throws DatabaseException {
        try {
            return resourceRepository.find().orElse(null);
        } catch (Exception e) {
            log.error("Error find() : {}", e.getMessage());
            throw new DatabaseException("(System) Database Error");
        }
    }
}
