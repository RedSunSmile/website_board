package com.co.kr.complain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComplainDto {

		private Long rowNum;

		private Long idx;
		public String title;
		private String content;
		private String regDt;
		private String regId;
		private String modDt;
		private String modId;
		private String useYn;


}
