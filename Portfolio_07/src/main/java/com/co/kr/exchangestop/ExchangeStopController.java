package com.co.kr.exchangestop;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

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

import com.co.kr.exchangestop.dto.ExchangeStopDto;
import com.co.kr.exchangestop.dto.ReplyDto;
import com.co.kr.exchangestop.dto.SearchDto;
import com.co.kr.core.common.CommonCode;
import com.co.kr.core.common.CommonResponse;
import com.co.kr.error.ErrorCode;
import com.co.kr.error.exception.CustomRestValidException;
import com.co.kr.util.MsgDto;
import com.co.kr.util.Page;
import com.co.kr.util.SessionUtil;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/exchangestop")
@Controller
public class ExchangeStopController {

	@Autowired
	private ExchangeStopService exchangestopService;
	@GetMapping("/list")
		public String exchangestopList(
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

			int totalCount = exchangestopService.getExchangeStopTotalCount(searchDto);
			// 마리아 db에서는 -1 해줘야 함
			Page page = new Page(nowPage, totalCount, listCount, pageGroup);
			searchDto.setStartNum(page.getStartNum()-1);
			searchDto.setEndNum(page.getEndNum());

			List<ExchangeStopDto> exchangestopList = exchangestopService.getExchangeStopList(searchDto);

			model.addAttribute("exchangestopList", exchangestopList);	//	목록
			model.addAttribute("page", page.getPageInfo());	//	페이징 관련 데이터 > 계산식
			model.addAttribute("search", searchDto);		//	파라미터


			return "exchangeStop";
		}
	//as사항 등록
	@GetMapping("/exchangestopReg")
	public void ExchangeStopReg() {
		System.out.println("ExchangeStop reg");
	}

	//as사항 등록 Post
	@PostMapping("/exchangestopReg")
	public String insertExchangeStop(ExchangeStopDto exchangestopDto, HttpSession session) throws Exception{
		String memberId=SessionUtil.getAdminId(session);
		log.info("{}", exchangestopDto);
		exchangestopService.insertExchangeStop(memberId, exchangestopDto);
		return "exchangestop/exchangestopSaveProc";
	}
	//as사항 읽기+댓글 상세보기
	@GetMapping("/exchangestopRead")
	public ModelAndView getExchangeStopSee(
		@ModelAttribute SearchDto searchDto
		, @RequestParam (value="exchangestopIdx") long exchangestopIdx
		, ModelAndView mav
			)throws Exception {

		//데이터 조회
		ExchangeStopDto exchangestopDto=exchangestopService.getExchangeStopSee(exchangestopIdx);
		List<ReplyDto> replyList=exchangestopService.readReply(exchangestopIdx);

		log.info("ExchangeStop see :{}", searchDto);
		log.info("ExchangeStopIdx : {}", exchangestopIdx);

		//여부 확인 없으면 404>>>//null==null(true)
		//as사항 사용 안하는지 확인
		if((exchangestopDto==null) || !"Y".equals(exchangestopDto.getUseYn())) {
			throw new CustomRestValidException(ErrorCode.PAGE_NOT_FOUND);
		}

		mav.addObject("reply", replyList);
		mav.addObject("search", searchDto);
		mav.addObject("exchangestop", exchangestopDto);
		mav.setViewName("exchangestop/exchangestopRead");

		return mav;
	}

//	// exchangestop/modifyForm
//	//as사항 수정 작성폼
	@PostMapping(value="/exchangestopModifyForm")
	public String exchangestopUpdate(
			@ModelAttribute SearchDto searchDto
			, @RequestParam(value="exchangestopIdx") long exchangestopIdx
			, HttpSession session
			, Model model, ExchangeStopDto Idx) throws Exception{

			log.info("modify form param : {}", searchDto);
			log.info("exchangestopIdx : {}", exchangestopIdx);

			//데이터 조회
			ExchangeStopDto exchangestopDto=exchangestopService.getExchangeStopSee(exchangestopIdx);

			if((exchangestopDto==null) || "N".equals(exchangestopDto.getUseYn())) {
				throw new Exception("해당하는 페이지가 없습니다");
			}

			//작성하고 세션이 일치한지 확인
			log.info("modify from ExchangeStopDto:{}", exchangestopDto);

//			if(!SessionUtil.getAdminId(session).equals(ExchangeStopDto.getRegId())) {
//				throw new Exception("작성자와 일치하지 않습니다");
//			}

			model.addAttribute("search", searchDto);
			model.addAttribute("exchangestop", exchangestopDto);

			return "exchangestop/exchangestopModifyForm";
	}
	//불만사항 수정>post/put
	@PostMapping("/exchangestopModifyProc")
	public String updateExchangeStop(
			HttpSession session
			, @ModelAttribute ExchangeStopDto exchangestopDto
			) throws Exception{

		ExchangeStopDto selectedDto=exchangestopService.getExchangeStopSee(exchangestopDto.getIdx());

		//여부 확인 없으면 404>>> 다시말해 선택된애들이 서로 null=null로 같으면 된다(true)의미있다
		if(selectedDto==null) {
			throw new Exception("해당하는 페이지가 없습니다");
		}

		//작성하고 세션이 일치한지 확인
		log.info("modify from ExchangeStopDto:{}", selectedDto);
//		if(!SessionUtil.getAdminId(session).equals(selectedDto.getRegId())) {
//			throw new Exception("작성자와 일치하지 않습니다");
//		}

		exchangestopDto.setModId(SessionUtil.getAdminId(session));
		exchangestopDto.setModDt(LocalDateTime.now().toString());
		exchangestopService.updateExchangeStop(exchangestopDto);
		return "exchangestop/exchangestopUpdateProc";
	}

