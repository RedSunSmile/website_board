package com.co.kr.shop.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {
	private int p_num;    //개수
	private int p_price;  //가격
	private int sum;//한줄합계
	private Goods goods;
	
	public Cart(Goods goods, int num) {
		this.goods=goods;
		this.p_num=num;
		sum=getSum();
	}
	
	public int getSum() {
		sum=goods.getP_price()* goods.getP_num();
		return sum;
	}

}
