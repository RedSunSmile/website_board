package com.co.kr.fix.dto;

import java.util.List;

import lombok.Data;





// 데이터모델sql과 순서와 뒤바껴도 아무상관없이 되게하기 위해,
	@Data				//인수가 없는 생성자 추가
public class FixDto {

		private Long rowNum;


	private Long idx;//pk
	private String title;
	private String content; //설명

	private String regId;
	private String regDt;
	private String modId;
	private String modDt;
	private String useYn;
	private String image;
	public void setFixIdx(long fixIdx) {
		// TODO Auto-generated method stub
		
	}

	

	
	

	

	
	





}
