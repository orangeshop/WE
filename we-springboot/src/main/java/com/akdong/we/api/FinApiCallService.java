package com.akdong.we.api;

import com.akdong.we.api.request.*;
import com.akdong.we.bank.TransferRequest;
import com.akdong.we.ledger.entity.Gift;
import com.akdong.we.ledger.repository.GiftRepository;
import com.akdong.we.ledger.repository.LedgerGiftRepository;
import com.akdong.we.ledger.repository.LedgerRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.services.storage.Storage;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
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

    public String[] createFormatDateTime(){
        String[] dateAndTime = new String[2];

        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");

        String formattedDate = currentDate.format(dateFormatter);
        String formattedTime = currentTime.format(timeFormatter);

        dateAndTime[0] = formattedDate;
        dateAndTime[1] = formattedTime;

        return dateAndTime;
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

    public String deposit(String accountNo, Long transactionBalance, String transactionSummary, String userKey){

        String[] dateAndTime = createFormatDateTime();

        CommonRequestHeader Header = CommonRequestHeader.customBuilder()
                .apiName("updateDemandDepositAccountDeposit")
                .transmissionDate(dateAndTime[0])
                .transmissionTime(dateAndTime[1])
                .apiServiceCode("updateDemandDepositAccountDeposit")
                .apiKey(apiKey)
                .userKey(userKey)
                .build();

        UpdateDepositRequest updateDepositRequest = UpdateDepositRequest.builder()
                .Header(Header)
                .accountNo(accountNo)
                .transactionBalance(transactionBalance)
                .transactionSummary(transactionSummary)
                .build();

        // HTTP 헤더 설정
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        // HTTP 요청 생성 (요청 본문에 AccountRequest 포함)
        HttpEntity<UpdateDepositRequest> requestEntity = new HttpEntity<>(updateDepositRequest, httpHeaders);
        String url = baseUrl + "/edu/demandDeposit/updateDemandDepositAccountDeposit";
        try{
            log.debug("sent Deposit request to FinOpenApi server, url={}", url);
            log.debug("Request Body: {}", objectMapper.writeValueAsString(requestEntity));
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            log.debug("received Deposit response from FinOpenApi server, response={}", response);
            String responseBody = response.getBody();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            JsonNode recNode = jsonNode.get("REC");
            return recNode.get("transactionUniqueNo").asText();

        }catch(Exception e){
            log.error("Exception occurred during FinAPI registration: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public JsonNode accountList(String userKey){
        String[] dateAndTime = createFormatDateTime();

        CommonRequestHeader Header = CommonRequestHeader.customBuilder()
                .apiName("inquireDemandDepositAccountList")
                .transmissionDate(dateAndTime[0])
                .transmissionTime(dateAndTime[1])
                .apiServiceCode("inquireDemandDepositAccountList")
                .apiKey(apiKey)
                .userKey(userKey)
                .build();

        HeaderOnlyRequest headerOnlyRequest = HeaderOnlyRequest.builder()
                .Header(Header)
                .build();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<HeaderOnlyRequest> requestEntity = new HttpEntity<>(headerOnlyRequest, httpHeaders);
        String url = baseUrl + "/edu/demandDeposit/inquireDemandDepositAccountList";

        try{
            log.debug("sent 계좌 목록 조회 request to FinOpenApi server, url={}", url);
            log.debug("Request Body: {}", objectMapper.writeValueAsString(requestEntity));
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            log.debug("received Deposit response from FinOpenApi server, response={}", response);
            String responseBody = response.getBody();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            JsonNode recNode = jsonNode.get("REC");
            return recNode;

        }catch(Exception e){
            log.error("Exception occurred during FinAPI registration: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public String openAccountAuth(String userKey, String accountNo){
        String[] dateAndTime = createFormatDateTime();

        CommonRequestHeader Header = CommonRequestHeader.customBuilder()
                .apiName("openAccountAuth")
                .transmissionDate(dateAndTime[0])
                .transmissionTime(dateAndTime[1])
                .apiServiceCode("openAccountAuth")
                .apiKey(apiKey)
                .userKey(userKey)
                .build();

        OpenAccountAuthRequest openAccountAuthRequest = OpenAccountAuthRequest.builder()
                .Header(Header)
                .accountNo(accountNo)
                .authText("akdong")
                .build();


        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<OpenAccountAuthRequest> requestEntity = new HttpEntity<>(openAccountAuthRequest, httpHeaders);
        String url = baseUrl + "/edu/accountAuth/openAccountAuth";

        try{
            log.debug("sent openAccountAuth request to FinOpenApi server, url={}", url);
            log.debug("Request Body: {}", objectMapper.writeValueAsString(requestEntity));
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            log.debug("received openAccountAuth response from FinOpenApi server, response={}", response);
            String responseBody = response.getBody();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            JsonNode recNode = jsonNode.get("REC");
            String transactionUniqueNo = recNode.get("transactionUniqueNo").asText();
            return getTransactionHistory(userKey, accountNo, transactionUniqueNo);

        }catch(Exception e){
            log.error("Exception occurred during FinAPI openAccountAuth: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }


    }

    public String getTransactionHistory(String userKey, String accountNo, String transactionUniqueNo){
        String[] dateAndTime = createFormatDateTime();

        CommonRequestHeader Header = CommonRequestHeader.customBuilder()
                .apiName("inquireTransactionHistory")
                .transmissionDate(dateAndTime[0])
                .transmissionTime(dateAndTime[1])
                .apiServiceCode("inquireTransactionHistory")
                .apiKey(apiKey)
                .userKey(userKey)
                .build();

        GetTransactionHistoryRequest getTransactionHistoryRequest = GetTransactionHistoryRequest.builder()
                .Header(Header)
                .accountNo(accountNo)
                .transactionUniqueNo(transactionUniqueNo)
                .build();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<GetTransactionHistoryRequest> requestEntity = new HttpEntity<>(getTransactionHistoryRequest, httpHeaders);
        String url = baseUrl + "/edu/demandDeposit/inquireTransactionHistory";

        try{
            log.debug("sent getTransactionHistoryRequest request to FinOpenApi server, url={}", url);
            log.debug("Request Body: {}", objectMapper.writeValueAsString(requestEntity));
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            log.debug("received getTransactionHistoryRequest response from FinOpenApi server, response={}", response);
            String responseBody = response.getBody();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            JsonNode recNode = jsonNode.get("REC");
            return recNode.get("transactionSummary").asText();

        }catch(Exception e){
            log.error("Exception occurred during FinAPI openAccountAuth: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public String checkAuthCode(String userKey, String accountNo, String authText, String authCode){
        String[] dateAndTime = createFormatDateTime();

        CommonRequestHeader Header = CommonRequestHeader.customBuilder()
                .apiName("checkAuthCode")
                .transmissionDate(dateAndTime[0])
                .transmissionTime(dateAndTime[1])
                .apiServiceCode("checkAuthCode")
                .apiKey(apiKey)
                .userKey(userKey)
                .build();

        CheckAuthRequest checkAuthRequest = CheckAuthRequest.builder()
                .Header(Header)
                .accountNo(accountNo)
                .authText(authText)
                .authCode(authCode)
                .build();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CheckAuthRequest> requestEntity = new HttpEntity<>(checkAuthRequest, httpHeaders);
        String url = baseUrl + "/edu/accountAuth/checkAuthCode";

        try{
            log.debug("Request Body: {}", objectMapper.writeValueAsString(requestEntity));
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            log.debug("received checkAuthCode response from FinOpenApi server, response={}", response);
            String responseBody = response.getBody();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            JsonNode recNode = jsonNode.get("REC");
            return recNode.get("status").asText();

        }catch(Exception e){
            log.error("Exception occurred during FinAPI openAccountAuth: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public String transfer(String userKey, TransferRequest request){

        String[] dateAndTime = createFormatDateTime();

        CommonRequestHeader Header = CommonRequestHeader.customBuilder()
                .apiName("updateDemandDepositAccountTransfer")
                .transmissionDate(dateAndTime[0])
                .transmissionTime(dateAndTime[1])
                .apiServiceCode("updateDemandDepositAccountTransfer")
                .apiKey(apiKey)
                .userKey(userKey)
                .build();

        request.setHeader(Header);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<TransferRequest> requestEntity = new HttpEntity<>(request, httpHeaders);
        String url = baseUrl + "/edu/demandDeposit/updateDemandDepositAccountTransfer";

        try{
            log.debug("Request Body: {}", objectMapper.writeValueAsString(requestEntity));
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            log.debug("received checkAuthCode response from FinOpenApi server, response={}", response);
            String responseBody = response.getBody();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            JsonNode HeaderNode = jsonNode.get("Header");
            return HeaderNode.get("responseCode").asText();

        }catch(Exception e){
            log.error("Exception occurred during FinAPI openAccountAuth: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public JsonNode getCoupleAccount(String userKey, String accountNo){
        String[] dateAndTime = createFormatDateTime();

        CommonRequestHeader Header = CommonRequestHeader.customBuilder()
                .apiName("inquireDemandDepositAccount")
                .transmissionDate(dateAndTime[0])
                .transmissionTime(dateAndTime[1])
                .apiServiceCode("inquireDemandDepositAccount")
                .apiKey(apiKey)
                .userKey(userKey)
                .build();

        InquireDemandDepositRequest inquireDemandDepositRequest = InquireDemandDepositRequest.builder()
                .Header(Header)
                .accountNo(accountNo)
                .build();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<InquireDemandDepositRequest> requestEntity = new HttpEntity<>(inquireDemandDepositRequest, httpHeaders);
        String url = baseUrl + "/edu/demandDeposit/inquireDemandDepositAccount";

        try{
            log.debug("sent 계좌 단건 조회 request to FinOpenApi server, url={}", url);
            log.debug("Request Body: {}", objectMapper.writeValueAsString(requestEntity));
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            log.debug("received Deposit response from FinOpenApi server, response={}", response);
            String responseBody = response.getBody();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            return jsonNode.get("REC");

        }catch(Exception e){
            log.error("Exception occurred during FinAPI registration: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}