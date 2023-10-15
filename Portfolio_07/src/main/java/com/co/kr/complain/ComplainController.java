package com.co.kr.complain;

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

import com.co.kr.complain.dto.ComplainDto;
import com.co.kr.complain.dto.ReplyDto;
import com.co.kr.complain.dto.SearchDto;
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
@RequestMapping("/complain")
@Controller
public class ComplainController {

	@Autowired
	private ComplainService complainService;
	@GetMapping("/list")
		public String complainList(
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

			int totalCount = complainService.getComplainTotalCount(searchDto);
			// 마리아 db에서는 -1 해줘야 함
			Page page = new Page(nowPage, totalCount, listCount, pageGroup);
			searchDto.setStartNum(page.getStartNum()-1);
			searchDto.setEndNum(page.getEndNum());

			List<ComplainDto> complainList = complainService.getComplainList(searchDto);

			model.addAttribute("complainList", complainList);	//	목록
			model.addAttribute("page", page.getPageInfo());	//	페이징 관련 데이터 > 계산식
			model.addAttribute("search", searchDto);		//	파라미터


			return "complain";
		}
	//불만사항 등록
	@GetMapping("/complainReg")
	public void complainReg() {
		System.out.println("complain reg");
	}

	//불만사항 등록 Post
	@PostMapping("/complainReg")
	public String insertComplain(ComplainDto complainDto, HttpSession session) throws Exception{
		String memberId=SessionUtil.getAdminId(session);
		log.info("{}", complainDto);
		complainService.insertComplain(memberId, complainDto);
		return "complain/complainSaveProc";
	}
	//불만사항 읽기+댓글 상세보기
	@GetMapping("/complainRead")
	public ModelAndView getComplainSee(
		@ModelAttribute SearchDto searchDto
		, @RequestParam (value="complainIdx") long complainIdx
		, ModelAndView mav
			)throws Exception {

		//데이터 조회
		ComplainDto complainDto=complainService.getComplainSee(complainIdx);
		List<ReplyDto> replyList=complainService.readReply(complainIdx);

		log.info("complain see :{}", searchDto);
		log.info("complainIdx : {}", complainIdx);

		//여부 확인 없으면 404>>>//null==null(true)
		//불만사항 사용 안하는지 확인
		if((complainDto==null) || !"Y".equals(complainDto.getUseYn())) {
			throw new CustomRestValidException(ErrorCode.PAGE_NOT_FOUND);
		}

		mav.addObject("reply", replyList);
		mav.addObject("search", searchDto);
		mav.addObject("complain", complainDto);
		mav.setViewName("complain/complainRead");

		return mav;
	}

//	// complain/modifyForm
//	//불만사항 수정 작성폼
	@PostMapping(value="/complainModifyForm")
	public String complainUpdate(
			@ModelAttribute SearchDto searchDto
			, @RequestParam(value="complainIdx") long complainIdx
			, HttpSession session
			, Model model, ComplainDto Idx) throws Exception{

			log.info("modify form param : {}", searchDto);
			log.info("complainIdx : {}", complainIdx);

			//데이터 조회
			ComplainDto complainDto=complainService.getComplainSee(complainIdx);

			if((complainDto==null) || "N".equals(complainDto.getUseYn())) {
				throw new Exception("해당하는 페이지가 없습니다");
			}

			//작성하고 세션이 일치한지 확인
			log.info("modify from complainDto:{}", complainDto);

//			if(!SessionUtil.getAdminId(session).equals(complainDto.getRegId())) {
//				throw new Exception("작성자와 일치하지 않습니다");
//			}

			model.addAttribute("search", searchDto);
			model.addAttribute("complain", complainDto);

			return "complain/complainModifyForm";
	}
	//불만사항 수정>post/put
	@PostMapping("/complainModifyProc")
	public String updateComplain(
			HttpSession session
			, @ModelAttribute ComplainDto complainDto
			) throws Exception{

		ComplainDto selectedDto=complainService.getComplainSee(complainDto.getIdx());

		//여부 확인 없으면 404>>> 다시말해 선택된애들이 서로 null=null로 같으면 된다(true)의미있다
		if(selectedDto==null) {
			throw new Exception("해당하는 페이지가 없습니다");
		}

		//작성하고 세션이 일치한지 확인
		log.info("modify from complainDto:{}", selectedDto);
//		if(!SessionUtil.getAdminId(session).equals(selectedDto.getRegId())) {
//			throw new Exception("작성자와 일치하지 않습니다");
//		}

		complainDto.setModId(SessionUtil.getAdminId(session));
		complainDto.setModDt(LocalDateTime.now().toString());
		complainService.updateComplain(complainDto);
		return "complain/complainUpdateProc";
	}

