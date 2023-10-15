package com.co.kr.shop.product.dto;



import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Goods implements Serializable{
	private int goodsNo;
	private String gname;  //상품명
	private int p_num;    //개수
	private int p_price;  //가격
	private int sum_p_num;//상품개수합계
	private int sum_p_price;//전체합계
	private long sum;//한줄합계
	

	
	public Goods(int goodsNo, String gName, int p_price) {
		super();
		this.goodsNo=goodsNo;
		this.gname=gName;
		this.p_price=p_price;
	}
}
