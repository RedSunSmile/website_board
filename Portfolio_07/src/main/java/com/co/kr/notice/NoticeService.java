package com.co.kr.notice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.co.kr.notice.dto.NoticeDto;
import com.co.kr.notice.dto.ReplyDto;
import com.co.kr.notice.dto.SearchDto;

@Service
public class NoticeService {
	@Autowired
	private NoticeMapper noticeMapper;

	//공지사항 전체 개수
		public int getNoticeTotalCount(SearchDto searchDto) throws Exception{
			return noticeMapper.getNoticeTotalCount(searchDto);
		}

		//공지사항 리스트 목록
		public List<NoticeDto> getNoticeList(SearchDto searchDto) throws Exception {
			return noticeMapper.getNoticeList(searchDto);
		}


		//공지사항 등록
		  public void insertNotice(String id,NoticeDto noticeDto) throws Exception{
			  noticeDto.setUseYn("Y");
			  noticeDto.setRegId(id);
			  noticeMapper.insertNotice(noticeDto);
		  }

		  //공지사항 수정읽기
		  public NoticeDto readNotice(long idx) throws Exception{
			  return noticeMapper.readNotice(idx);
		  }


		  //공지사항 조회(상세보기)
		public NoticeDto getNoticeSee(Long noticeIdx) throws Exception {

			return noticeMapper.getNoticeSee(noticeIdx);
		}

		//공지사항 수정
		public void updateNotice(NoticeDto noticeDto) throws Exception{
			noticeMapper.updateNotice(noticeDto);
		}


		// 공지사항 지우기
		public void deleteNotice(NoticeDto noticeDto) throws Exception {
			noticeMapper.deleteNotice(noticeDto);
		}

		//댓글 등록
		@Transactional(propagation = Propagation.REQUIRED, rollbackFor= {Exception.class})
		public void insertReply(ReplyDto replyDto, long noticeIdx) {
			replyDto.setNoticeIdx(noticeIdx);
			noticeMapper.insertReply(replyDto);
		}

		//댓글 리스트
		public List<ReplyDto> readReply(long noticeIdx) throws Exception{
			return noticeMapper.readReply(noticeIdx);
		}

		//댓글 수정
		@Transactional(propagation=Propagation.REQUIRED, rollbackFor= {Exception.class})
		public void updateReplyByReplyIdx(ReplyDto replyDto) throws Exception{
			noticeMapper.updateReplyByReplyIdx(replyDto);
		}

		//댓글 삭제
		@Transactional(propagation = Propagation.REQUIRED, rollbackFor= {Exception.class})
		public void deleteReply(ReplyDto replyDto) throws Exception{
			noticeMapper.deleteReply(replyDto);
		}



}
