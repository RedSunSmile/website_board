package com.co.kr.notice;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.co.kr.notice.dto.NoticeDto;
import com.co.kr.notice.dto.ReplyDto;
import com.co.kr.notice.dto.SearchDto;

@Mapper
public interface NoticeMapper {
	//공지사항 전체 개수 파악
		public int getNoticeTotalCount(SearchDto searchDto) throws Exception;

		//공지사항 리스트 목록
		public List<NoticeDto> getNoticeList(SearchDto searchDto) throws Exception;

		//공지사항 리스트 등록
		public void insertNotice(NoticeDto noticeDto) throws Exception;

		//공지사항 수정읽기
		public NoticeDto readNotice(long idx);

		//공지사항 조회(상세보기)
		public NoticeDto getNoticeSee(Long noticeIdx) throws Exception;


		//공지사항 수정
		public void updateNotice(NoticeDto noticeDto) throws Exception;


		//공지사항 지우기
		public void deleteNotice(NoticeDto noticeDto) throws Exception;


		//댓글 등록
		public void insertReply(ReplyDto replyDto);

		//댓글 리스트
		public List<ReplyDto> readReply(long noticeIdx) throws Exception;

		//댓글 수정
		public void updateReplyByReplyIdx(ReplyDto replyDto) throws Exception;

		//댓글 삭제
		public void deleteReply(ReplyDto replyDto) throws Exception;








}
