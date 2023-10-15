package com.co.kr.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchDto {

		private int nowPage;
		private String searchText;
		private int listCount;
		private int pageGroup;

		//컨트롤러에서 저장
		private int startNum;
		private int endNum;
}
