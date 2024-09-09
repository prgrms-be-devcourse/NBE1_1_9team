package gc.cafe.service;

import gc.cafe.controller.dto.requestdto.OrderPostRequestDTO;
import gc.cafe.entity.Orders;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    //주문 생성 _주문등록신청 dto (주문 post dto)
    //주문 등록 (주문정보, 상품정보)
    //한 이메일의 전체 주문 조회
    //주문 단건 조회
    //주문 수정
    //주문 취소
    //주문 처리 _2시마다 한 이메일에 대한 모든 주문 발송처리
    Orders generateOrderByDTO(OrderPostRequestDTO dto);
    byte[] registerOrder(Orders order);
    List<Orders> findOrdersByEmail(String email);
    Optional<Orders> findOrder(byte[] orderId);
    byte[] updateOrder(byte[] beforeOrderId, Orders newOrder);
    void cancelOrder(byte[] orderId);
    void processOrders();
}
