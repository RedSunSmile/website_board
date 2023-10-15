package com.co.kr.admin;

import java.util.Map;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.co.kr.admin.dto.AdminDto;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequestMapping("/admin")
@Controller
public class AdminController {




		  @Autowired
		private AdminService adminService;

		/*
		 * @GetMapping(value="/index") 
		 * public String loadPage() throws Exception{
		 * 
		 * return "admin/main"; }
		 */

		@GetMapping(value="/index")
		public ModelAndView  main() throws Exception{
			ModelAndView modelAndView=new ModelAndView();
			modelAndView.setViewName("admin/index");
			return modelAndView;
		}

		//login폼만들기
		@GetMapping(value="/adminlogin")
		public String login() {

			return "admin/adminlogin";
		}
		//로그인만들기
		@PostMapping(value="/adminlogin")
		public String login(AdminDto adminDto, String name,RedirectAttributes attr, HttpSession session) throws Exception{
			Map<String, Object> map=adminService.adminlogin(adminDto);

			boolean canLogin=(boolean)map.get("canLogin");
			if(!canLogin) {
				attr.addFlashAttribute("msg", map.get("msg"));
				return "redirect:/admin/adminlogin";
			}

				session.setAttribute("admin", map.get("admin"));
				return "redirect:/admin/index";
		}

		@RequestMapping(value="/logout")
		public String logout(HttpSession session) {
			session.removeAttribute("admin");
			return "admin/index";
		}

	
}
