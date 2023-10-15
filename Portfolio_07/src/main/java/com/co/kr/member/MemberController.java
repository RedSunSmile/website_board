package com.co.kr.member;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.co.kr.core.common.CommonCode;
import com.co.kr.core.common.CommonResponse;
import com.co.kr.error.ErrorCode;
import com.co.kr.error.exception.CustomRestValidException;
import com.co.kr.member.dto.MemberDto;
import com.co.kr.member.dto.ReplyDto;
import com.co.kr.member.dto.SearchDto;
import com.co.kr.util.MsgDto;
import com.co.kr.util.Page;
import com.co.kr.util.SessionUtil;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/member")
@Controller
public class MemberController {

	@Autowired
	private MemberService memberService;

	// 회원관리 목록
	@GetMapping("/list")
			public String memberList(
					//처음 페이지
					  @RequestParam(value="nowPage", defaultValue="1") int nowPage
					//한 페이지에 공지사항 나열 개수
					, @RequestParam(value="listCount", defaultValue="10") int listCount
					//화면에 보여줄 페이지 그룹
					, @RequestParam(value="pageGroup", defaultValue="10") int pageGroup
					//검색어
					, @RequestParam(value="searchText", defaultValue = "") String searchText
					, Model model
					) throws Exception {

				log.info("nowPage : {}, listCount : {}, pageGroup : {} ", nowPage, listCount, pageGroup);

				//공지사항의 총 갯수
				SearchDto searchDto = SearchDto.builder()
						.searchText(searchText)		//	파라미터	> 전체 갯수 조회	> 화면
						.listCount(listCount)	//	파라미터	> 목록조회
						.nowPage(nowPage)		//	파라미터					> 화면
						.pageGroup(pageGroup)	//	파라미터
						.build();

				int totalCount = memberService.getMemberTotalCount(searchDto);
				// 마리아 db에서는 -1 해줘야 함
				Page page = new Page(nowPage, totalCount, listCount, pageGroup);
				searchDto.setStartNum(page.getStartNum()-1);
				searchDto.setEndNum(page.getEndNum());

				 List<MemberDto> memberList = memberService.getMemberList(searchDto);

				model.addAttribute("memberList", memberList);	//목록
				model.addAttribute("page", page.getPageInfo());	//페이징 관련 데이터 > 계산식
				model.addAttribute("search", searchDto);		//파라미터

				return "member";
			}


	@GetMapping(value = "/index")
	public String loadPage() throws Exception {
		return "member/index";
	}
	// 로그인 폼
		@GetMapping("/login")
		public String login() {
			log.info("call login : ");
			return "member/login";
		}

		// 로그인 만들기
		@RequestMapping(value = "/login", method = RequestMethod.POST)
		public String login(MemberDto memberDto, RedirectAttributes attr, HttpSession session) throws Exception {

			Map<String, Object> map = memberService.login(memberDto);

			log.info("{}", memberDto);
			boolean canLogin = (boolean) map.get("canLogin");
			/* String roleId=memberDto.getRoleId().toString(); */
			if (!canLogin) {
				attr.addFlashAttribute("msg", map.get("msg"));
				return "redirect:/member/login";

			}

				session.setAttribute("member", map.get("member"));
				/* memberService.updateLogin(memberDto); */
				return "redirect:/member/index";
		}



	// 회원가입 폼
	@GetMapping(value="/signup")
	public String signup() {
		return "member/signup";
	}

	// 회원가입 프로세스
	@PostMapping(value="/signup")
	@ResponseBody //@ResponseBody와 @RequestBody는 하나의짝
	public ResponseEntity<CommonResponse> signup(@RequestBody MemberDto memberDto) throws Exception {
		log.info(memberDto.toString());

		memberDto.setUseYn("Y");
		memberDto.setRoleId("0");//일반사용자

		memberService.insertMember(memberDto);

		return ResponseEntity.ok().body(CommonResponse.of(CommonCode.INSERT, memberDto));
	}

	//회원 읽기+ 회원댓글 상세보기
		@GetMapping("/memberRead")
		public ModelAndView getMemberSee(
			@ModelAttribute SearchDto searchDto
			, @RequestParam (value="memberIdx") Long memberIdx
			, ModelAndView mav
			) throws Exception{

			//데이터 조회
			MemberDto memberDto=memberService.getMemberSee(memberIdx);
			List<ReplyDto> replyList=memberService.readReply(memberIdx);

			log.info("member see :{}", searchDto);
			log.info("memberIdx : {}",memberIdx);

			//여부 확인 없으면 404>>>//null==null(true)
			//	 공지사항사용 안하는지 확인
			if((memberDto==null) || !"Y".equals(memberDto.getUseYn())) {
				throw new CustomRestValidException(ErrorCode.PAGE_NOT_FOUND);
			}

			mav.addObject("reply",replyList);
			mav.addObject("search", searchDto);
			mav.addObject("member", memberDto);
			mav.setViewName("member/memberRead");


			return mav;
		}

