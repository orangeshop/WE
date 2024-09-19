package com.akdong.we.invitation.service;

import com.akdong.we.file.domain.FileDto;
import com.akdong.we.invitation.domain.FormalInvitationDto;
import com.akdong.we.invitation.domain.FormalInvitationEntity;
import com.akdong.we.invitation.domain.formal.EmptyFormalInvitation;
import com.akdong.we.invitation.domain.formal.GreetingsDto;
import com.akdong.we.invitation.domain.formal.MetaInfo;
import com.akdong.we.invitation.domain.formal.PersonDto;
import com.akdong.we.invitation.repository.FormalInvitationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class FormalInvitationService {

    private final FormalInvitationRepository formalInvitationRepository;

    public EmptyFormalInvitation saveFormalInvitation(){
        FormalInvitationDto ret = formalInvitationRepository
                .save(FormalInvitationEntity.builder().build())
                .asDto();

        return EmptyFormalInvitation.builder()
                .invitation_id(ret.getInvitationId())
                .build();
    }

    public FileDto updateFormalInvitationFile(String url, long id) {
        FormalInvitationEntity invitation = getFormalIvnitationEntity(id);
        invitation.setUrl(url);

        return formalInvitationRepository
                .save(invitation)
                .asFileDto();
    }

    @Transactional
    public PersonDto updateFormalInvitationBride(long id, PersonDto bride) {
        FormalInvitationEntity invitation = getFormalIvnitationEntity(id);

        invitation.setBride_last_name(bride.getLastName());
        invitation.setBride_first_name(bride.getFirstName());

        invitation.setBride_father_first_name(bride.getFatherFirstName());
        invitation.setBride_father_last_name(bride.getFatherLastName());

        invitation.setBride_mother_last_name(bride.getMotherLastName());
        invitation.setBride_mother_first_name(bride.getMotherFirstName());

        return formalInvitationRepository
                .save(invitation)
                .asBrideDto();
    }

    public PersonDto updateFormalInvitationGroom(long id, PersonDto groom)
    {
        FormalInvitationEntity invitation = getFormalIvnitationEntity(id);

        invitation.setGroom_last_name(groom.getLastName());
        invitation.setGroom_first_name(groom.getFirstName());

        invitation.setGroom_father_first_name(groom.getFatherFirstName());
        invitation.setGroom_father_last_name(groom.getFatherLastName());

        invitation.setGroom_mother_last_name(groom.getMotherLastName());
        invitation.setGroom_mother_first_name(groom.getMotherFirstName());

        return formalInvitationRepository
                .save(invitation)
                .asGroomDto();
    }

    public GreetingsDto updateFormalInvitationGreetings(
            @PathVariable("invitationId") long id,
            GreetingsDto greetings
    )
    {
        FormalInvitationEntity invitation = getFormalIvnitationEntity(id);
        invitation.setGreetings(greetings.getGreetings());

        return formalInvitationRepository
                .save(invitation)
                .asGreetingDto();
    }

    public MetaInfo updateFormalInvitationMetaInfo(long id, MetaInfo metaInfo)
    {
        FormalInvitationEntity invitation = getFormalIvnitationEntity(id);
        invitation.setDate(metaInfo.getDate());
        invitation.setTimezone(metaInfo.getTimezone());
        invitation.setHour(metaInfo.getHour());
        invitation.setMinute(metaInfo.getMinute());

        invitation.setAddress(metaInfo.getAddress());
        invitation.setAddress_detail(metaInfo.getAddress_detail());
        invitation.setWedding_hall(metaInfo.getWedding_hall());

        return formalInvitationRepository
                .save(invitation)
                .asMetaInfoDto();
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

    private FormalInvitationEntity getFormalIvnitationEntity(long id) {
        return formalInvitationRepository.findById(id).orElseThrow();
    }
}
