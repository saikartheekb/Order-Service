package com.assignment.orderservice.Dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CustomerDto {

    private String name;
    private String email;
}
