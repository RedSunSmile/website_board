package com.co.kr.pergola.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
 @AllArgsConstructor 
@NoArgsConstructor
@Builder
public class PergolaDto {

	private Long rowNum;
	
	private Long idx;
	public String title;
	private String content;
	private String regDt;
	private String regId;
	private String modDt;
	private String modId;
	private String useYn;
	
	
	public  PergolaDto(Long idx) {
		this.idx=idx;
	}



	
}
