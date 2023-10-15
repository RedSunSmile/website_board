package com.co.kr.core.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@ToString
public class CommonResponse<T>{

	private Integer status;
	
	private String code;
	
	private String message;
	
	private T data;

	public CommonResponse(CommonCode code, T data) {
		this.message = code.getMessage();
		this.status = code.getStatus();
		this.code = code.getCode();
		this.data = data;		
	}
	
	public static <T> CommonResponse<T> of(CommonCode code , T data){
		return new CommonResponse<T>(code, data);
	}
	
	
	public static <T> CommonResponse<T> of(CommonCode code){
		return new CommonResponse<T>(code, null);
	}
}