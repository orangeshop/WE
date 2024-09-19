package com.akdong.we.invitation.domain.formal;

import com.akdong.we.invitation.domain.TIMEZONE;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetaInfo {

    private String date;
    private TIMEZONE timezone;
    private int hour;
    private int minute;

    private String address;
    private String address_detail;
    private String wedding_hall;
}
