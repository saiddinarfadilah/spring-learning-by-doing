package com.learning.client;

import com.learning.exception.APIException;
import com.learning.exception.GeneralException;
import com.learning.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Component
@Slf4j
public class RestClient {


    private final RestTemplate primary;
    private final StringUtil stringUtil;

    public RestClient(RestTemplate primary, StringUtil stringUtil) {
        this.primary = primary;
        this.stringUtil = stringUtil;
    }

    public Object callAPI(String destination, String url, String method, Object request) throws APIException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Object> entity = new HttpEntity<>(request, headers);

        try {
            log.info("<<<<<  REST CLIENT  >>>>>");
            log.info("{} request  : {} {} headers : {}, body : {}" , destination, HttpMethod.valueOf(method), url, entity.getHeaders(), stringUtil.toString(entity.getBody()));
            ResponseEntity<Object> exchange = primary.exchange(
                    url,
                    HttpMethod.valueOf(method),
                    entity,
                    Object.class);
            log.info("{} response : status code : {}, body : {}, process in 0 ms", destination, exchange.getStatusCode(), stringUtil.toString(exchange.getBody()));
            log.info("<<<<<<<<<<<<>>>>>>>>>>>>>");
            return exchange.getBody();
        } catch (RestClientException | GeneralException e) {
            log.error(e.getMessage());
            throw new APIException("Error callAPI() : " + e.getMessage());
        }
    }
}
