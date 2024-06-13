package com.learning.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.exception.GeneralException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Random;
import java.util.UUID;

@Component
@Slf4j
public class StringUtil {

    private static final Random RANDOM = new Random();
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

    public static UUID generateUuidV7() throws GeneralException {
        long timestamp = Instant.now().toEpochMilli();
        long mostSignificantBits = createMostSignificantBits(timestamp);
        long leastSignificantBits = createLeastSignificantBits();

        return new UUID(mostSignificantBits, leastSignificantBits);
    }

    private static long createMostSignificantBits(long timestamp) throws GeneralException {
        try {

        } catch (Exception e) {
            log.error("Error createMostSignificantBits() : {}", e.getMessage());
            throw new GeneralException("(System) General Error");
        }
        long msb = timestamp << 16;
        msb |= (0x7L << 12);
        msb |= (RANDOM.nextLong() & 0xFFF);
        return msb;
    }

    private static long createLeastSignificantBits() {
        long lsb = RANDOM.nextLong() & 0x3FFFFFFFFFFFFFFFL;
        lsb |= 0x8000000000000000L;
        return lsb;
    }

    public static long extractTimestamp(UUID uuid) throws GeneralException {
        try {
            return uuid.getMostSignificantBits() >>> 16;
        } catch (Exception e) {
            log.error("Error extractTimestamp() : {}", e.getMessage());
            throw new GeneralException("(System) General Error");
        }
    }

    public static LocalDateTime convertTimestamp(long timestamp) throws GeneralException {
        try {
            Instant instant = Instant.ofEpochMilli(timestamp);
            return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        } catch (Exception e) {
            log.error("Error convertTimestamp() : {}", e.getMessage());
            throw new GeneralException("(System) General Error");
        }
    }
}
