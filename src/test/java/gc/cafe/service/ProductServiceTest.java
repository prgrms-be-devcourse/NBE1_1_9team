package gc.cafe.service;

import gc.cafe.product.domain.Product;
import gc.cafe.product.dto.*;
import gc.cafe.product.repository.ProductRepository;
import gc.cafe.product.service.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    private Product savedProduct;

    @BeforeEach
    void setUp() {
        ProductAddRequest request = new ProductAddRequest("Coffee", "Beverage", 5000, "Delicious coffee");
        ProductResponse productResponse = productService.addProduct(request);
        savedProduct = productRepository.findById(productResponse.productId()).orElseThrow();
    }

    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("상품 추가 테스트")
    void addProductTest() {
        // given
        ProductAddRequest request = new ProductAddRequest("Tea", "Beverage", 3000, "Refreshing tea");

        // when
        ProductResponse response = productService.addProduct(request);

        // then
        assertThat(productRepository.findById(response.productId()))
                .isPresent()
                .get()
                .extracting(Product::getProductName)
                .isEqualTo("Tea");
    }

    @Test
    @DisplayName("모든 상품 조회 테스트")
    void getAllProductTest() {
        // when
        ProductListResponse response = productService.getAllProduct();

        // then
        assertThat(response.result())
                .isNotEmpty()
                .hasSize(1)
                .extracting(ProductSimpleResponse::name)
                .containsExactly("Coffee");
    }

    @Test
    @DisplayName("상품 상세 조회 테스트")
    void getProductDetailTest() {
        // when
        ProductDetailResponse response = productService.getProductDetail(savedProduct.getId());

        // then
        assertThat(response)
                .extracting(ProductDetailResponse::name, ProductDetailResponse::category, ProductDetailResponse::price, ProductDetailResponse::description)
                .containsExactly("Coffee", "Beverage", 5000, "Delicious coffee");
    }

    @Test
    @DisplayName("상품 수정 테스트")
    void updateProductTest() {
        // given
        ProductUpdateRequest updateRequest = new ProductUpdateRequest("Latte", "Beverage", 6000, "Creamy latte");

        // when
        ProductResponse response = productService.updateProduct(savedProduct.getId(), updateRequest);

        // then
        assertThat(productRepository.findById(response.productId()))
                .isPresent()
                .get()
                .extracting(Product::getProductName, Product::getPrice, Product::getDescription)
                .containsExactly("Latte", 6000, "Creamy latte");
    }

    @Test
    @DisplayName("상품 삭제 테스트")
    void deleteProductTest() {
        // when
        productService.deleteProduct(savedProduct.getId());

        // then
        assertThat(productRepository.findById(savedProduct.getId())).isEmpty();
    }

    @Test
    @DisplayName("존재하지 않는 상품 조회 시 예외 테스트")
    void getProductNotFoundTest() {
        // when & then
        Long invalidProductId = -1L;

        assertThatThrownBy(() -> productService.getProductDetail(invalidProductId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 메뉴를 찾을 수 없습니다.");
    }
}
