package com.akdong.we.api;

import com.akdong.we.api.request.ApiMemberRegisterRequest;
import com.akdong.we.api.request.CommonRequestHeader;
import com.akdong.we.api.request.CreateAccountRequest;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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

    public String makeAccount(String userKey) {

        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");

        String formattedDate = currentDate.format(dateFormatter);
        String formattedTime = currentTime.format(timeFormatter);


        CommonRequestHeader Header = CommonRequestHeader.customBuilder()
                .apiName("createDemandDepositAccount")
                .transmissionDate(formattedDate)
                .transmissionTime(formattedTime)
                .apiServiceCode("createDemandDepositAccount")
                .apiKey(apiKey)
                .userKey(userKey)
                .build();

        // AccountRequest 생성 (Header + accountTypeUniqueNo)
        CreateAccountRequest accountRequest = CreateAccountRequest.builder()
                .Header(Header)
                .accountTypeUniqueNo("001-1-0914b8aac1e947")
                .build();


        // HTTP 헤더 설정
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        // HTTP 요청 생성 (요청 본문에 AccountRequest 포함)
        HttpEntity<CreateAccountRequest> requestEntity = new HttpEntity<>(accountRequest, httpHeaders);

        // API 요청 보내기 (URL은 실제 API 엔드포인트로 변경)
        String url = baseUrl + "/edu/demandDeposit/createDemandDepositAccount";

        try {
            log.debug("sent AccountCreate request to FinOpenApi server, url={}", url);
            log.debug("Request Body: {}", objectMapper.writeValueAsString(accountRequest));
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );
            log.debug("received Register response from FinOpenApi server, response={}", response);
            String responseBody = response.getBody();
            if (responseBody != null) {
                JsonNode jsonNode = objectMapper.readTree(responseBody);
                JsonNode recNode = jsonNode.get("REC");
                return recNode.get("accountNo").asText();
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
