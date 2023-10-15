package com.co.kr.shop.cart.dto;

import com.co.kr.complain.dto.ComplainDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartVO {
	private int cartId;
	private String userId;
	private String userName;
	private int productId;
	private String productName;
	private int productPrice;
	private int amount;
	private int money;

}
