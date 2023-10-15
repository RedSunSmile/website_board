package com.co.kr.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter

public enum ErrorCode {
	/**
	 * status : HTTP 응답 상태 코드
	 * code : 에러 코드
	 * message : 에러 메시지
	 */
	//공통
	INTERNAL_SERVER_ERROR(500, "ER001", "서버처리중 오류가 발생하였습니다"),
	PAGE_NOT_FOUND(404, "ER002", "해당하는 정보를 찾을 수 없습니다"),
	
	//공지사항 등록
	NTC_TITLE_BLANK(400, "NC001", "제목을 입력해주시기 바랍니다"),
	NTC_TITLE_SIZE(400, "NC002", "제목은 100자이하로 작성해주시기 바랍니다"),
	NTC_CONTENT_BLANK(400, "NC003", "내용을 작성해주시기 바랍니다"),
	NTC_MEMBER_ID_NOT_EQUAL(400, "NC004", "작성자와 일치하지 않습니다"),
	
	//댓글 등록
	REPLY_MESSAGE_BLANK(400, "RE001", "댓글을 입력해주시기 바랍니다")
	;

	private int status;
	private String code;
	private String message;
	private String detail;
	
	ErrorCode(int status, String code, String message){
		this.status=status;
		this.code=code;
		this.message=message;
	}
}
