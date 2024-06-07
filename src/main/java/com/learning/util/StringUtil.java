package com.learning.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.exception.GeneralException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StringUtil {

    private final ObjectMapper mapper;

    public StringUtil(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public String toString(Object object) throws GeneralException {
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            log.error("Error toString() : {}", e.getMessage());
            throw new GeneralException("(System) General Error");
        }
    }
}
