package com.co.kr.admin;

import org.apache.ibatis.annotations.Mapper;

import com.co.kr.admin.dto.AdminDto;

@Mapper
public interface AdminMapper {

	//관리자 로그인
	public AdminDto adminlogin(AdminDto adminDto) throws Exception;





}
