package com.co.kr.complain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.co.kr.complain.dto.ComplainDto;
import com.co.kr.complain.dto.ReplyDto;
import com.co.kr.complain.dto.SearchDto;

@Service
public class ComplainService {
	  @Autowired
		private ComplainMapper complainMapper;

		//불만사항 전체 개수
		public int getComplainTotalCount(SearchDto searchDto) throws Exception{
			return complainMapper.getComplainTotalCount(searchDto);
		}

		//불만사항 리스트 목록
		public List<ComplainDto> getComplainList(SearchDto searchDto) throws Exception{
			return complainMapper.getComplainList(searchDto);
		}

		//불만사항 등록
		public void insertComplain(String id, ComplainDto complainDto) throws Exception{
			complainDto.setUseYn("Y");
			complainDto.setRegId(id);
			complainMapper.insertComplain(complainDto);
		}

		//불만사항 수정읽기
		public ComplainDto readComplain(long idx) throws Exception{
			return complainMapper.readComplain(idx);
		}

		//불만사항 조회(상세보기)
		public ComplainDto getComplainSee(long complainIdx) throws Exception{
			return complainMapper.getComplainSee(complainIdx);
		}

		//불만사항 수정
		public void updateComplain(ComplainDto complainDto) throws Exception{
			complainMapper.updateComplain(complainDto);
		}

		//불만사항 지우기
		public void deleteComplain(ComplainDto complainDto) throws Exception{
			complainMapper.deleteComplain(complainDto);
		}

		//불만댓글 등록
		@Transactional(propagation=Propagation.REQUIRED, rollbackFor= {Exception.class})
		public void insertReply(ReplyDto replyDto, long complainIdx) throws Exception {
			replyDto.setComplainIdx(complainIdx);
			complainMapper.insertReply(replyDto);
		}

		//불만댓글 리스트
		public List<ReplyDto> readReply(long complainIdx) throws Exception{
			return complainMapper.readReply(complainIdx);
		}

		//불만댓글 수정
		@Transactional(propagation=Propagation.REQUIRED, rollbackFor= {Exception.class})
		public void updateReplyByReplyIdx(ReplyDto replyDto) throws Exception{
			complainMapper.updateReplyByReplyIdx(replyDto);
		}

		//불만댓글 삭제
		@Transactional(propagation=Propagation.REQUIRED, rollbackFor= {Exception.class})
		public void deleteReply(ReplyDto replyDto) throws Exception{
			complainMapper.deleteReply(replyDto);
		}
}
