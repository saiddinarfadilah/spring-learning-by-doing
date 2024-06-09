package com.learning.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.client.RestClient;
import com.learning.entity.Resource;
import com.learning.exception.APIException;
import com.learning.exception.DatabaseException;
import com.learning.exception.GeneralException;
import com.learning.model.application.Response;
import com.learning.repository.impl.ResourceRepositoryImpl;
import com.learning.util.StringUtil;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ResourceServiceTest {

    @Mock
    ResourceRepositoryImpl mockResourceRepository;
    @Mock
    RestClient mockRestClient;
    @Mock
    StringUtil mockStringUtil;
    @InjectMocks
    ResourceService resourceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createResource() {
        ResponseEntity<Response> source = resourceService.createResource();
        // get body from ResponseEntity
        Response body = source.getBody();
        // assert response actual data and responseCode
        assertEquals("resource save successfully", body.getData());
        assertEquals("00", body.getResponseCode());
    }

    @Test
    void createResourceDatabaseException() throws DatabaseException {
        // mock DatabaseException
        doThrow(new DatabaseException("(System) Database Error")).when(mockResourceRepository).save(any(Resource.class));
        ResponseEntity<Response> resource = resourceService.createResource();
        // get body from ResponseEntity
        Response body = resource.getBody();
        // assert response actual data and responseCode
        assertNull(body.getData());
        assertEquals("03", body.getResponseCode());
    }

    @Test
    void fetchResourceAPI() throws APIException, GeneralException {
        // mock application properties
        String url = "http://localhost:8080/api/v1/resource";
        ReflectionTestUtils.setField(resourceService, "apiUrl", url);
        // mock dependency
        when(mockRestClient.callAPI(eq("something"), eq(url), eq("GET"), eq(null))).thenReturn("from api");
        when(mockStringUtil.toString(eq("from api"))).thenReturn("from api");

        String resourceName = "api";
        ResponseEntity<Response> resource = resourceService.fetchResource(resourceName);
        // get body from ResponseEntity
        Response body = resource.getBody();
        // assert response actual data and responseCode
        assertEquals("from api", body.getData());
        assertEquals("00", body.getResponseCode());
    }

    @Test
    void fetchResourceAPIException() throws APIException, GeneralException {
        // mock application properties
        String url = "http://localhost:8080/api/v1/resource";
        ReflectionTestUtils.setField(resourceService, "apiUrl", url);
        // mock dependency
        when(mockRestClient.callAPI(eq("something"), eq(url), eq("GET"), eq(null))).thenThrow(new APIException("Error callAPI() : time out"));
        when(mockStringUtil.toString(eq("from api"))).thenReturn("from api");

        String resourceName = "api";
        ResponseEntity<Response> resource = resourceService.fetchResource(resourceName);
        // get body from ResponseEntity
        Response body = resource.getBody();
        // assert response actual data and responseCode
        assertNull(body.getData());
        assertEquals("04", body.getResponseCode());
    }

    @Test
    void fetchResourceDatabase() throws DatabaseException, GeneralException, JsonProcessingException {
        // mock result from database
        Resource result = new Resource();
        result.setIdSource("1");
        result.setSourceName("database");

        // transform to string
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(result);

        // mock dependency
        when(mockResourceRepository.find()).thenReturn(result);
        when(mockStringUtil.toString(eq(result))).thenReturn(s);

        String resourceName = "db";
        ResponseEntity<Response> resource = resourceService.fetchResource(resourceName);
        // get body from ResponseEntity
        Response body = resource.getBody();
        // assert response actual data and responseCode
        assertEquals(result, body.getData());
        assertEquals("00", body.getResponseCode());
    }

    @Test
    void fetchResourceDatabaseException() throws DatabaseException, GeneralException, JsonProcessingException {
        // mock result from database
        Resource result = new Resource();
        result.setIdSource("1");
        result.setSourceName("database");

        // transform to string
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(result);

        // mock dependency
        when(mockResourceRepository.find()).thenThrow(new DatabaseException("(System) Database Error"));
        when(mockStringUtil.toString(eq(result))).thenReturn(s);

        String resourceName = "db";
        ResponseEntity<Response> resource = resourceService.fetchResource(resourceName);
        // get body from ResponseEntity
        Response body = resource.getBody();
        // assert response actual data and responseCode
        assertNull(body.getData());
        assertEquals("03", body.getResponseCode());
    }
}