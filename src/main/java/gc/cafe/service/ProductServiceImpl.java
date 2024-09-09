package gc.cafe.service;

import gc.cafe.entity.Products;
import gc.cafe.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductsRepository productsRepository;
    @Override
    @Transactional
    public byte[] registerProduct(Products products) {
        Products savedProduct = productsRepository.save(products);
        return savedProduct.getProductId();
    }

    @Override
    public Optional<Products> findProduct(byte[] productId) {
        return productsRepository.findById(productId);
    }

    @Override
    public List<Products> findAllProducts() {
        return productsRepository.findAll();
    }

    @Override
    @Transactional
    public byte[] updateProduct(byte[] beforeProductId, Products newProduct) {
        Products foundProduct = productsRepository.findById(beforeProductId).get();
        Products updatedProduct = foundProduct.setTo(newProduct);;
        return updatedProduct.getProductId();
    }

    @Override
    @Transactional
    public void removeProduct(byte[] productsId) {
        productsRepository.deleteById(productsId);
    }
}
