package com.akdong.we.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CreateAccountRequest {
    private CommonRequestHeader Header;
    private String accountTypeUniqueNo;
}
