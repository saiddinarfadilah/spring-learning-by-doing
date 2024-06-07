package com.learning.config;

import com.learning.model.application.Response;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorConfig implements ErrorController {

    @RequestMapping(value = "/error")
    public Object error(HttpServletRequest request) {
        Object statusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (statusCode != null) {
            int code = Integer.parseInt(statusCode.toString());
            if (code == HttpStatus.NOT_FOUND.value()) {
                return new ResponseEntity<>(
                        Response.builder()
                                .data(null)
                                .statusCode("404").build(),
                        HttpStatus.NOT_FOUND);
            } else if (code == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return new ResponseEntity<>(
                        Response.builder()
                                .data(null)
                                .statusCode("500").build(),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<>(
                Response.builder()
                        .data(null)
                        .statusCode("500").build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
