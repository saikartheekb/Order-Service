package com.assignment.orderservice.Service;

import com.assignment.orderservice.Dto.OrderRequestDto;
import com.assignment.orderservice.Dto.OrderResponseDto;
import com.assignment.orderservice.Model.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    Order createOrder(OrderRequestDto orderRequestDto);

    List<OrderResponseDto> getAllOrders();

    OrderResponseDto getOrderById(int id);
}
