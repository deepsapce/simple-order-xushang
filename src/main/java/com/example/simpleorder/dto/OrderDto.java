package com.example.simpleorder.dto;

import lombok.Data;

@Data
public class OrderDto {
    private Long productId;
    private int quantity;
}
