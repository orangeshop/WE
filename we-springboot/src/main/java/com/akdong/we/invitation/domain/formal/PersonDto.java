package com.akdong.we.invitation.domain.formal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonDto {
    private String lastName;
    private String firstName;

    private String fatherLastName;
    private String fatherFirstName;

    private String motherLastName;
    private String motherFirstName;
}
