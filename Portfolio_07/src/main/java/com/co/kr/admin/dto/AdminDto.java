package com.co.kr.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class AdminDto {//계층별 value값 자료전송 mapper로 드간다

	String adminId;
	String adminPass;
	String name;
	String useYn;
	int	no;


}
