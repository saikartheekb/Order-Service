package com.assignment.orderservice.Dto;

import com.assignment.orderservice.Model.Customer;
import com.assignment.orderservice.Model.OrderItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderResponseDto {

    private int id;
    private double totalPrice;
    private LocalDateTime createdAt;
    private int customerId;
    private List<OrderItemDto> items;
}