	@GetMapping("/exchangestopModifyProc")
	public String updateExchangeStop() throws Exception{
		return "exchangestop/exchangestopUpdateProc";
	}

	//불만사항 삭제>post/delete
	@DeleteMapping("/exchangestopDeleteProc")
	@ResponseBody
	public ResponseEntity deleteExchangeStop(
			@RequestBody ExchangeStopDto exchangestopDto
			, HttpSession session
			) throws Exception{
		log.info("delete exchangestop : {}", exchangestopDto);

		ExchangeStopDto selectedDto=exchangestopService.getExchangeStopSee(exchangestopDto.getIdx());
		log.info("selectDto : {}", selectedDto);

		//여부 확인 없으면 404>>>
		if((selectedDto==null) || "N".equals(exchangestopDto.getUseYn())) {
			throw new Exception("해당하는 페이지가 없습니다");
		}

		//작성자하고 세션이 일치한지 확인
		log.info("delete ExchangeStopDto:{}", selectedDto);

		exchangestopDto.setModId(SessionUtil.getAdminId(session));
		exchangestopDto.setModDt(LocalDateTime.now().toString());
		exchangestopDto.setUseYn("N");

		exchangestopService.deleteExchangeStop(exchangestopDto);

		return ResponseEntity.ok().body(MsgDto.builder().code(200).msg("삭제에 성공하였습니다").build());
	}
	@GetMapping("exchangestopDeleteProc")
	public String deleteexchangestop() {
		return "exchangestop/list";
	}
	//댓글 등록
	@PostMapping("/{exchangestopIdx}/reply")
	@ResponseBody
	public ResponseEntity<CommonResponse> insertReply(
			@PathVariable(value="exchangestopIdx") long exchangestopIdx
			, @Valid @RequestBody ReplyDto replyDto, HttpSession session
			) throws Exception{
			replyDto.setRegId(SessionUtil.getAdminId(session));
			exchangestopService.insertReply(replyDto, exchangestopIdx);
			return ResponseEntity.ok().body(CommonResponse.of(CommonCode.INSERT));
	}

	//댓글수정
	@PutMapping("/{exchangestopIdx}/reply/{replyIdx}")
	@ResponseBody
	public ResponseEntity<CommonResponse> updateReply(
			 @PathVariable(value="exchangestopIdx") long exchangestopIdx
			, @PathVariable(value="replyIdx") long replyIdx
			, @Valid @RequestBody ReplyDto replyDto
			, HttpSession session
			) throws Exception{
		replyDto.setModId(SessionUtil.getAdminId(session));
		replyDto.setModDt(LocalDateTime.now().toString());

		exchangestopService.updateReplyByReplyIdx(replyDto);
		log.info(SessionUtil.getAdminId(session));
		System.out.println(SessionUtil.getAdminId(session));
		return ResponseEntity.ok().body(CommonResponse.of(CommonCode.UPDATE));

	}
	//댓글 삭제
	@DeleteMapping("/{exchangestopIdx}/reply/{replyIdx}")
	@ResponseBody
	public ResponseEntity<CommonResponse> deleteReply(
			@PathVariable(value="exchangestopIdx") long exchangestopIdx
		,	@PathVariable(value="replyIdx") long replyIdx
		, 	@RequestBody ReplyDto replyDto, HttpSession session
			) throws Exception{
		replyDto.setUseYn("N");
		replyDto.setModId(SessionUtil.getAdminId(session));

		log.info("replyDto : {}", replyDto);
		log.info(SessionUtil.getAdminId(session));
		System.out.println(SessionUtil.getAdminId(session));

		exchangestopService.deleteReply(replyDto);
		return ResponseEntity.ok().body(CommonResponse.of(CommonCode.DELETE));

	}


}
