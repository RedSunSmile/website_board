package com.co.kr.fix;

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

import com.co.kr.fix.dto.FixDto;
import com.co.kr.fix.dto.ReplyDto;
import com.co.kr.fix.dto.SearchDto;
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
@RequestMapping("/fix")
@Controller
public class FixController {

	@Autowired
	private FixService fixService;
	@GetMapping("/list")
		public String fixList(
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

			int totalCount =fixService.getFixTotalCount(searchDto);
			// 마리아 db에서는 -1 해줘야 함
			Page page = new Page(nowPage, totalCount, listCount, pageGroup);
			searchDto.setStartNum(page.getStartNum()-1);
			searchDto.setEndNum(page.getEndNum());

			List<FixDto> fixList =fixService.getFixList(searchDto);

			model.addAttribute("fixList", fixList);	//	목록
			model.addAttribute("page", page.getPageInfo());	//	페이징 관련 데이터 > 계산식
			model.addAttribute("search", searchDto);		//	파라미터


			return "fix";
		}
	//불만사항 등록
	@GetMapping("/fixReg")
	public void fixReg() {
		System.out.println("fixreg");
	}

	//불만사항 등록 Post
	@PostMapping("/fixReg")
	public String insertfix(FixDto fixDto, HttpSession session) throws Exception{
		String memberId=SessionUtil.getAdminId(session);
		log.info("{}", fixDto);
		fixService.insertFix(memberId, fixDto);
		return "fix/fixSaveProc";
	}
	//불만사항 읽기+댓글 상세보기
	@GetMapping("/fixRead")
	public ModelAndView getFixSee(
		@ModelAttribute SearchDto searchDto
		, @RequestParam (value="fixIdx") long fixIdx
		, ModelAndView mav
			)throws Exception {

		//데이터 조회
		FixDto fixDto=fixService.getFixSee(fixIdx);
		List<ReplyDto> replyList=fixService.readReply(fixIdx);

		log.info("fixsee :{}", searchDto);
		log.info("fixIdx : {}", fixIdx);

		//여부 확인 없으면 404>>>//null==null(true)
		//불만사항 사용 안하는지 확인
		if((fixDto==null) || !"Y".equals(fixDto.getUseYn())) {
			throw new CustomRestValidException(ErrorCode.PAGE_NOT_FOUND);
		}

		mav.addObject("reply", replyList);
		mav.addObject("search", searchDto);
		mav.addObject("fix", fixDto);
		mav.setViewName("fix/fixRead");

		return mav;
	}

//	// fix/modifyForm
//	//불만사항 수정 작성폼
	@PostMapping(value="/fixModifyForm")
	public String fixUpdate(
			@ModelAttribute SearchDto searchDto
			, @RequestParam(value="fixIdx") long fixIdx
			, HttpSession session
			, Model model, FixDto Idx) throws Exception{

			log.info("modify form param : {}", searchDto);
			log.info("fixIdx : {}", fixIdx);

			//데이터 조회
			FixDto fixDto=fixService.getFixSee(fixIdx);

			if((fixDto==null) || "N".equals(fixDto.getUseYn())) {
				throw new Exception("해당하는 페이지가 없습니다");
			}

			//작성하고 세션이 일치한지 확인
			log.info("modify from fixDto:{}", fixDto);

//			if(!SessionUtil.getAdminId(session).equals(fixDto.getRegId())) {
//				throw new Exception("작성자와 일치하지 않습니다");
//			}

			model.addAttribute("search", searchDto);
			model.addAttribute("fix", fixDto);

			return "fix/fixModifyForm";
	}
	//불만사항 수정>post/put
	@PostMapping("/fixModifyProc")
	public String updateFix(
			HttpSession session
			, @ModelAttribute FixDto fixDto
			) throws Exception{

		FixDto selectedDto=fixService.getFixSee(fixDto.getIdx());

		//여부 확인 없으면 404>>> 다시말해 선택된애들이 서로 null=null로 같으면 된다(true)의미있다
		if(selectedDto==null) {
			throw new Exception("해당하는 페이지가 없습니다");
		}

		//작성하고 세션이 일치한지 확인
		log.info("modify from fixDto:{}", selectedDto);
//		if(!SessionUtil.getAdminId(session).equals(selectedDto.getRegId())) {
//			throw new Exception("작성자와 일치하지 않습니다");
//		}

		fixDto.setModId(SessionUtil.getAdminId(session));
		fixDto.setModDt(LocalDateTime.now().toString());
		fixService.updateFix(fixDto);
		return "fix/fixUpdateProc";
	}

