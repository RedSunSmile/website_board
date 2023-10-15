package com.co.kr.shop.product.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class MemberDto {

	private Long rowNum;
	private Long idx;
	private String id;
	private String password;
	private String email;
	private String name;
	private String address;
	private String useYn;
	private String roleId;
	private String regDt;
	private	String modId;
	private String modDt;
	private String userId;















}
