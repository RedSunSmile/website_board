package com.co.kr.notice;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.co.kr.core.common.CommonCode;
import com.co.kr.core.common.CommonResponse;
import com.co.kr.error.ErrorCode;
import com.co.kr.error.exception.CustomRestValidException;
import com.co.kr.notice.dto.NoticeDto;
import com.co.kr.notice.dto.ReplyDto;
import com.co.kr.notice.dto.SearchDto;
import com.co.kr.util.MsgDto;
import com.co.kr.util.Page;
import com.co.kr.util.SessionUtil;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/notice")
@Controller
public class NoticeController {

	@Autowired
	private NoticeService noticeService;
	@GetMapping("/list")
		public String noticeList(
				// 시작 페이지
				  @RequestParam(value="nowPage", defaultValue="1") int nowPage
				//공지사항 화면에 표시할 개수
				, @RequestParam(value="listCount", defaultValue="10") int listCount
				// 쪽번호 개수
				, @RequestParam(value="pageGroup", defaultValue="5") int pageGroup
				// 검색어
				, @RequestParam(value="searchText", defaultValue = "") String searchText
				, Model model
				) throws Exception {

			log.info("nowPage : {}, listCount : {}, pageGroup : {} ", nowPage, listCount, pageGroup);

			//	공지사항의 총 갯수
			SearchDto searchDto = SearchDto.builder()
					.searchText(searchText)	//	파라미터	> 전체 갯수 조회	> 화면
					.listCount(listCount)	//	파라미터	> 목록조회
					.nowPage(nowPage)		//	파라미터					> 화면
					.pageGroup(pageGroup)	//	파라미터
					.build();

			int totalCount = noticeService.getNoticeTotalCount(searchDto);
			// 마리아 db에서는 -1 해줘야 함
			Page page = new Page(nowPage, totalCount, listCount, pageGroup);
			searchDto.setStartNum(page.getStartNum()-1);
			searchDto.setEndNum(page.getEndNum());

			List<NoticeDto> noticeList = noticeService.getNoticeList(searchDto);

			model.addAttribute("noticeList", noticeList);	//	목록
			model.addAttribute("page", page.getPageInfo());	//	페이징 관련 데이터 > 계산식
			model.addAttribute("search", searchDto);		//	파라미터


			return "notice";
		}
	//공지사항 등록
	@GetMapping("/noticeReg")
	public void noticeReg() {
		System.out.println("notice reg");

	}


	//공지사항등록 Post!!
	@PostMapping("/noticeReg")
	public String insertNotice(NoticeDto noticeDto, HttpSession session)throws Exception {
		String memberId=SessionUtil.getAdminId(session);
		log.info("{}", noticeDto);
		noticeService.insertNotice(memberId, noticeDto);
		return "notice/noticeSaveProc";
	}

	//공지사항 읽기+ 댓글 상세보기
	@GetMapping("/noticeRead")
	public ModelAndView getNoticeSee(
		@ModelAttribute SearchDto searchDto
		, @RequestParam (value="noticeIdx") Long noticeIdx
		, ModelAndView mav
		) throws Exception{

		//데이터 조회
		NoticeDto noticeDto=noticeService.getNoticeSee(noticeIdx);
		List<ReplyDto> replyList=noticeService.readReply(noticeIdx);

		log.info("notice see :{}", searchDto);
		log.info("noticeIdx : {}",noticeIdx);

		//여부 확인 없으면 404>>>//null==null(true)
		//	 공지사항사용 안하는지 확인
		if((noticeDto==null) || !"Y".equals(noticeDto.getUseYn())) {
			throw new CustomRestValidException(ErrorCode.PAGE_NOT_FOUND);
		}

		mav.addObject("reply",replyList);
		mav.addObject("search", searchDto);
		mav.addObject("notice", noticeDto);
		mav.setViewName("notice/noticeRead");

			 //return "notice/noticeSee";
		return mav;
	}

	// /notice/modifyForm
	//공지사항 수정 작성폼
	@PostMapping(value="/noticeModifyForm")
	public String noticeUpdate(
				@ModelAttribute SearchDto searchDto
				, @RequestParam(value="noticeIdx") Long noticeIdx// 여기서 noticeIdx, false로 해주면 소화못한다
				, HttpSession session
				, Model model,NoticeDto Idx) throws Exception{
			log.info("modify form parm : {}", searchDto);
			log.info("noticeIdx : {}", noticeIdx);

			//데이터 조회
			NoticeDto noticeDto=noticeService.getNoticeSee(noticeIdx);
			//여부 확인 없으면 404>>>

			if((noticeDto==null) || "N".equals(noticeDto.getUseYn())) {
				throw new Exception("해당하는 페이지가 없습니다");
			}

			//작성하고 세션이 일치한지 확인
			log.info("modify from noticeDto:{}", noticeDto);
			/*
			 * if(!SessionUtil.getMemberId(session).equals(noticeDto.getRegId())) { throw
			 * new Exception("작성자와 일치하지 않습니다"); }
			 */

			model.addAttribute("serach", searchDto);
			model.addAttribute("notice", noticeDto);

			return "notice/noticeModifyForm";
	}

