package com.akdong.we.invitation.domain.formal;

import com.akdong.we.invitation.domain.BirthOrder;
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
    private BirthOrder birthOrder;

    private String fatherLastName;
    private String fatherFirstName;

    private String motherLastName;
    private String motherFirstName;
}