	@GetMapping("/fixModifyProc")
	public String updateFix() throws Exception{
		return "fix/fixUpdateProc";
	}

	//불만사항 삭제>post/delete
	@DeleteMapping("/fixDeleteProc")
	@ResponseBody
	public ResponseEntity deleteFix(
			@RequestBody FixDto fixDto
			, HttpSession session
			) throws Exception{
		log.info("delete fix: {}", fixDto);

		FixDto selectedDto=fixService.getFixSee(fixDto.getIdx());
		log.info("selectDto : {}", selectedDto);

		//여부 확인 없으면 404>>>
		if((selectedDto==null) || "N".equals(fixDto.getUseYn())) {
			throw new Exception("해당하는 페이지가 없습니다");
		}

		//작성자하고 세션이 일치한지 확인
		log.info("delete fixDto:{}", selectedDto);

		fixDto.setModId(SessionUtil.getAdminId(session));
		fixDto.setModDt(LocalDateTime.now().toString());
		fixDto.setUseYn("N");

		fixService.deleteFix(fixDto);

		return ResponseEntity.ok().body(MsgDto.builder().code(200).msg("삭제에 성공하였습니다").build());
	}
	@GetMapping("fixDeleteProc")
	public String deleteFix() {
		return "fix/list";
	}
	//댓글 등록
	@PostMapping("/{fixIdx}/reply")
	@ResponseBody
	public ResponseEntity<CommonResponse> insertReply(
			@PathVariable(value="fixIdx") long fixIdx
			, @Valid @RequestBody ReplyDto replyDto, HttpSession session
			) throws Exception{
			replyDto.setRegId(SessionUtil.getAdminId(session));
			fixService.insertReply(replyDto, fixIdx);
			return ResponseEntity.ok().body(CommonResponse.of(CommonCode.INSERT));
	}

	//댓글수정
	@PutMapping("/{fixIdx}/reply/{replyIdx}")
	@ResponseBody
	public ResponseEntity<CommonResponse> updateReply(
			 @PathVariable(value="fixIdx") long fixIdx
			, @PathVariable(value="replyIdx") long replyIdx
			, @Valid @RequestBody ReplyDto replyDto
			, HttpSession session
			) throws Exception{
		replyDto.setModId(SessionUtil.getAdminId(session));
		replyDto.setModDt(LocalDateTime.now().toString());

		fixService.updateReplyByReplyIdx(replyDto);
		log.info(SessionUtil.getAdminId(session));
		System.out.println(SessionUtil.getAdminId(session));
		return ResponseEntity.ok().body(CommonResponse.of(CommonCode.UPDATE));

	}
	//댓글 삭제
	@DeleteMapping("/{fixIdx}/reply/{replyIdx}")
	@ResponseBody
	public ResponseEntity<CommonResponse> deleteReply(
			@PathVariable(value="fixIdx") long fixIdx
		,	@PathVariable(value="replyIdx") long replyIdx
		, 	@RequestBody ReplyDto replyDto, HttpSession session
			) throws Exception{
		replyDto.setUseYn("N");
		replyDto.setModId(SessionUtil.getAdminId(session));

		log.info("replyDto : {}", replyDto);
		log.info(SessionUtil.getAdminId(session));
		System.out.println(SessionUtil.getAdminId(session));

		fixService.deleteReply(replyDto);
		return ResponseEntity.ok().body(CommonResponse.of(CommonCode.DELETE));

	}


}
