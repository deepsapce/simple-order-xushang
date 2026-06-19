package com.example.simpleorder.controller;

import com.example.simpleorder.dto.OrderDto;
import com.example.simpleorder.entity.OrderInfo;
import com.example.simpleorder.service.OrderInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderInfoService orderInfoService;

    @PostMapping
    // 在这个里面可能没必要用dto，但是可能会用apifox测试
    public OrderInfo createOrder(@RequestBody OrderDto orderDto) {
        return orderInfoService.createOrderInfo(orderDto.getProductId(), orderDto.getQuantity());
    }
}
