package com.assignment.orderservice.Dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderRequestDto {
    private int customer_id;
    private List<OrderItemDto> items;
}