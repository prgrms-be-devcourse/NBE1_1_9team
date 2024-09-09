package gc.cafe.model.service;

import gc.cafe.controller.dto.req.OrderReqDto;
import gc.cafe.controller.dto.req.OrderItemReqDto;
import gc.cafe.controller.dto.res.OrderResDto;
import gc.cafe.model.entity.OrderItems;
import gc.cafe.model.entity.OrderStatus;
import gc.cafe.model.entity.Orders;
import gc.cafe.model.entity.Products;
import gc.cafe.model.repository.*;
import gc.cafe.util.uuid.UuidUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final BulkOrderItemJpaRepository bulkRepository;
    private final SelectOrdersRepository selectOrdersRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public String addOrders(OrderReqDto dto) {
        Orders savedOrder = orderRepository.save(OrderReqDto.createWithNewId(dto));
        List<OrderItems> orderItemsList = convertToEntityList(dto.getOrderItems(), savedOrder);
        bulkRepository.saveAll(orderItemsList);
        return "주문 완료: 승인까지 기다려주세요(오후 2시에 일괄 처리 예정)";
    }

    private List<OrderItems> convertToEntityList(List<OrderItemReqDto> orderItemsDtos, Orders order) {
        Map<String, Products> productsMap = getProductsMap(orderItemsDtos);

        return orderItemsDtos.stream()
                    .map(dto -> OrderItemReqDto.createEntity(dto, productsMap.get(dto.getId()), order))
                    .collect(Collectors.toList());
    }

    private Map<String, Products> getProductsMap(List<OrderItemReqDto> orderItemsDtos) {
        List<byte[]> itemIds = getProductsIds(orderItemsDtos);
        List<Products> allByIdIn = productRepository.findAllByIdIn(itemIds);

        return allByIdIn.stream()
                .collect(Collectors.toMap(products -> UuidUtils.convertBytesToString(products.getId()),
                        products -> products));
    }

    private List<byte[]> getProductsIds(List<OrderItemReqDto> orderItemsDtos) {
        return orderItemsDtos.stream()
                .map(OrderItemReqDto::getId)
                .map(UuidUtils::convertStringToBytes)
                .toList();
    }

    @Override
    @Transactional
    public void approveOrders() {
        orderRepository.updateAllByStatusRequestedEquals(OrderStatus.APPROVED);
    }

    @Override
    public List<OrderResDto> getMyOrder(String email) {
        return selectOrdersRepository.findOrdersByEmail(email);
    }

    @Override
    public String cancelOrder(String id) {
        byte[] entityId = UuidUtils.convertStringToBytes(id);
        Orders findOrder = orderRepository.findById(entityId)
                .orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다."));

        if(findOrder.getStatus().equals(OrderStatus.APPROVED)) {
            throw new IllegalArgumentException("승인 완료된 주문은 취소가 불가능합니다");
        }

        orderRepository.delete(findOrder);
        return "주문이 취소되었습니다";
    }
}
