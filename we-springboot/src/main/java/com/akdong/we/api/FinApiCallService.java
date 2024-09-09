package com.akdong.we.api;

import com.akdong.we.api.request.ApiMemberRegisterRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.*;

@Slf4j
@Service
public class FinApiCallService {
    @Value("${fin-api.url}")
    private String baseUrl;

    @Value("${fin-api.apiKey}")
    private String apiKey;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private RestTemplate restTemplate;

    @PostConstruct
    public void printLogBaseUrl() {
        log.info("----------FinAPI URL={}----------", baseUrl);
    }

    public String apiRegister(String email) {
        String url = baseUrl + "/member/";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ApiMemberRegisterRequest apiMemberRegisterRequest = new ApiMemberRegisterRequest(apiKey, email);
        HttpEntity<ApiMemberRegisterRequest> entity = new HttpEntity<>(apiMemberRegisterRequest, headers);

        try {
            log.debug("sent Register request to FinOpenApi server, url={}", url, entity);
            log.debug("Request Body: {}", objectMapper.writeValueAsString(apiMemberRegisterRequest));
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            log.debug("received Register response from FinOpenApi server, response={}", response);
            String responseBody = response.getBody();
            if (responseBody != null) {
                JsonNode jsonNode = objectMapper.readTree(responseBody);
                String userKey = jsonNode.get("userKey").asText();
                userKey = userKey.replaceAll("\n", "[Line Feed]");
                return userKey;
            } else {
                return null;
            }
        } catch (ResourceAccessException e) {
        log.error("ResourceAccessException: {}", e.getMessage(), e);
        return null;
    } catch (Exception e) {
        log.error("Exception occurred during FinAPI registration: {}", e.getMessage(), e);
        throw new RuntimeException(e);
    }
    }

}
