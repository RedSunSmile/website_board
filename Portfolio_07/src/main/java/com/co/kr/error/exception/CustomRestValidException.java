package com.co.kr.error.exception;
import com.co.kr.error.*;
public class CustomRestValidException extends Exception {
	private static final long serialVersionUID=1L;
	private ErrorCode errorCode;
	
	/**
	 * 
	 * @param detail
	 * @param errorCode
	 */
	public CustomRestValidException(String detail, ErrorCode errorCode) {
		super(detail);
		this.errorCode=errorCode;
	}
	
	public CustomRestValidException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode=errorCode;
	}
	
	public ErrorCode getErrorCode() {
		return this.errorCode;
	}
}
