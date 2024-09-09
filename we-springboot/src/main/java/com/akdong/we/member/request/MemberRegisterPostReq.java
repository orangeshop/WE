package com.akdong.we.member.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저 회원가입 API ([POST] /api/v1/users) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
public class MemberRegisterPostReq {
	@NotBlank(message = "이메일을 입력해주세요")
	@Schema(description = "이메일", example = "test1@gmail.com")
	private String email;

	@NotBlank(message = "비밀번호를 입력해주세요")
	@Schema(description = "비밀번호", example = "1234")
	private String password;

	@NotBlank(message = "닉네임을 입력해주세요")
	@Schema(description = "닉네임", example = "테스트1")
	private String nickname;


}
