package com.akdong.we.invitation.controller;

import com.akdong.we.file.service.FileService;
import com.akdong.we.invitation.domain.FormalInvitationDto;
import com.akdong.we.invitation.domain.CustomInvitationDto;
import com.akdong.we.invitation.service.FormalInvitationService;
import com.akdong.we.invitation.service.InvitationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/v1/invitation")
@Tag(name = "청첩장 API", description = "청첩장 관련 API")
@RequiredArgsConstructor
public class InvitationController {

    private final InvitationService invitationService;
    private final FormalInvitationService formalInvitationService;
    private final FileService fileService;

    @MessageMapping("/send")
    public void send(CustomInvitationDto invitation) throws Exception {
        invitationService.handleInvitation(invitation);
    }

    @PostMapping("/formal")
    @Operation(summary = "정보형 청첩장 생성", description = "정보형 청첩장 생성")
    public FormalInvitationDto saveFormalInvitation(
            @RequestPart(value = "invitation") FormalInvitationDto invitation,
            @RequestPart(value = "file",required = false) MultipartFile file) throws Exception {

        if(file != null) invitation.setUrl(fileService.upload(file,"formal"));
        return formalInvitationService.saveFormalInvitation(invitation);
    }

    @GetMapping("/formal/{invitationId}")
    @Operation(summary = "정보형 청첩장 조회", description = "청첩장 ID로 조회")
    public FormalInvitationDto findFormalInvitationById(@PathVariable("invitationId") Long invitationId) throws Exception {
        return formalInvitationService.findFormalInvitationById(invitationId);
    }

    @GetMapping("/formal/couple/{coupleId}")
    @Operation(summary = "정보형 청첩장 조회", description = "커플 ID로 조회")
    public List<FormalInvitationDto> findAllFormalInvitation(@PathVariable("coupleId") Long coupleId) throws Exception {
        return formalInvitationService.findAllFormalInvitation(coupleId);
    }
}
