package com.co.kr.complain;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.co.kr.complain.dto.ComplainDto;
import com.co.kr.complain.dto.ReplyDto;
import com.co.kr.complain.dto.SearchDto;

@Mapper
public interface ComplainMapper {

		//불만사항 전체 개수 파악
		public int getComplainTotalCount(SearchDto searchDto) throws Exception;

		//불만사항 리스트 목록
		public List<ComplainDto> getComplainList(SearchDto searchDto) throws Exception;

		//불만사항 리스트 등록
		public void insertComplain(ComplainDto complainDto) throws Exception;

		//불만사항 수정읽기
		public ComplainDto readComplain(long idx);

		//불만사항 조회(상세보기)
		public ComplainDto getComplainSee(long noticeIdx) throws Exception;

		//불만사항 수정
		public void updateComplain(ComplainDto complainDto) throws Exception;

		//불만사항 지우기
		public void deleteComplain(ComplainDto complainDto) throws Exception;

		//불만댓글 등록
		public void insertReply(ReplyDto replyDto) throws Exception;

		//불만댓글 리스트
		public List<ReplyDto> readReply(long complainIdx) throws Exception;

		//불만댓글 수정
		public void updateReplyByReplyIdx(ReplyDto replyDto) throws Exception;

		//불만댓글 삭제
		public void deleteReply(ReplyDto replyDto) throws Exception;







}
