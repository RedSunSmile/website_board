package com.co.kr.exchangestop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchDto {
	//페이지 목록페이지
		private int nowPage;
		private String searchText;
		private int listCount;
		private int pageGroup;

		//컨트롤저장페이지
		private int startNum;
		private int endNum;
}
