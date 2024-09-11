package com.akdong.we.invitation.service;

import com.akdong.we.invitation.domain.FormalInvitationDto;
import com.akdong.we.invitation.domain.FormalInvitationEntity;
import com.akdong.we.invitation.repository.FormalInvitationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class FormalInvitationService {

    private final FormalInvitationRepository formalInvitationRepository;

    public FormalInvitationDto saveFormalInvitation(FormalInvitationDto invitation){
        return formalInvitationRepository
                .save(invitation.asEntity())
                .asDto();
    }

    public FormalInvitationDto findFormalInvitationById(long id){
        return formalInvitationRepository
                .findById(id)
                .orElseThrow()
                .asDto();
    }

    public FormalInvitationDto updateFormalInvitation(FormalInvitationDto invitation){
        return formalInvitationRepository
                .save(invitation.asEntity())
                .asDto();
    }

    public void deleteFormalInvitationById(long id){
        formalInvitationRepository.deleteById(id);
    }

    public List<FormalInvitationDto> findAllFormalInvitation(long coupleId){
        return formalInvitationRepository
                .findFormalInvitationByCoupleId(coupleId)
                .stream()
                .map(FormalInvitationEntity::asDto)
                .toList();
    }
}
