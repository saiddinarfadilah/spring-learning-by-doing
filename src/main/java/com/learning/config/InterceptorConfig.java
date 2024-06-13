package com.learning.config;

import com.learning.exception.GeneralException;
import com.learning.util.StringUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.UUID;

@Component
@Slf4j
public class InterceptorConfig implements HandlerInterceptor {

    @Value("${spring.application.name}")
    private String appName;

    private Long startTime;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws GeneralException {
        if (request.getRequestURI().equalsIgnoreCase("/error")) {
            return true;
        }

        String correlationId = request.getHeader("correlation-id");
        if (correlationId == null) {
            correlationId = String.valueOf(StringUtil.generateUuidV7());
        }

        MDC.put("app", appName);
        MDC.put("correlationId", correlationId);
        MDC.put("ip", request.getRemoteAddr());

        log.info("<<<<< START PROCESS >>>>>");
        log.info("{} {}://{}:{}{}", request.getMethod(), request.getScheme(),
                request.getServerName(), request.getServerPort(), request.getRequestURI());
        response.setHeader("X-Correlation-ID", correlationId);
        startTime = System.nanoTime();

        return true;
    }

    @Override
    public void postHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler,
                           ModelAndView modelAndView) {
        Long endTime = System.nanoTime();
        long elapsedTimeInMillis = (endTime - startTime) / 1_000_000;
        String elapsedTime = (elapsedTimeInMillis > 1000) ? (elapsedTimeInMillis / 1000) + " seconds" : elapsedTimeInMillis + " ms";

        log.info("<<<<<  END PROCESS  >>>>> {}", elapsedTime);
        MDC.clear();
    }
}
