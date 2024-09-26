package com.akdong.we.bank;

import com.akdong.we.api.FinApiCallService;
import com.akdong.we.member.entity.Member;
import com.akdong.we.member.entity.MemberAccount;
import com.akdong.we.member.repository.MemberAccountRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class BankService {
    private final MemberAccountRepository memberAccountRepository;
    private final FinApiCallService finApiCallService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<GetAccountResponse> accountList(Member member) throws JsonProcessingException {
        List<MemberAccount> myAccounts = memberAccountRepository.findByMember(member);
        List<String> accountNoList = new ArrayList<String>();
        for(MemberAccount memberAccount: myAccounts){
            accountNoList.add(memberAccount.getAccountNo());
        }

        JsonNode jsonResponse = finApiCallService.accountList(member.getUserKey());
        List<GetAccountResponse> response = new ArrayList<>();
        for (JsonNode node : jsonResponse) {
            // node에 있는 accountNo가 accountNoList에 있다면 담는다.
            for(String accountNo : accountNoList){
                if(Objects.equals(accountNo, node.get("accountNo").asText())){
                    response.add(objectMapper.treeToValue(node, GetAccountResponse.class));
                }
            }
        }
        return response;
    }
}
