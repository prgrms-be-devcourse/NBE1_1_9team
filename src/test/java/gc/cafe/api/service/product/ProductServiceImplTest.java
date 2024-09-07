package gc.cafe.api.service.product;

import gc.cafe.IntegrationTestSupport;
import gc.cafe.api.service.product.request.ProductCreateServiceRequest;
import gc.cafe.api.service.product.request.ProductUpdateServiceRequest;
import gc.cafe.api.service.product.response.ProductResponse;
import gc.cafe.domain.product.Product;
import gc.cafe.domain.product.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

@Transactional
class ProductServiceImplTest extends IntegrationTestSupport {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("신규 상품을 등록한다.")
    @Test
    void createProduct() {
        //given
        ProductCreateServiceRequest request = ProductCreateServiceRequest.builder()
            .name("스타벅스 원두")
            .category("원두")
            .price(50000L)
            .description("에티오피아산")
            .build();

        //when
        ProductResponse productResponse = productService.createProduct(request);

        List<Product> products = productRepository.findAll();

        //then
        assertThat(products).hasSize(1)
            .extracting("id", "name", "category", "price", "description")
            .containsExactlyInAnyOrder(
                tuple(1L, "스타벅스 원두", "원두", 50000L, "에티오피아산")
            );

        assertThat(productResponse)
            .extracting("id", "name", "category", "price", "description")
            .containsExactlyInAnyOrder(1L, "스타벅스 원두", "원두", 50000L, "에티오피아산");

    }

    @DisplayName("상품 ID를 통해 상품에 대한 상세정보를 조회한다.")
    @Test
    void getProductByProductId() {
        //given
        Long productId = 1L;

        Product product = Product.builder()
            .name("스타벅스 원두")
            .category("원두")
            .price(50000L)
            .description("에티오피아산")
            .build();

        productRepository.save(product);

        //when
        ProductResponse productResponse = productService.getProduct(productId);

        //then
        assertThat(productResponse)
            .extracting("id", "name", "category", "price", "description")
            .containsExactlyInAnyOrder(productId, "스타벅스 원두", "원두", 50000L, "에티오피아산");

    }

    @DisplayName("상품 ID를 통해 상품에 대한 상세정보를 조회 할 때 해당 ID의 상품이 존재하지 않을 때 상품을 조회 할 수 없다.")
    @Test
    void getProductByProductIdWhenProductIsNull() {
        //given
        Long productId = 1L;

        //when
        //then
        assertThatThrownBy(() -> productService.getProduct(productId))
            .isInstanceOf(NoSuchElementException.class)
            .hasMessage("해당 id : " + productId + "를 가진 상품을 찾을 수 없습니다.");

    }

    @DisplayName("상품 ID를 통해 해당 상품을 삭제 할 수 있다.")
    @Test
    void deleteProductByProductId() {
        //given
        Long productId = 1L;

        Product product = Product.builder()
            .name("스타벅스 원두")
            .category("원두")
            .price(50000L)
            .description("에티오피아산")
            .build();

        productRepository.save(product);

        //when
        Long deletedProductId = productService.deleteProduct(productId);
        List<Product> products = productRepository.findAll();

        //then
        assertThat(products).hasSize(0);
        assertThat(deletedProductId).isEqualTo(productId);

    }

    @DisplayName("상품 ID를 통해 해당 상품을 삭제 할 때 해당 상품이 존재하지 않으면 상품을 삭제 할 수 없다.")
    @Test
    void deleteProductByProductIdWhenProductIsNull() {
        //given
        Long productId = 1L;

        //when
        //then
        assertThatThrownBy(()->productService.deleteProduct(productId))
            .isInstanceOf(NoSuchElementException.class)
            .hasMessage("해당 id : " + productId + "를 가진 상품을 찾을 수 없습니다.");
    }

    @DisplayName("상품 ID를 통해 해당 상품의 정보를 수정 할 수 있다.")
    @Test
    void updateProductByProductId() {
        //given
        Long productId = 1L;

        Product product = Product.builder()
            .name("스타벅스 원두")
            .category("원두")
            .price(50000L)
            .description("에티오피아산")
            .build();

        productRepository.save(product);

        ProductUpdateServiceRequest request = ProductUpdateServiceRequest.builder()
            .name("이디야 커피")
            .category("커피")
            .price(40000L)
            .description("국산")
            .build();

        //when
        ProductResponse response = productService.updateProduct(productId, request);

        //then
        assertThat(response)
            .extracting("id", "name", "category", "price", "description")
            .containsExactlyInAnyOrder(productId, "이디야 커피", "커피", 40000L, "국산");
    }

    @DisplayName("상품 ID를 통해 해당 상품의 정보를 수정 할 때 해당 상품이 존재하지 않으면 상품을 삭제 할 수 없다.")
    @Test
    void updateProductByProductIdWhenProductIsNull() {
        //given
        Long productId = 1L;

        ProductUpdateServiceRequest request = ProductUpdateServiceRequest.builder()
            .name("이디야 커피")
            .category("커피")
            .price(40000L)
            .description("국산")
            .build();

        //when
        //then
        assertThatThrownBy(()->productService.updateProduct(productId,request))
            .isInstanceOf(NoSuchElementException.class)
            .hasMessage("해당 id : " + productId + "를 가진 상품을 찾을 수 없습니다.");
    }

}