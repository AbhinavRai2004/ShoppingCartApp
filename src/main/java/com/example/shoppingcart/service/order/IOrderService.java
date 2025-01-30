package com.example.shoppingcart.service.order;


import com.example.shoppingcart.dto.OrderDto;
import com.example.shoppingcart.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);
    List<OrderDto> getUserOrders(Long userId);
}
