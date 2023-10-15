package com.co.kr.util;

import jakarta.servlet.http.HttpSession;

import com.co.kr.admin.dto.AdminDto;
import com.co.kr.member.dto.MemberDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SessionUtil {

	public static String getMemberId(HttpSession session) {
		MemberDto memberdto = (MemberDto) session.getAttribute("member");
		log.info("session : {}", memberdto);
		return memberdto.getId();
	}

	public static String getAdminId(HttpSession session){

		AdminDto admindto=(AdminDto)session.getAttribute("admin");

		log.info("session: {}", admindto);
		/* log.info("session: {}",admindto.getAdminId() ); */
		return admindto.getAdminId();
	}
//	// 로그인 아이디 가져오기
//	public static String getUsername() {
//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		CustomUserDto user = (CustomUserDto) principal;
//		return user.getUsername();
//	}


}