package com.akdong.we.couple.response;

import com.akdong.we.member.response.MemberInfo;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateCodeResponse {
    private String code;
}
