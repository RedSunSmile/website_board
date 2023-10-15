package com.co.kr.fix;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import com.co.kr.fix.dto.FixDto;
import com.co.kr.fix.dto.ReplyDto;
import com.co.kr.fix.dto.SearchDto;


import lombok.extern.slf4j.Slf4j;




@Slf4j
@Service
public class FixService {


	@Autowired
	private FixMapper fixMapper;
	
	//불만사항 전체 개수
	public int getFixTotalCount(SearchDto searchDto) throws Exception{
		return fixMapper.getFixTotalCount(searchDto);
	}

	//불만사항 리스트 목록
	public List<FixDto> getFixList(SearchDto searchDto) throws Exception{
		return fixMapper.getFixList(searchDto);
	}

	//불만사항 등록
	public void insertFix(String id, FixDto fixDto) throws Exception{
		fixDto.setUseYn("Y");
		fixDto.setRegId(id);
		fixMapper.insertFix(fixDto);
	}

	//불만사항 수정읽기
	public FixDto readFix(long idx) throws Exception{
		return fixMapper.readFix(idx);
	}

	//불만사항 조회(상세보기)
	public FixDto getFixSee(long fixIdx) throws Exception{
		return fixMapper.getFixSee(fixIdx);
	}

	//불만사항 수정
	public void updateFix(FixDto fixDto) throws Exception{
		fixMapper.updateFix(fixDto);
	}

	//불만사항 지우기
	public void deleteFix(FixDto fixDto) throws Exception{
		fixMapper.deleteFix(fixDto);
	}

	//불만댓글 등록
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor= {Exception.class})
	public void insertReply(ReplyDto replyDto, long fixIdx) throws Exception {
		replyDto.setFixIdx(fixIdx);
		fixMapper.insertReply(replyDto);
	}

	//불만댓글 리스트
	public List<ReplyDto> readReply(long fixIdx) throws Exception{
		return fixMapper.readReply(fixIdx);
	}

	//불만댓글 수정
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor= {Exception.class})
	public void updateReplyByReplyIdx(ReplyDto replyDto) throws Exception{
		fixMapper.updateReplyByReplyIdx(replyDto);
	}

	//불만댓글 삭제
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor= {Exception.class})
	public void deleteReply(ReplyDto replyDto) throws Exception{
		fixMapper.deleteReply(replyDto);
	}

}




