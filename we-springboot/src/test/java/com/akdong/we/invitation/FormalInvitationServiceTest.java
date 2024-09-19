package com.akdong.we.invitation;

import com.akdong.we.invitation.domain.FormalInvitationDto;
import com.akdong.we.invitation.domain.FormalInvitationEntity;
import com.akdong.we.invitation.repository.FormalInvitationRepository;
import com.akdong.we.invitation.service.FormalInvitationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class FormalInvitationServiceTest {

    @Autowired
    private FormalInvitationService formalInvitationService;

    @MockBean
    private FormalInvitationRepository formalInvitationRepository;

    @Test
    public void testCreateFormalInvitation() {
        // Given
//        FormalInvitationDto invitation = new FormalInvitationDto();
//        invitation.setGroomFirstName("John");
//        invitation.setBrideFirstName("Jane");
//
//        // Mocking the repository save method
//        when(formalInvitationRepository.save(any(FormalInvitationEntity.class)))
//                .thenReturn(invitation.asEntity());
//
//        // When
//        FormalInvitationDto savedInvitation = formalInvitationService
//                .saveFormalInvitation(invitation);
//
//        // Then
//        assertNotNull(savedInvitation);
//        assertEquals("John", savedInvitation.getGroomFirstName());
//        assertEquals("Jane", savedInvitation.getBrideFirstName());
//        verify(formalInvitationRepository, times(1)).save(any(FormalInvitationEntity.class));
    }

    @Test
    public void testFindAllFormalInvitation() {
        // Given
        long coupleId = 1L;

        // Entity 객체 생성
        FormalInvitationEntity entity1 = new FormalInvitationEntity();
        entity1.setCouple_id(coupleId);
        entity1.setBride_last_name("John");
        entity1.setBride_first_name("Jane");

        FormalInvitationEntity entity2 = new FormalInvitationEntity();
        entity2.setCouple_id(coupleId);
        entity2.setGroom_last_name("Mike");
        entity2.setGroom_first_name("Anna");

        // DTO 객체 생성
        FormalInvitationDto dto1 = new FormalInvitationDto();
        dto1.setGroomFirstName("John");
        dto1.setBrideFirstName("Jane");

        FormalInvitationDto dto2 = new FormalInvitationDto();
        dto2.setGroomFirstName("Mike");
        dto2.setBrideFirstName("Anna");

        // Mocking the repository
        when(formalInvitationRepository.findFormalInvitationByCoupleId(coupleId))
                .thenReturn(Arrays.asList(entity1, entity2));

        // When
        List<FormalInvitationDto> result = formalInvitationService.findAllFormalInvitation(coupleId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        System.out.println(result.get(0));
        assertEquals("John", result.get(0).getBrideLastName());
        assertEquals("Jane", result.get(0).getBrideFirstName());
        assertEquals("Mike", result.get(1).getGroomLastName());
        assertEquals("Anna", result.get(1).getGroomFirstName());

        // Verify that findFormalInvitationByCoupleId was called with the correct coupleId
        verify(formalInvitationRepository, times(1)).findFormalInvitationByCoupleId(coupleId);
    }
}