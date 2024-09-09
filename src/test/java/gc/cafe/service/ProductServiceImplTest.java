package gc.cafe.service;

import gc.cafe.entity.Products;
import gc.cafe.repository.ProductsRepository;
import gc.cafe.util.UUIDConversion;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
@Transactional
class ProductServiceImplTest {
    @Autowired
    ProductService service;
    @Autowired
    EntityManager entityManager;
    @Autowired
    ProductsRepository repository;

    Products testProduct1 = Products.builder()
                                .productName("coffee")
                                .category("drink")
                                .price(1000L)
                                .description(null)
                                .build();

    Products testProduct2 = Products.builder()
                                .productName("tea")
                                .category("drink")
                                .price(2000L)
                                .description(null)
                                .build();
    @Test
    public void 상품등록() throws Exception{
        //given
        //when
        Products newProduct = testProduct1;
        //then
        service.registerProduct(newProduct);
    }
    @Test
    public void 상품조회() throws Exception{
        System.out.println("ProductServiceImplTest.상품조회");
        //given
        Products newProduct = testProduct1;
        repository.save(newProduct);
        entityManager.flush();
        //@CreationTimestamp 작동방식이 transaction이 끝나며 flush될때 값이 생겨 들어감
        //하지만 이 메서드는 save()하고 save()한걸 조회한 후 트랜잭션이 끝나기 때문에 (트랜잭션이 메서드단위로 실행되기 때문에)
        //save()한 다음 지접 flush()해주어야 함

        byte[] id = newProduct.getProductId();
        //when
        Products foundProduct = service.findProduct(id).get();
        printProduct(foundProduct);
        //then
        Assertions.assertThat(Arrays.equals(id, foundProduct.getProductId()));
        Assertions.assertThat(foundProduct.getCreatedAtforTest()).isNotNull();
    }
    @Test
    public void 상품전체조회() throws Exception{
        //given
        //when
        service.registerProduct(testProduct1);
        service.registerProduct(testProduct2);
        //then
        Assertions.assertThat(service.findAllProducts().size()).isEqualTo(2);
    }

    @Test
    public void 상품수정() throws Exception{
        //given
        byte[] id = service.registerProduct(testProduct1);
        Products product = service.findProduct(id).get();
        entityManager.flush();
        //when

        service.updateProduct(id, testProduct2);
        entityManager.flush();
        //then
        Products updatedProduct = service.findProduct(id).get();
        System.out.println("ProductServiceImplTest.상품수정");
        System.out.println("created at : " + updatedProduct.getCreatedAtforTest());
        System.out.println("updated at : " + updatedProduct.getUpdatedAtforTest());
        Assertions.assertThat(updatedProduct.getCreatedAtforTest()).isNotEqualTo(updatedProduct.getUpdatedAtforTest());
        Assertions.assertThat(updatedProduct.getProductNameforTest()).isEqualTo(testProduct2.getProductNameforTest());
    }
    @Test
    public void 상품삭제() throws Exception{
        //given
        byte[] id = service.registerProduct(testProduct1);
        //when
        service.removeProduct(id);
        //then
        Assertions.assertThat(service.findAllProducts()).isEmpty();
    }
    private void printProduct(Products foundProduct) {
        System.out.println("foundProduct.getProductId() = " + UUIDConversion.bytesToUUID(foundProduct.getProductId()));
        System.out.println("foundProduct.getProductNameforTest() = " + foundProduct.getProductNameforTest());
        System.out.println("foundProduct.getCreatedAtforTest() = " + foundProduct.getCreatedAtforTest());
        System.out.println("foundProduct.getUpdatedAtforTest() = " + foundProduct.getUpdatedAtforTest());
    }
}
