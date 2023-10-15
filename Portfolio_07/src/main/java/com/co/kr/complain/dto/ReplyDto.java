package com.co.kr.complain.dto;


import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDto {

	private long idx;

	private long complainIdx;

	@NotBlank(message="REPLY_MESSAGE_BLANK")
	private String reply;

	private String regId;

	private String regDt;

	private String useYn;

	private String modDt;

	private String modId;

}
