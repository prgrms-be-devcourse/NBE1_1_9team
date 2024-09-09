package gc.cafe.service;

import gc.cafe.entity.Products;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    //상품 등록
    //상품 단건조회
    //상품 전체조회
    //상품 수정
    //상품 삭제
    byte[] registerProduct(Products products);   //PK(UUID) 반환
    Optional<Products> findProduct(byte[] productId);
    List<Products> findAllProducts();
    byte[] updateProduct(byte[] beforeProductId, Products newProducts);
    void removeProduct(byte[] productsId);
}