package gc.cafe.api.controller.product;

import gc.cafe.ControllerTestSupport;
import gc.cafe.api.controller.product.request.ProductCreateRequest;
import gc.cafe.api.service.product.request.ProductCreateServiceRequest;
import gc.cafe.api.service.product.response.ProductResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductControllerTest extends ControllerTestSupport {

    @DisplayName("신규 상품을 등록한다.")
    @Test
    void createProduct() throws Exception {
        ProductCreateRequest request = ProductCreateRequest.builder()
            .name("스타벅스 원두")
            .category("원두")
            .price(50000L)
            .description("에티오피아산")
            .build();

        given(productService.createProduct(any(ProductCreateServiceRequest.class)))
            .willReturn(ProductResponse.builder()
                .id(1L)
                .name("스타벅스 원두")
                .category("원두")
                .price(50000L)
                .description("에티오피아산")
                .build());

        mockMvc.perform(
                post("/api/v1/products/new")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("200"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("OK"))
            .andExpect(jsonPath("$.data").isMap())
            .andExpect(jsonPath("$.data.id").isNumber())
            .andExpect(jsonPath("$.data.name").isString())
            .andExpect(jsonPath("$.data.category").isString())
            .andExpect(jsonPath("$.data.price").isNumber())
            .andExpect(jsonPath("$.data.description").isString());
    }

    @DisplayName("신규 상품을 등록 할 때 이름은 필수값이다.")
    @Test
    void createProductWithoutName() throws Exception {
        ProductCreateRequest request = ProductCreateRequest.builder()
            .category("원두")
            .price(50000L)
            .description("에티오피아산")
            .build();

        mockMvc.perform(
                post("/api/v1/products/new")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("상품명은 필수입니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 상품을 등록 할 때 이름은 최대 20자이다.")
    @Test
    void createProductWhenNameLengthIsOver20() throws Exception {
        ProductCreateRequest request = ProductCreateRequest.builder()
            .name(generateFixedLengthString(21))
            .category("원두")
            .price(50000L)
            .description("에티오피아산")
            .build();

        mockMvc.perform(
                post("/api/v1/products/new")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("상품명은 20자 이하여야 합니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 상품을 등록 할 때 이름은 최대 20자이다.")
    @Test
    void createProductWhenNameLengthIs20() throws Exception {
        ProductCreateRequest request = ProductCreateRequest.builder()
            .name(generateFixedLengthString(20))
            .category("원두")
            .price(50000L)
            .description("에티오피아산")
            .build();

        given(productService.createProduct(any(ProductCreateServiceRequest.class)))
            .willReturn(ProductResponse.builder()
                .id(1L)
                .name(generateFixedLengthString(20))
                .category("원두")
                .price(50000L)
                .description("에티오피아산")
                .build());

        mockMvc.perform(
                post("/api/v1/products/new")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("200"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("OK"))
            .andExpect(jsonPath("$.data").isMap())
            .andExpect(jsonPath("$.data.id").isNumber())
            .andExpect(jsonPath("$.data.name").isString())
            .andExpect(jsonPath("$.data.category").isString())
            .andExpect(jsonPath("$.data.price").isNumber())
            .andExpect(jsonPath("$.data.description").isString());
    }

    @DisplayName("신규 상품을 등록 할 때 카테고리는 필수값이다.")
    @Test
    void createProductWithoutCategory() throws Exception {
        ProductCreateRequest request = ProductCreateRequest.builder()
            .name("스타벅스 원두")
            .price(50000L)
            .description("에티오피아산")
            .build();

        mockMvc.perform(
                post("/api/v1/products/new")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("카테고리는 필수입니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 상품을 등록 할 때 카테고리의 길이는 최대 50자이다.")
    @Test
    void createProductWhenCategoryLengthIs50() throws Exception {
        ProductCreateRequest request = ProductCreateRequest.builder()
            .name("스타벅스 원두")
            .category(generateFixedLengthString(50))
            .price(50000L)
            .description("에티오피아산")
            .build();

        given(productService.createProduct(any(ProductCreateServiceRequest.class)))
            .willReturn(ProductResponse.builder()
                .id(1L)
                .name("스타벅스 원두")
                .category(generateFixedLengthString(50))
                .price(50000L)
                .description("에티오피아산")
                .build());

        mockMvc.perform(
                post("/api/v1/products/new")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("200"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("OK"))
            .andExpect(jsonPath("$.data").isMap())
            .andExpect(jsonPath("$.data.id").isNumber())
            .andExpect(jsonPath("$.data.name").isString())
            .andExpect(jsonPath("$.data.category").isString())
            .andExpect(jsonPath("$.data.price").isNumber())
            .andExpect(jsonPath("$.data.description").isString());
    }

    @DisplayName("신규 상품을 등록 할 때 카테고리의 길이는 최대 50자이다.")
    @Test
    void createProductWhenCategoryLengthIsOver50() throws Exception {
        ProductCreateRequest request = ProductCreateRequest.builder()
            .name("스타벅스 원두")
            .category(generateFixedLengthString(51))
            .price(50000L)
            .description("에티오피아산")
            .build();

        mockMvc.perform(
                post("/api/v1/products/new")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("카테고리는 50자 이하여야 합니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 상품을 등록 할 때 상품 설명은 필수값이다.")
    @Test
    void createProductWithoutDescription() throws Exception {
        ProductCreateRequest request = ProductCreateRequest.builder()
            .name("스타벅스 원두")
            .category("원두")
            .price(50000L)
            .build();

        mockMvc.perform(
                post("/api/v1/products/new")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("상품 설명은 필수입니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 상품을 등록 할 때 상품 설명의 길이는 최대 500자이다.")
    @Test
    void createProductWhenDescriptionLengthIs500() throws Exception {
        ProductCreateRequest request = ProductCreateRequest.builder()
            .name("스타벅스 원두")
            .category("원두")
            .price(50000L)
            .description(generateFixedLengthString(500))
            .build();

        given(productService.createProduct(any(ProductCreateServiceRequest.class)))
            .willReturn(ProductResponse.builder()
                .id(1L)
                .name("스타벅스 원두")
                .category("원두")
                .price(50000L)
                .description(generateFixedLengthString(500))
                .build());

        mockMvc.perform(
                post("/api/v1/products/new")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("200"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("OK"))
            .andExpect(jsonPath("$.data").isMap())
            .andExpect(jsonPath("$.data.id").isNumber())
            .andExpect(jsonPath("$.data.name").isString())
            .andExpect(jsonPath("$.data.category").isString())
            .andExpect(jsonPath("$.data.price").isNumber())
            .andExpect(jsonPath("$.data.description").isString());
    }

    @DisplayName("신규 상품을 등록 할 때 상품 설명의 길이는 최대 500자이다")
    @Test
    void createProductWhenDescriptionLengthIsOver500() throws Exception {
        ProductCreateRequest request = ProductCreateRequest.builder()
            .name("스타벅스 원두")
            .category("원두")
            .price(50000L)
            .description(generateFixedLengthString(501))
            .build();

        mockMvc.perform(
                post("/api/v1/products/new")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("상품 설명은 500자 이하여야 합니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 상품을 등록 할 때 가격은 필수값이다.")
    @Test
    void createProductWithoutPrice() throws Exception {
        ProductCreateRequest request = ProductCreateRequest.builder()
            .name("스타벅스 원두")
            .category("원두")
            .description("에티오피아산")
            .build();

        mockMvc.perform(
                post("/api/v1/products/new")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("가격은 필수입니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 상품을 등록 할 때 상품 가격은 양수이어야 한다.")
    @Test
    void createProductWhenPriceIsOverZero() throws Exception {
        ProductCreateRequest request = ProductCreateRequest.builder()
            .name("스타벅스 원두")
            .category("원두")
            .price(1L)
            .description("에티오피아산")
            .build();

        given(productService.createProduct(any(ProductCreateServiceRequest.class)))
            .willReturn(ProductResponse.builder()
                .id(1L)
                .name("스타벅스 원두")
                .category("원두")
                .price(1L)
                .description(generateFixedLengthString(500))
                .build());

        mockMvc.perform(
                post("/api/v1/products/new")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("200"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("OK"))
            .andExpect(jsonPath("$.data").isMap())
            .andExpect(jsonPath("$.data.id").isNumber())
            .andExpect(jsonPath("$.data.name").isString())
            .andExpect(jsonPath("$.data.category").isString())
            .andExpect(jsonPath("$.data.price").isNumber())
            .andExpect(jsonPath("$.data.description").isString());
    }

    @DisplayName("신규 상품을 등록 할 때 상품 가격은 0이 아니어야 한다.")
    @Test
    void createProductWhenPriceIsZero() throws Exception {
        ProductCreateRequest request = ProductCreateRequest.builder()
            .name("스타벅스 원두")
            .category("원두")
            .price(0L)
            .description("에티오피아산")
            .build();

        mockMvc.perform(
                post("/api/v1/products/new")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("가격은 양수이어야 합니다."))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    private static String generateFixedLengthString(int length) {
        return "나".repeat(length);
    }

}