	//공지사항 수정>post/put
	@PostMapping("/noticeModifyProc")
	public String updateNotice(
			HttpSession session
			, @ModelAttribute NoticeDto noticeDto
			) throws Exception{

		NoticeDto selectedDto=noticeService.getNoticeSee(noticeDto.getIdx());

		//여부 확인 없으면 404>>> 다시말해선택된애들이 서로null=null로 같으면 된다(true)의미있다
		if(selectedDto==null) {
			throw new Exception("해당하는 페이지가 없습니다");
		}
		//작성하고 세션이 일치한지 확인//크게보자!!
		log.info("modify from noticeDto:{}", selectedDto);
//		if(!SessionUtil.getMemberId(session).equals(selectedDto.getRegId())) {
//				throw new Exception("작성자와 일치하지 않습니다");
//		}

		noticeDto.setModId(SessionUtil.getAdminId(session));
		noticeDto.setModDt(LocalDateTime.now().toString());
		noticeService.updateNotice(noticeDto);
		return "notice/noticeUpdateProc";
	}

	@GetMapping("/noticeModifyProc")
	public String updateNotice() throws Exception{
		return  "notice/noticeUpdateProc";
	}

	//공지사항 삭제 > post/delete
	@DeleteMapping("/noticeDeleteProc")
	@ResponseBody
	public ResponseEntity deleteNotice(
			@RequestBody NoticeDto noticeDto
			, HttpSession session
			) throws Exception{

		log.info("delete notice : {}", noticeDto);

		NoticeDto selectedDto=noticeService.getNoticeSee(noticeDto.getIdx());
		log.info("selectDto : {}", selectedDto);
		//여부 확인 없으면 404 >>>
		if(selectedDto==null) {
			throw new Exception("해당하는 페이지가 없습니다.");
		}

		if("N".equals(noticeDto.getUseYn())) {
			throw new Exception("해당하는 페이지가 없습니다");
		}
		//작성자하고 세션이 일치한지 확인
		log.info("delete noticeDto:{}", selectedDto);
//		if(!SessionUtil.getAdminId(session).equals(selectedDto.getRegId())) {
//			throw new Exception("작성자와 일치하지 않습니다.");
//		}

		noticeDto.setModId(SessionUtil.getAdminId(session));
		noticeDto.setModDt(LocalDateTime.now().toString());
		noticeDto.setUseYn("N");

		noticeService.deleteNotice(noticeDto);

		return ResponseEntity.ok().body(MsgDto.builder().code(200).msg("삭제에 성공하였습니다").build());
	}

	@GetMapping("/noticeDeleteProc")
	public String deleteNotice() {
		return "notice/list";
	}

////	//댓글등록
	@PostMapping("/{noticeIdx}/reply")
	@ResponseBody
	public ResponseEntity<CommonResponse> insertReply(
			@PathVariable(value="noticeIdx") long noticeIdx
			,@Valid @RequestBody ReplyDto replyDto, HttpSession session
			) throws Exception{
		replyDto.setRegId(SessionUtil.getAdminId(session));//getUsername()아니고 getMemberId로 처리
		noticeService.insertReply(replyDto, noticeIdx);
		return ResponseEntity.ok().body(CommonResponse.of(CommonCode.INSERT));
	}

			//댓글수정
	@PutMapping("/{noticeIdx}/reply/{replyIdx}")
	@ResponseBody
	public ResponseEntity<CommonResponse> updateReply(
			@PathVariable(value="noticeIdx") long noticeIdx
		,	@PathVariable(value="replyIdx") long replyIdx
		,	@Valid @RequestBody ReplyDto replyDto
		, 	HttpSession session
			) throws Exception{
			replyDto.setModId(SessionUtil.getAdminId(session));
			replyDto.setModDt(LocalDateTime.now().toString());

			noticeService.updateReplyByReplyIdx(replyDto);
			log.info(SessionUtil.getAdminId(session));
			System.out.println(SessionUtil.getAdminId(session));
			return ResponseEntity.ok().body(CommonResponse.of(CommonCode.UPDATE));

	}

	//댓글 삭제
	@DeleteMapping("/{noticeIdx}/reply/{replyIdx}")
	@ResponseBody
	public ResponseEntity<CommonResponse> deleteReply(
			@PathVariable(value="noticeIdx") long noticeIdx
		, 	@PathVariable(value="replyIdx") long replyIdx
		,	@RequestBody ReplyDto replyDto, HttpSession session
			) throws Exception{
		replyDto.setUseYn("N");
		replyDto.setModId(SessionUtil.getAdminId(session));

		log.info("replyDto : {}", replyDto);
		log.info(SessionUtil.getAdminId(session));
		System.out.println(SessionUtil.getAdminId(session));


		noticeService.deleteReply(replyDto);
		return ResponseEntity.ok().body(CommonResponse.of(CommonCode.DELETE));
	}

}
