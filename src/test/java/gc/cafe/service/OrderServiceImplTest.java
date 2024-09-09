package gc.cafe.service;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Transactional
class OrderServiceImplTest {
    @Autowired
    OrderService orderService;
    //먼저 order를 만들고, order와 product로 orderitems를 만들고, order에 orderitems세팅
    @Test
    public void 주문등록() throws Exception{
        //given

        //when

        //then
    }
    @Test
    public void 이메일로주문조회() throws Exception{
        //given

        //when

        //then
    }
    @Test
    public void 아이디로주문조회() throws Exception{
        //given

        //when

        //then
    }
    @Test
    public void 주문수정() throws Exception{
        //given

        //when

        //then
    }
    @Test
    public void 주문취소() throws Exception{
        //given

        //when

        //then
    }
    @Test
    public void 주문처리() throws Exception{
        //given

        //when

        //then
    }
}