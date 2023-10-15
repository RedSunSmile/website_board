package com.co.kr.exchangestop;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.co.kr.exchangestop.dto.ExchangeStopDto;

import com.co.kr.exchangestop.dto.ReplyDto;
import com.co.kr.exchangestop.dto.SearchDto;


import lombok.extern.slf4j.Slf4j;




@Slf4j
@Service
public class ExchangeStopService {


	@Autowired
	private ExchangeStopMapper exchangestopMapper;
	
	//공지사항 전체 개수
			public int getExchangeStopTotalCount(SearchDto searchDto) throws Exception{
				return exchangestopMapper.getExchangeStopTotalCount(searchDto);
			}

			//공지사항 리스트 목록
			public List<ExchangeStopDto> getExchangeStopList(SearchDto searchDto) throws Exception {
				return exchangestopMapper.getExchangeStopList(searchDto);
			}


			//공지사항 등록
			  public void insertExchangeStop(String id,ExchangeStopDto exchangestopDto) throws Exception{
				  exchangestopDto.setUseYn("Y");
				  exchangestopDto.setRegId(id);
				  exchangestopMapper.insertExchangeStop(exchangestopDto);
			  }

			  //공지사항 수정읽기
			  public ExchangeStopDto readExchangeStop(long idx) throws Exception{
				  return exchangestopMapper.readExchangeStop(idx);
			  }


			  //공지사항 조회(상세보기)
			public ExchangeStopDto getExchangeStopSee(Long exchangestopIdx) throws Exception {

				return exchangestopMapper.getExchangeStopSee(exchangestopIdx);
			}

			//공지사항 수정
			public void updateExchangeStop(ExchangeStopDto exchangestopDto) throws Exception{
				exchangestopMapper.updateExchangeStop(exchangestopDto);
			}


			// 공지사항 지우기
			public void deleteExchangeStop(ExchangeStopDto exchangestopDto) throws Exception {
				exchangestopMapper.deleteExchangeStop(exchangestopDto);
			}

			//댓글 등록
			@Transactional(propagation = Propagation.REQUIRED, rollbackFor= {Exception.class})
			public void insertReply(ReplyDto replyDto, long exchangestopIdx) {
				replyDto.setExchangestopIdx(exchangestopIdx);
				exchangestopMapper.insertReply(replyDto);
			}

			//댓글 리스트
			public List<ReplyDto> readReply(long exchangestopIdx) throws Exception{
				return exchangestopMapper.readReply(exchangestopIdx);
			}

			//댓글 수정
			@Transactional(propagation=Propagation.REQUIRED, rollbackFor= {Exception.class})
			public void updateReplyByReplyIdx(ReplyDto replyDto) throws Exception{
				exchangestopMapper.updateReplyByReplyIdx(replyDto);
			}

			//댓글 삭제
			@Transactional(propagation = Propagation.REQUIRED, rollbackFor= {Exception.class})
			public void deleteReply(ReplyDto replyDto) throws Exception{
				exchangestopMapper.deleteReply(replyDto);
			}


}




