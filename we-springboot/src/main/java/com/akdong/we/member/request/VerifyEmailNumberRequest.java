package com.akdong.we.member.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerifyEmailNumberRequest {
    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "유효한 이메일 주소를 입력해 주세요")
    private String email;
    @NotBlank(message = "인증번호를 입력해주세요")
    private String authNumber;

}
