package com.co.kr.admin;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.co.kr.admin.dto.AdminDto;
import lombok.extern.slf4j.Slf4j;
@Slf4j

@Service
public class AdminService {

	@Autowired
	private AdminMapper adminMapper;



	//로그인(세션에 아이디와 비밀번호저장/
	//id가 null이면 false를 리턴하고 값이 있으면 true를 리턴
	  public Map<String, Object> adminlogin(AdminDto adminDto) throws Exception{
		  Map<String, Object> resultMap=new HashMap<> ();
		  boolean canLogin=false;

		  String msg=null;
		  AdminDto Dto=adminMapper.adminlogin(adminDto);

	 if(Dto==null) {
		 msg="등록자가 없습니다.";
		 }else if(!"Y".equals(Dto.getUseYn())) {
		 msg="탈퇴한 사용자입니다";
		 }else if(!Dto.getAdminPass().equals(adminDto.getAdminPass())) {
		 msg="패스워드가 일치하지 않습니다.";
		 }else {
			 canLogin=true;
			 }
	 	 resultMap.put("msg", msg);
		 resultMap.put("canLogin", canLogin);
		 resultMap.put("admin", adminDto);
		 return resultMap;
	}



		/**
		 * 회원가입
		 * @param memberDto
		 * @throws Exception
		 */


}
