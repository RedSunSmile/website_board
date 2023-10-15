package com.co.kr.core.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CommonCode {
	
	SELECT(200, "CA001", "조회에 성공하였습니다"),
	INSERT(200, "CA002", "저장에 성공하였습니다"),
	UPDATE(200, "CA003", "수정에 성공하였습니다"),
	DELETE(200, "CA004", "삭제에 성공하였습니다"),
	
	LOGIN(200, "CA005", "로그인하였습니다"),
	LOGOUT(200, "CA006", "로그아웃하였습니다"),
	
	REFRESH_TOKEN(200, "CA007", "토큰발급하였습니다"),
	
	SEND_EMAIL_SUCCESS(200, "CA008", "EMAIL 발송에 성공하였습니다"),
	
	
	AUTH_USE_ID(200, "CA009", "사용가능한 아이디 입니다"),
	AUTH_NOT_USE_ID(200, "CA010", "사용불가능한 아이디 입니다"),
	AUTH_SIGN_UP(200, "CA011", "회원 가입에 성공하였습니다"),
	;
	
	
	private int status;
	private String code;
	private String message;
	
	
	CommonCode(int status, String code,  String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}
}