package com.prophius.socialMedia.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prophius.socialMedia.exception.GenericException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException) {

        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        Map<String, Object> data = new HashMap<>();
        data.put("Status", false);
        data.put("Message", "You do not have permission to access this resource.");

        try {
            response.getOutputStream().print(objectMapper.writeValueAsString(data));
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }
}

