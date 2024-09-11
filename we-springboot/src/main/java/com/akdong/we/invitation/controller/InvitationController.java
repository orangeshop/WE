package com.akdong.we.invitation.controller;

import com.akdong.we.file.service.FileService;
import com.akdong.we.invitation.domain.FormalInvitationDto;
import com.akdong.we.invitation.domain.CustomInvitationDto;
import com.akdong.we.invitation.service.FormalInvitationService;
import com.akdong.we.invitation.service.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class InvitationController {

    private final InvitationService invitationService;
    private final FormalInvitationService formalInvitationService;
    private final FileService fileService;

    @MessageMapping("/invitation/send")
    public void send(CustomInvitationDto invitation) throws Exception {
        invitationService.handleInvitation(invitation);
    }

    @PostMapping("/invitation/formal")
    public FormalInvitationDto saveFormalInvitation(
            @RequestPart(value = "invitation") FormalInvitationDto invitation,
            @RequestPart(value = "file",required = false) MultipartFile file) throws Exception {

        if(file != null) invitation.setUrl(fileService.upload(file,"formal"));
        return formalInvitationService.saveFormalInvitation(invitation);
    }

    @GetMapping("/invitation/formal/{invitationId}")
    public FormalInvitationDto findFormalInvitationById(@PathVariable Long invitationId) throws Exception {
        return formalInvitationService.findFormalInvitationById(invitationId);
    }

    @GetMapping("/invitation/formal/couple/{coupleId}")
    public List<FormalInvitationDto> findAllFormalInvitation(@PathVariable Long coupleId) throws Exception {
        return formalInvitationService.findAllFormalInvitation(coupleId);
    }
}
