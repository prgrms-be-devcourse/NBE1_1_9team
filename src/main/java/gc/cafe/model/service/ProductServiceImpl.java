package gc.cafe.model.service;

import gc.cafe.model.dto.ProductDto;
import gc.cafe.model.entity.Products;
import gc.cafe.model.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    @Override
    public List<ProductDto> getProductList() {
        return productRepository.findAll().stream()
                .map(Products::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public String addProduct(ProductDto dto) {
        productRepository.save(ProductDto.toEntity(dto));
        return "저장완료";
    }
}
