package com.co.kr.shop.product;

import java.util.ArrayList;
import java.util.List;

import com.co.kr.shop.product.dto.Product;

public class ProductModel {
	
	private List<Product> products;

	public ProductModel() {
		this.products = new ArrayList<Product>();
		this.products.add(new Product("p01", "name 1", "ADSB-05-2020.jpg", 20));
		this.products.add(new Product("p02", "name 2", "adsb-07-001.jpg", 21));
		this.products.add(new Product("p03", "name 3", "adsb-09-200.jpg", 22));
	}

	public List<Product> findAll() {
		return this.products;
	}

	public Product find(String id) {
		for (Product product : this.products) {
			if (product.getId().equalsIgnoreCase(id)) {
				return product;
			}
		}
		return null;
	}

}
