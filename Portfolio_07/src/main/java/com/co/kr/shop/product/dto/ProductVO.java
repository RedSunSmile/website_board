package com.co.kr.shop.product.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductVO {
	
	private int productId;
	private String productName;
	private int productPrice;
	private String productDesc;
	private String productUrl;//상품이미지경로
	private MultipartFile productPhoto;//상품이미지파일

}
