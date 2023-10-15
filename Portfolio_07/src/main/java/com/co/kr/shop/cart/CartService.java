package com.co.kr.shop.cart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.co.kr.shop.cart.dto.CartVO;

@Service
public class CartService {
	
	@Autowired
	CartMapper cartMapper;
	
	//1.장바구니 추가
	public void insert(String cart, CartVO vo) {
		cartMapper.insert(vo);
	}

	//2.장바구니 목록
	public List<CartVO> listCart(String userId){
		return cartMapper.listCart(userId);
	}
	
	//3.장바구니 삭제
	public void delete(int cartId) {
		cartMapper.delete(cartId);
	}
	
	//4.장바구니 수정
	public void modifyCart(CartVO vo) {
		cartMapper.modifyCart(vo);
	}
	
	//5.장바구니 금액 합계
	public int sumMoney(String userId) {
		return cartMapper.sumMoney(userId);
	}
	
	//6.장바구니 동일한 상품 레코드 확인
	public int countCart(int productId, String userId) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("productId", productId);
		map.put("userId", userId);
		return cartMapper.countCart(productId, userId);
	}
	
	//7.장바구니 상품수량 변경
	public void updateCart(CartVO vo) {
		cartMapper.updateCart(vo);
	}
}