	@GetMapping("/complainModifyProc")
	public String updateComplain() throws Exception{
		return "complain/complainUpdateProc";
	}

	//불만사항 삭제>post/delete
	@DeleteMapping("/complainDeleteProc")
	@ResponseBody
	public ResponseEntity deleteComplain(
			@RequestBody ComplainDto complainDto
			, HttpSession session
			) throws Exception{
		log.info("delete complain : {}", complainDto);

		ComplainDto selectedDto=complainService.getComplainSee(complainDto.getIdx());
		log.info("selectDto : {}", selectedDto);

		//여부 확인 없으면 404>>>
		if((selectedDto==null) || "N".equals(complainDto.getUseYn())) {
			throw new Exception("해당하는 페이지가 없습니다");
		}

		//작성자하고 세션이 일치한지 확인
		log.info("delete complainDto:{}", selectedDto);

		complainDto.setModId(SessionUtil.getAdminId(session));
		complainDto.setModDt(LocalDateTime.now().toString());
		complainDto.setUseYn("N");

		complainService.deleteComplain(complainDto);

		return ResponseEntity.ok().body(MsgDto.builder().code(200).msg("삭제에 성공하였습니다").build());
	}
	@GetMapping("complainDeleteProc")
	public String deleteComplain() {
		return "complain/list";
	}
	//댓글 등록
	@PostMapping("/{complainIdx}/reply")
	@ResponseBody
	public ResponseEntity<CommonResponse> insertReply(
			@PathVariable(value="complainIdx") long complainIdx
			, @Valid @RequestBody ReplyDto replyDto, HttpSession session
			) throws Exception{
			replyDto.setRegId(SessionUtil.getAdminId(session));
			complainService.insertReply(replyDto, complainIdx);
			return ResponseEntity.ok().body(CommonResponse.of(CommonCode.INSERT));
	}

	//댓글수정
	@PutMapping("/{complainIdx}/reply/{replyIdx}")
	@ResponseBody
	public ResponseEntity<CommonResponse> updateReply(
			 @PathVariable(value="complainIdx") long complainIdx
			, @PathVariable(value="replyIdx") long replyIdx
			, @Valid @RequestBody ReplyDto replyDto
			, HttpSession session
			) throws Exception{
		replyDto.setModId(SessionUtil.getAdminId(session));
		replyDto.setModDt(LocalDateTime.now().toString());

		complainService.updateReplyByReplyIdx(replyDto);
		log.info(SessionUtil.getAdminId(session));
		System.out.println(SessionUtil.getAdminId(session));
		return ResponseEntity.ok().body(CommonResponse.of(CommonCode.UPDATE));

	}
	//댓글 삭제
	@DeleteMapping("/{complainIdx}/reply/{replyIdx}")
	@ResponseBody
	public ResponseEntity<CommonResponse> deleteReply(
			@PathVariable(value="complainIdx") long complainIdx
		,	@PathVariable(value="replyIdx") long replyIdx
		, 	@RequestBody ReplyDto replyDto, HttpSession session
			) throws Exception{
		replyDto.setUseYn("N");
		replyDto.setModId(SessionUtil.getAdminId(session));

		log.info("replyDto : {}", replyDto);
		log.info(SessionUtil.getAdminId(session));
		System.out.println(SessionUtil.getAdminId(session));

		complainService.deleteReply(replyDto);
		return ResponseEntity.ok().body(CommonResponse.of(CommonCode.DELETE));

	}


}
