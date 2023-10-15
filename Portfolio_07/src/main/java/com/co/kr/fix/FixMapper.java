package com.co.kr.fix;



import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.co.kr.fix.dto.FixDto;
import com.co.kr.fix.dto.SearchDto;

import com.co.kr.fix.dto.ReplyDto;




@Mapper
public interface FixMapper {

	// 전체 개수 파악
	public int getFixTotalCount(SearchDto searchDto) throws Exception;

	// 리스트 목록
	public List<FixDto> getFixList(SearchDto searchDto) throws Exception;

	// 리스트 등록
	public void insertFix(FixDto fixDto) throws Exception;

	// 수정읽기
	public FixDto readFix(long idx);

	// 조회(상세보기)
	public FixDto getFixSee(long fixIdx) throws Exception;

	//as사항 수정
	public void updateFix(FixDto fixDto) throws Exception;

	//as사항 지우기
	public void deleteFix(FixDto fixDto) throws Exception;

	//as댓글 등록
	public void insertReply(ReplyDto replyDto) throws Exception;

	//as댓글 리스트
	public List<ReplyDto> readReply(long fixIdx) throws Exception;

	//as댓글 수정
	public void updateReplyByReplyIdx(ReplyDto replyDto) throws Exception;

	//as댓글 삭제
	public void deleteReply(ReplyDto replyDto) throws Exception;




		

	

	








}
