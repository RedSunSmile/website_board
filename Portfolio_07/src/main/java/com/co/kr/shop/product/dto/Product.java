package com.co.kr.shop.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@NoArgsConstructor
@Builder
public class Product {
	private String id;
	private String name;
	private String photo;
	private double price;
	
	
	public Product(String id, String name, String photo, double price) {
		this.id=id;
		this.name=name;
		this.photo=photo;
		this.price=price;
	}
}
