package com.co.kr.shop.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.co.kr.shop.cart.CartService;
import com.co.kr.shop.cart.dto.CartVO;
import com.co.kr.shop.product.dto.Item;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	CartService cartService;
	
	//1.장바구니 추가
	@RequestMapping("/cart/insert")
	public String insert(@ModelAttribute CartVO vo, HttpSession session) {
		String userId=(String) session.getAttribute("userId");
		vo.setUserId(userId);
		//장바구니에 기존 상품이 있는지 검사
		int count=cartService.countCart(vo.getProductId(), userId);
		if(count==0) {
			//없으면 insert
			cartService.insert(userId,vo);
		}else {
			//잇으면 update
			cartService.updateCart(vo);
		}
		return "redirect:/shop/cart/list";
	}
	
	//2.장바구니 목록
	@GetMapping("/cart/cartList")
	public ModelAndView list(HttpSession session, ModelAndView mav) {
		String userId=(String) session.getAttribute("userId");//session에 저장된 userId
		Map<String, Object> map=new HashMap<String, Object>();
		List<CartVO> list=cartService.listCart(userId);//장바구니정보
		int sumMoney=cartService.sumMoney(userId);//장바구니 전체 금액호출
		//장바구니 전체 금액에 따라 배송비 구분
		//배송료(10만원이상=>무료, 미만=>2500원
		int fee=sumMoney >=100000 ? 0 : 2500;
		map.put("list", list);//장바구니 정보를 map에 저장
		map.put("count", list.size());//장바구니 상품의 유무
		map.put("sumMoney", sumMoney);//장바구니 전체 금액
		map.put("fee", fee); //배송금액
		map.put("allSum",sumMoney+ fee);//주문 상품 전체 금액
		 mav.setViewName("/shop/cart/cartList"); 
		mav.addObject("map", map);//map변수 저장
		return mav;
	}
	//3.장바구니 삭제
	@DeleteMapping("/cart/delete")
	public String delete(@RequestParam int cartId) {
		cartService.delete(cartId);
		return "redirect:/shop/cart/list";
	}
	
	//4.장바구니 수정
	@PostMapping("/cart/update")
	public String update(@RequestParam int[] amount, @RequestParam int[] productId, HttpSession session) {
		//session의 id
		String userId=(String) session.getAttribute("userId");
		//레코드의 갯수 만큼 반복문 실행
		for(int i=0;i<productId.length;i++) {
			CartVO vo=new CartVO();
			vo.setUserId(userId);
			vo.setAmount(amount[i]);
			vo.setProductId(productId[i]);
			cartService.modifyCart(vo);
		}
		return "redirect:/shop/cart/list";
	}
	
	@GetMapping("/index")
	public String index() {
		return "cart/index";
	}

	@GetMapping("/buy/{id}")
	public String buy(@PathVariable(value="id", required=false) String id, HttpSession session) {
		ProductModel productModel = new ProductModel();
		if (session.getAttribute("cart") == null) {
			List<Item> cart = new ArrayList<Item>();
			cart.add(new Item(productModel.find(id), 1));
			session.setAttribute("cart", cart);
		} else {
			List<Item> cart = (List<Item>) session.getAttribute("cart");
			int index = this.exists(id, cart);
			if (index == -1) {
				cart.add(new Item(productModel.find(id), 1));
			} else {
				int quantity = cart.get(index).getQuantity() + 1;
				cart.get(index).setQuantity(quantity);
			}
			session.setAttribute("cart", cart);
		}
		return "redirect:/cart/index";
	}

	@GetMapping(value = "remove/{id}")
	public String remove(@PathVariable("id") String id, HttpSession session) {
		ProductModel productModel = new ProductModel();
		List<Item> cart = (List<Item>) session.getAttribute("cart");
		int index = this.exists(id, cart);
		cart.remove(index);
		session.setAttribute("cart", cart);
		return "redirect:/cart/index";
	}

	private int exists(String id, List<Item> cart) {
		for (int i = 0; i < cart.size(); i++) {
			if (cart.get(i).getProduct().getId().equalsIgnoreCase(id)) {
				return i;
			}
		}
		return -1;
	}
}
