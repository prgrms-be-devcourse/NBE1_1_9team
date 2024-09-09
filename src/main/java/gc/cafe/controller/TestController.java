package gc.cafe.controller;

import gc.cafe.entity.Products;
import gc.cafe.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final ProductsRepository repository;

    @GetMapping("/save")
    @ResponseBody
    public String save() {
        repository.save(Products.builder()
                .productName("coffee")
                .category("drink")
                .price(1000L)
                .description(null)
                .build());
        return "success";
    }
}