		//  /member/modifyForm
		//회원사항 수정 작성폼
		@PostMapping(value="/memberModifyForm")
		public String memberUpdate(
				@ModelAttribute SearchDto searchDto
				, @RequestParam(value="memberIdx") Long memberIdx
				, HttpSession session
				, Model model, MemberDto Idx) throws Exception{
			log.info("modify form param : {}", searchDto);
			log.info("memberIdx : {}", memberIdx);

			//회원데이터 조회
			MemberDto memberDto=memberService.getMemberSee(memberIdx);
			//여부 확인 없으면 404에러

			if((memberDto==null) || "N".equals(memberDto.getUseYn())) {
				throw new Exception("해당하는 페이지가 없습니다");
			}
			//작성하고 세션이 일치한지 확인
			log.info("modify from memberDto:{}", memberDto);
			/*
			 * if(!SessionUtil.getMemberId(session).equals(memberDto.getId())) { throw new
			 * Exception("작성자와 일치하지 않습니다"); }
			 */


			model.addAttribute("serach", searchDto);
			model.addAttribute("member", memberDto);

			return "member/memberModifyForm";
		}

		//회원사항 수정>post/put
		@PostMapping("/memberModifyProc")
		public String updateMember(
				HttpSession session
				,	@ModelAttribute MemberDto memberDto
				) throws Exception{
			MemberDto selectedDto=memberService.getMemberSee(memberDto.getIdx());

			//여부 확인 없으면 404>>> 다시말해 선택된 애들이 서로 null=null로 같으면 된다(true)의미 있다
//			if(selectedDto==null) {
//				throw new Exception("해당하는 페이지가 없습니다");
//			}
			//작성하고 세션이 일치한지 확인//크게보자!!
			log.info("modify from memberDto:{}", selectedDto);
			/*
			 * if(!SessionUtil.getMemberId(session).equals(selectedDto.getId())) { throw new
			 * Exception("작성자와 일치하지 않습니다"
			 */

			memberDto.setModId(SessionUtil.getAdminId(session));
			memberDto.setModDt(LocalDateTime.now().toString());
			memberService.updateMember(memberDto);
			return "member/memberUpdateProc";
		}

		//회원사항 삭제>post/delete
		@DeleteMapping(value="/memberDeleteProc" )
		@ResponseBody
		public ResponseEntity deleteMember(
				@RequestBody MemberDto memberDto
				, HttpSession session
				) throws Exception{
			log.info("delete member : {}", memberDto);

			MemberDto selectedDto=memberService.getMemberSee(memberDto.getIdx());

			//여부 확인 없으면 404 >>>
//			if(selectedDto== null) {
//				throw new Exception("해당하는 페이지가 없습니다.");
//			}
//
//			if("N".equals(memberDto.getUseYn())) {
//				throw new Exception("해당하는 페이지가 없습니다");
//			}

			//작성자하고 세션이 일치한지 확인
			log.info("delete memberDto:{}", selectedDto);
			memberDto.setModId(SessionUtil.getAdminId(session));
			memberDto.setModDt(LocalDateTime.now().toString());
			memberDto.setUseYn("N");

			memberService.deleteMember(memberDto);

			return ResponseEntity.ok().body(MsgDto.builder().code(200).msg("삭제에 성공하였습니다").build());
		}

		@GetMapping(value="/memberDeleteProc" )
		@ResponseBody
		public String deleteMember() {
			return "member/list";
		}

		@GetMapping("/memberModifyProc")
		public String updateMember() {
				return "member/memberUpdateProc";
		}
//	아이디 중복 확인
	@PostMapping(value = "/idCheck")
	@ResponseBody
	public int idCheck(@RequestBody MemberDto memberDto) throws Exception {
		int result = memberService.idCheck(memberDto.getId());

		return result;
	}

//////댓글등록
		@PostMapping("/{memberIdx}/reply")
		@ResponseBody
		public ResponseEntity<CommonResponse> insertReply(
				@PathVariable(value="memberIdx") long noticeIdx
				,@Valid @RequestBody ReplyDto replyDto, HttpSession session
				) throws Exception{
			replyDto.setRegId(SessionUtil.getAdminId(session));//getUsername()아니고 getMemberId로 처리
			memberService.insertReply(replyDto, noticeIdx);
			return ResponseEntity.ok().body(CommonResponse.of(CommonCode.INSERT));
		}

				//댓글수정
		@PutMapping("/{memberIdx}/reply/{replyIdx}")
		@ResponseBody
		public ResponseEntity<CommonResponse> updateReply(
				@PathVariable(value="memberIdx") long memberIdx
			,	@PathVariable(value="replyIdx") long replyIdx
			,	@Valid @RequestBody ReplyDto replyDto
			, 	HttpSession session
				) throws Exception{
				replyDto.setModId(SessionUtil.getAdminId(session));
				replyDto.setModDt(LocalDateTime.now().toString());

				memberService.updateReplyByReplyIdx(replyDto);
				log.info(SessionUtil.getAdminId(session));
				System.out.println(SessionUtil.getAdminId(session));
				return ResponseEntity.ok().body(CommonResponse.of(CommonCode.UPDATE));

		}

		//댓글 삭제
		@DeleteMapping("/{memberIdx}/reply/{replyIdx}")
		@ResponseBody
		public ResponseEntity<CommonResponse> deleteReply(
				@PathVariable(value="memberIdx") long memberIdx
			, 	@PathVariable(value="replyIdx") long replyIdx
			,	@RequestBody ReplyDto replyDto, HttpSession session
				) throws Exception{
			replyDto.setUseYn("N");
			replyDto.setModId(SessionUtil.getAdminId(session));

			log.info("replyDto : {}", replyDto);
			log.info(SessionUtil.getAdminId(session));
			System.out.println(SessionUtil.getAdminId(session));


			memberService.deleteReply(replyDto);
			return ResponseEntity.ok().body(CommonResponse.of(CommonCode.DELETE));
		}

	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("member");
		return "redirect:/member/login";
	}


}
