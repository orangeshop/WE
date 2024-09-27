package com.akdong.we.bank;

import com.akdong.we.api.FinApiCallService;
import com.akdong.we.common.exception.BusinessException;
import com.akdong.we.couple.entity.Couple;
import com.akdong.we.couple.repository.CoupleRepository;
import com.akdong.we.couple.response.CoupleInfo;
import com.akdong.we.couple.service.CoupleService;
import com.akdong.we.ledger.LedgerService;
import com.akdong.we.member.entity.Member;
import com.akdong.we.member.entity.MemberAccount;
import com.akdong.we.member.exception.member.MemberErrorCode;
import com.akdong.we.member.repository.MemberAccountRepository;
import com.akdong.we.member.repository.MemberRepository;
import com.akdong.we.member.response.MemberInfo;
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
    private final CoupleService coupleService;
    private final LedgerService ledgerService;
    private final MemberRepository memberRepository;
    private final CoupleRepository coupleRepository;

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

    public MemberInfo registerPriorAccount(Member member, String accountNo){
        member.setPriorAccount(accountNo);
        memberRepository.save(member);
        return MemberInfo.of(member);
    }

    public CoupleInfo registerCoupleAccount(Member member, String accountNo, String bankName){
        Couple couple = coupleService.getMyCoupleInfo(member)
                .orElseThrow(() -> new BusinessException(MemberErrorCode.COUPLE_NOT_FOUND_ERROR));

        couple.setAccountNumber(accountNo);
        couple.setAccountOwnerName(member.getNickname());
        couple.setAccountBankName(bankName);
        coupleRepository.save(couple);


//        ledgerService.createLedger(couple);

        return CoupleInfo.of(couple);
    }

    public List<TransactionInfo> transactionHistory(Member member, String accountNo) throws JsonProcessingException {
        JsonNode jsonResponse = finApiCallService.getTransactionHistoryList(member.getUserKey(), accountNo);
        List<TransactionInfo> transactionInfoList = new ArrayList<>();

        for(JsonNode node : jsonResponse){
            transactionInfoList.add(objectMapper.treeToValue(node, TransactionInfo.class));
        }
        return transactionInfoList;
    }
}
