package com.learning.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class BeanConfig {

    @Value("${connect.timeout.primary}")
    private Integer connectTimeoutPrimary;
    @Value("${read.timeout.primary}")
    private Integer readTimeoutPrimary;
    @Value("${connect.timeout.secondary}")
    private Integer connectTimeoutSecondary;
    @Value("${read.timeout.secondary}")
    private Integer readTimeoutSecondary;

    @Bean
    public RestTemplate primary(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofSeconds(connectTimeoutPrimary))
                .setReadTimeout(Duration.ofSeconds(readTimeoutPrimary))
                .build();
    }

    @Bean
    public RestTemplate secondary(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofSeconds(connectTimeoutSecondary))
                .setReadTimeout(Duration.ofSeconds(readTimeoutSecondary))
                .build();
    }
}
