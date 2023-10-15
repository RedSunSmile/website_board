package com.co.kr.exchangestop;



import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.co.kr.exchangestop.dto.ExchangeStopDto;

import com.co.kr.exchangestop.dto.SearchDto;

import com.co.kr.exchangestop.dto.ReplyDto;




@Mapper
public interface ExchangeStopMapper {

	//공지사항 전체 개수 파악
			public int getExchangeStopTotalCount(SearchDto searchDto) throws Exception;

			//공지사항 리스트 목록
			public List<ExchangeStopDto> getExchangeStopList(SearchDto searchDto) throws Exception;

			//공지사항 리스트 등록
			public void insertExchangeStop(ExchangeStopDto exchangestopDto) throws Exception;

			//공지사항 수정읽기
			public ExchangeStopDto readExchangeStop(long idx);

			//공지사항 조회(상세보기)
			public ExchangeStopDto getExchangeStopSee(Long exchangestopIdx) throws Exception;


			//공지사항 수정
			public void updateExchangeStop(ExchangeStopDto exchangestopDto) throws Exception;


			//공지사항 지우기
			public void deleteExchangeStop(ExchangeStopDto exchangstopDto) throws Exception;


			//댓글 등록
			public void insertReply(ReplyDto replyDto);

			//댓글 리스트
			public List<ReplyDto> readReply(long exchangestopIdx) throws Exception;

			//댓글 수정
			public void updateReplyByReplyIdx(ReplyDto replyDto) throws Exception;

			//댓글 삭제
			public void deleteReply(ReplyDto replyDto) throws Exception;




		

	

	








}
