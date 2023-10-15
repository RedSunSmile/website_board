package com.co.kr.shop.product;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.co.kr.shop.product.dto.ProductVO;


@Mapper
public interface ProductMapper {
	
	// 01. 상품목록
		public List<ProductVO> listProduct();
		// 02. 상품상세
		public ProductVO detailProduct(int productId);
		// 03. 상품수정
//		public void updateProduct(ProductVO vo);
//		// 04. 상품삭제
//		public void deleteProduct(int productId);
//		// 05. 상품추가
//		public void insertProduct(ProductVO vo);
//		// 06. 상품이미지 삭제를 위한 이미지파일 정보
//		public String fileInfo(int productId);

}
