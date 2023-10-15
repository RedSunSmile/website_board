package com.co.kr.member;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.co.kr.member.dto.MemberDto;
import com.co.kr.member.dto.ReplyDto;
import com.co.kr.member.dto.SearchDto;
import com.co.kr.member.dto.SignupResDto;
import lombok.extern.slf4j.Slf4j;
@Slf4j
// 관리자는 member회원중에 한명이다 따라서 관리자는 member자료를 가져다 쓴다
@Service
public class MemberService {


		@Autowired
		private MemberMapper memberMapper;



		//회원가입폼사항
		public SignupResDto signup(MemberDto memberDto) throws Exception{
			int idCheck=this.idCheck(memberDto.getId());//회원가입 시 중복체크 값에 idCheck

			String msg="회원가입에 성공하였습니다";
			int code=0;

			log.info(msg);


			if(idCheck !=0) {
				msg="사용불가능한 아이디입니다";
				code=1;//아이디중복
			}else {
				memberDto.setUseYn("Y");
				memberMapper.insertMember(memberDto);
			}

			return SignupResDto.builder()
					.code(code)
					.msg(msg)
					.build();

		}

		//아이디 중복체크
		public int idCheck(String id) throws Exception{

			return memberMapper.idCheck(id);
		}


		//로그인
		public Map<String, Object> login(MemberDto memberDto) throws Exception{
			Map<String, Object> resultMap=new HashMap<>();
			boolean canLogin=false;//로그인 상태 확인 기본값 false;

			String msg=null; //메시지 기본값 null

			//로그인 쿼리문 select * 모든 정보 다 뽑았음. MemberDto dto에 로그인 입력 정보
			MemberDto dto=memberMapper.login(memberDto);

			if(dto==null) { //dto에 정보가 없으면 당연히 등록된 사용자가 없음
				//에러처리
				msg="등록된 사용자가 없습니다";
			}else if(!"Y".equals(dto.getUseYn())) {//N이 탈퇴한 사용자
				msg="탈퇴한 사용자입니다.";
			}else if(!dto.getPassword().equals(memberDto.getPassword())) {
				msg="패스워드가 일치하지 않습니다";
			}else {
				canLogin=true;//로그인 성공했을시 true값으로 변경
			 //관리자로그인 성공했을시 true값으로 변경
			}
			//result.put("키 값", value 값)

			System.out.println(memberDto);
			resultMap.put("msg", msg);
			resultMap.put("canLogin", canLogin);
			resultMap.put("member", dto);

			return resultMap;


		}
		//회원 전체 개수 파악
		public int getMemberTotalCount(SearchDto searchDto) throws Exception{
			return memberMapper.getMemberTotalCount(searchDto);
		}

		//회원 리스트 목록
		public List<MemberDto> getMemberList(SearchDto searchDto) throws Exception{
			return memberMapper.getMemberList(searchDto);
		}

		/**
		 * 회원가입
		 * @param memberDto
		 * @throws Exception
		 */

		//회원가입
		public void insertMember(MemberDto memberDto) throws Exception{
			memberMapper.insertMember(memberDto);
		}

		//회원 수정읽기

		public MemberDto readMember(long idx) throws Exception{
			return memberMapper.readMember(idx);
		}

		//회원 조회(상세보기)
		public MemberDto getMemberSee(Long memberIdx) throws Exception{
			return memberMapper.getMemberSee(memberIdx);
		}

		//회원 수정
		public void updateMember(MemberDto memberDto) throws Exception{
			memberMapper.updateMember(memberDto);
		}


		//회원 지우기
		public void deleteMember(MemberDto memberDto) throws Exception{
			memberMapper.deleteMember(memberDto);
		}

		//회원댓글 등록
		@Transactional(propagation = Propagation.REQUIRED, rollbackFor= {Exception.class})
		public void insertReply(ReplyDto replyDto, long memberIdx) {
			replyDto.setMemberIdx(memberIdx);
			memberMapper.insertReply(replyDto);
		}


		//회원댓글 리스트
		public List<ReplyDto> readReply(long memberIdx) throws Exception{
			return memberMapper.readReply(memberIdx);
		}

		//회원댓글 수정
		@Transactional(propagation=Propagation.REQUIRED, rollbackFor= {Exception.class})
		public void updateReplyByReplyIdx(ReplyDto replyDto) throws Exception{
			memberMapper.updateReplyByReplyIdx(replyDto);
		}

		//회원댓글 삭제
		@Transactional(propagation = Propagation.REQUIRED, rollbackFor= {Exception.class})
		public void deleteReply(ReplyDto replyDto) throws Exception{
			memberMapper.deleteReply(replyDto);
		}


}
