package gc.cafe.api.service.product;

import gc.cafe.IntegrationTestSupport;
import gc.cafe.api.service.product.request.ProductCreateServiceRequest;
import gc.cafe.api.service.product.response.ProductResponse;
import gc.cafe.domain.product.Product;
import gc.cafe.domain.product.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.*;

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
        assertThat(productResponse)
            .extracting("id","name","category","price","description")
            .containsExactlyInAnyOrder(1L,"스타벅스 원두","원두",50000L,"에티오피아산");

        assertThat(products).hasSize(1)
            .extracting("id","name","category","price","description")
            .containsExactlyInAnyOrder(
                tuple(1L,"스타벅스 원두","원두",50000L,"에티오피아산")
            );
    }



}