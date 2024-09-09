package gc.cafe.service;

import gc.cafe.controller.dto.common.entitydto.requestdto.OrderItemsRequestDTO;
import gc.cafe.controller.dto.common.entitydto.requestdto.OrdersRequestDTO;
import gc.cafe.controller.dto.requestdto.OrderPostRequestDTO;
import gc.cafe.entity.OrderItems;
import gc.cafe.entity.Orders;
import gc.cafe.entity.Products;
import gc.cafe.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrdersRepository ordersRepository;

    @Override
    public Orders generateOrderByDTO(OrderPostRequestDTO dto) {
        OrdersRequestDTO ordersRequestDTO = dto.getOrdersRequestDTO();
        List<OrderItemsRequestDTO> orderItemsDTOs = dto.getOrderItemsDTOs();

        Orders newOrder = generateOrderFromDTO(ordersRequestDTO);
        List<OrderItems> orderItems = generateOrderItemsFromDTO(orderItemsDTOs);

        for (OrderItems orderItem : orderItems) {
            newOrder.addOrderItem(orderItem);
        }

        return newOrder;
    }

    private static Orders generateOrderFromDTO(OrdersRequestDTO ordersRequestDTO) {
        return Orders.builder()
                .email(ordersRequestDTO.getEmail())
                .address(ordersRequestDTO.getAddress())
                .postcode(ordersRequestDTO.getPostcode())
                .build();
    }

    private static List<OrderItems> generateOrderItemsFromDTO(List<OrderItemsRequestDTO> orderItemsDTOs) {
        return orderItemsDTOs.stream()
                .map(o -> OrderItems.builder()
                        .product(Products.getProductsFromRequestDTO(o.getProductsRequestDTO()))
                        .category(o.getCategory())
                        .price(o.getPrice())
                        .quantity(o.getQuantity())
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    public byte[] registerOrder(Orders order) {
        Orders savedOrder = ordersRepository.save(order);
        return savedOrder.getOrderId();
    }

    @Override
    public List<Orders> findOrdersByEmail(String email) {
        return ordersRepository.findByEmail(email);
    }

    @Override
    public Optional<Orders> findOrder(byte[] orderId) {
        return ordersRepository.findById(orderId);
    }

    @Override
    public byte[] updateOrder(byte[] beforeOrderId, Orders newOrder) {
        Orders foundOrder = ordersRepository.findById(beforeOrderId).get();
        Orders updatedOrder = foundOrder.setTo(newOrder);
        return updatedOrder.getOrderId();
    }

    @Override
    public void cancelOrder(byte[] orderId) {
        ordersRepository.deleteById(orderId);
    }

    @Override
    @Scheduled(cron = "0 0 14 * * *")
    public void processOrders() {
        List<Orders> allOrders = ordersRepository.findAll();
        Set<String> emailList = findEmails(allOrders);
        Iterator<String> targetEmail = emailList.iterator();
        while (targetEmail.hasNext()) {
            List<Orders> orders = collectOrdersByEmail(allOrders, targetEmail);
            process(orders);
        }
    }

    private Set<String> findEmails(List<Orders> allOrders) {
        HashSet<String> emails = new HashSet<>();
        for (Orders order : allOrders) {
            emails.add(order.getEmail());
        }
        return emails;
    }
    private List<Orders> collectOrdersByEmail(List<Orders> allOrders, Iterator<String> targetEmail) {
        List<Orders> orders = new ArrayList<>();
        for (Orders order : allOrders) {
            if (targetEmail.next().equals(order.getEmail())) {
                orders.add(order);
            }
        }
        return orders;
    }
    private void process(List<Orders> orders) {
        for (Orders order : orders) {
            order.process();
        }

    }
}
