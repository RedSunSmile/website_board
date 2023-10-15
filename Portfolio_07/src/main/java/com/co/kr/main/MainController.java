package com.co.kr.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.co.kr.admin.AdminController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MainController {
	
	@GetMapping(value="/gallery/spring")
	public String  spring() throws Exception{
		return 	"gallery/spring";
	}

	@GetMapping(value="/gallery/summer")
	public String  summer() throws Exception{
		return 	"gallery/summer";
	}

	@GetMapping(value="/product/best")
	public String  best() throws Exception{
		return 	"product/best";
	}
	
	@GetMapping(value="/product/new")
	public String hot() throws Exception{
		return 	"product/new";
	}
	
	@GetMapping(value="/product/chair")
	public String chair() throws Exception{
		return 	"product/chair";
	}
	
	@GetMapping(value="/pergola/pergola")
	public String pergola() throws Exception{
		return 	"pergola/pergola";
	}
	
	@GetMapping(value="/product/playground")
	public String playground() throws Exception{
		return 	"product/playground";
	}
	
	@GetMapping(value="/product/box")
	public String box() throws Exception{
		return 	"product/box";
	}
	
	@GetMapping(value="/product/tree")
	public String tree() throws Exception{
		return 	"product/tree";
	}

	@GetMapping(value="/category/totalproduct")
	public String totalproduct() throws Exception{
		return 	"category/totalproduct";
	}

}
