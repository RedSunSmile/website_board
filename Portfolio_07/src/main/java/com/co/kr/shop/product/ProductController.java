package com.co.kr.shop.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.co.kr.shop.product.dto.Cart;
import com.co.kr.shop.product.dto.Goods;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/product")
public class ProductController {
	@Autowired
	ProductService productService;
	
	// 1. 상품 전체 목록
	@GetMapping("/productList")
	public ModelAndView list(ModelAndView mav) {
		mav.setViewName("shop/product/productList");
		mav.addObject("list", productService.listProduct());
		return mav;
	}
	// 2. 상품 상세보기
	@GetMapping("/productDetail")
	public ModelAndView detail(@PathVariable("productId") int productId, ModelAndView mav){
		mav.setViewName("/shop/product/productDetail");
		mav.addObject("vo", productService.detailProduct(productId));
		return mav;
	}

	@GetMapping("/add")
	public String Add(Model model) {
		return "shop/add";
	}
	@PostMapping(value="add")
	public String add(@ModelAttribute Goods goods,
			          @ModelAttribute("cart") List<Goods> cart) {
		cart.add(goods);
		return "redirect:/shop/product/cartDisplay";
	}
	@GetMapping("/sum")
	public String Sum(Model model) {
		return "shop/sum";
	}
	//수량 변경 메서드
	@PostMapping("/product/cartchangenum")
	public void changeNum(int cartNum, int num) {
		Map<Object, Object> cart=new HashMap<Object, Object>();
		for(Map.Entry<Object, Object> entry:cart.entrySet()) {
			if(entry.getKey()==(Object)cartNum) {
				Cart c=(Cart)entry.getValue();
				c.setP_num(num);
			}
		}
		//Cart 번호에 수량 변경
		//changeNum(1, 10);
	}
	//상품번호 삭제 메서드
	@GetMapping("/product/goodsNoDel")
	public void removeGood(int goodNum) {
		Map<Object, Object> cart=new HashMap<Object, Object>();
		Object key=null;
		for(Map.Entry<Object, Object> entry:cart.entrySet()) {
			Cart c=(Cart)entry.getValue();
			if(c.getGoods().getGoodsNo()==goodNum) {
				key=entry.getKey();
			}
		}
		//상품번호 1002  삭제
		//removeGood(1002);
		
	}
	//장바구니 진열 메서드 
	@GetMapping("/product/cartDisplay")
	public String cartDisplay(
			@ModelAttribute(value="gname") String gname, @ModelAttribute(value="p_num") int p_num,
			@ModelAttribute(value="p_price") int p_price, @ModelAttribute(value="sum") int sum) {
		Map<Object, Object> cart=new HashMap<Object, Object>();
		for(Map.Entry<Object, Object> entry:cart.entrySet()) {
			log.info("카드진열"+entry.getKey()+":");
			
			Cart c=(Cart) entry.getValue();
			log.info("상품번호:"+c.getGoods().getGoodsNo()+"상품명:"+c.getGoods().getGname()+"단가:"+c.getGoods().getP_price()+"수량:"+
			c.getP_num()+"합계:"+c.getSum());
		}
		log.info("================================================");
		//Cart 진열
		//cartDisplay();
		return "shop/product/cartDisplay";
	}
	//상품진열 메서드
	@GetMapping("/product/goodsDisplay")
	public void goodsDisplay(@RequestParam(value="goodsNo") int goodsNo,
			@RequestParam(value="gname") String gname, @RequestParam(value="p_num") int p_num,
			@RequestParam(value="p_price") int p_price, @RequestParam(value="sum") int sum) {
		Map<Object, Object> map=new HashMap<Object, Object>();
		
		for(Map.Entry<Object,Object> entry:map.entrySet()) {
			log.info("상품진열"+entry.getKey()+":");
		
		Goods goods=(Goods) entry.getValue();
		log.info("상품번호:"+goods.getGoodsNo()+"상품명:"+goods.getGname()+"수량:"+goods.getP_num()+
				"가격:"+goods.getP_price()+"합계:"+goods.getSum());
		}
		log.info("=============================================");
	}
	//상품 진열
	//goodsDisplay();
	//상품진열 메서드
		@GetMapping("/product/Page")
		public String Page() {
			return "shop/product/Page";
		}
		//상품 진열
		//goodsDisplay();
		
		@GetMapping("/index")
		public String index(ModelMap modelMap) {
			ProductModel productModel = new ProductModel();
			modelMap.put("products", productModel.findAll());
			return "product/index";
		}
		
		
}
	