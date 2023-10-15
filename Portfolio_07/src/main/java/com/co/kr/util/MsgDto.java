package com.co.kr.util;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MsgDto {
	private String msg;
	private int code;

}
