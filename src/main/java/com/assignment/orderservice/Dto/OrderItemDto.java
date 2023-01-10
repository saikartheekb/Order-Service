package com.assignment.orderservice.Dto;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderItemDto{
    private int itemId; // item id
    private int quantity;
}