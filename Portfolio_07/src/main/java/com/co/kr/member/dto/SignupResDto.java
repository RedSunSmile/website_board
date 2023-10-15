package com.co.kr.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupResDto {
	// 0 회원가입 완료
	// 1 아이디 중복
	// -1 auth 관리자
	private int code;
	/* private int num; */


	// 회원가입에 성공하였습니다.
	// 사용불가능한 아이디 입니다.
	private String msg;

	//관리자님 환영합니다
	/* private String admin; */
}
