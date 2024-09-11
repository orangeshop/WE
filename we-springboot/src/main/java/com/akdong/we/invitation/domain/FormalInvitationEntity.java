package com.akdong.we.invitation.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Table(name="formal_invitation_entity")
public class FormalInvitationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long invitation_id;

    @Column
    private String url;

    @Column
    private long couple_id;

    //신랑 측 정보
    @Column
    private String groom_last_name;

    @Column
    private String groom_first_name;

    @Enumerated(EnumType.STRING)
    @Column
    private BirthOrder groom_birth_order;

    //신랑 부모 정보
    @Column
    private String groom_father_last_name;

    @Column
    private String groom_father_first_name;

    @Column
    private String groom_mother_last_name;

    @Column
    private String groom_mother_first_name;

    //신부 측 정보
    @Column
    private String bride_last_name;

    @Column
    private String bride_first_name;

    @Enumerated(EnumType.STRING)
    @Column
    private BirthOrder bride_birth_order;

    //신부 부모 정보
    @Column
    private String bride_father_last_name;

    @Column
    private String bride_father_first_name;

    @Column
    private String bride_mother_last_name;

    @Column
    private String bride_mother_first_name;

    //인사말
    @Column
    private String greetings;

    //날짜
    @Column
    private String date;

    @Enumerated(EnumType.STRING)
    @Column
    private TIMEZONE timezone;

    @Column
    private int hour;

    @Column
    private int minute;

    //주소
    @Column
    private String address;

    @Column
    private String address_detail;

    @Column
    private String wedding_hall;

    public FormalInvitationDto asDto(){
        return FormalInvitationDto.builder()

                .invitationId(invitation_id)
                .coupleId(couple_id)
                .url(url)

                .groomLastName(groom_last_name)
                .groomFirstName(groom_first_name)
                .groomBirthOrder(groom_birth_order)

                .groomFatherLastName(groom_father_last_name)
                .groomFatherFirstName(groom_father_first_name)

                .groomMotherLastName(groom_mother_last_name)
                .groomMotherFirstName(groom_mother_first_name)

                .brideLastName(bride_last_name)
                .brideFirstName(bride_first_name)
                .brideBirthOrder(bride_birth_order)

                .brideFatherLastName(bride_father_last_name)
                .brideFatherFirstName(bride_father_first_name)

                .brideMotherLastName(bride_mother_last_name)
                .brideMotherFirstName(bride_mother_first_name)

                .greetings(greetings)
                .date(date)
                .timezone(timezone)
                .hour(hour)
                .minute(minute)

                .address(address)
                .addressDetail(address_detail)
                .weddingHall(wedding_hall)
                .build();
    }
}
