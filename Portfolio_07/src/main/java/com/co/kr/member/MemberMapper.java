package com.co.kr.member;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.co.kr.member.dto.MemberDto;
import com.co.kr.member.dto.ReplyDto;
import com.co.kr.member.dto.SearchDto;


@Mapper
public interface MemberMapper {

	//회원가입
	public void insertMember(MemberDto memberDto) throws Exception;

	//회원아이디 중복체크
	public int idCheck(String id) throws Exception;


	//회원로그인
	public MemberDto login(MemberDto memberDto) throws Exception;



	//회원 전체 개수 파악
	public int getMemberTotalCount(SearchDto searchDto) throws Exception;

	//회원 리스트 목록
	public List<MemberDto> getMemberList(SearchDto searchDto) throws Exception;



	//회원 수정읽기
	public MemberDto readMember(long idx);

	//회원 조회(상세보기)
	public MemberDto getMemberSee(Long memberIdx) throws Exception;


	//회원 수정
	public void updateMember(MemberDto memberDto) throws Exception;


	//회원 지우기
	public void deleteMember(MemberDto memberDto) throws Exception;


	//회원댓글 등록
	public void insertReply(ReplyDto replyDto);

	//회원댓글 리스트
	public List<ReplyDto> readReply(long memberIdx) throws Exception;

	//회원댓글 수정
	public void updateReplyByReplyIdx(ReplyDto replyDto) throws Exception;

	//회원댓글 삭제
	public void deleteReply(ReplyDto replyDto) throws Exception;

}
