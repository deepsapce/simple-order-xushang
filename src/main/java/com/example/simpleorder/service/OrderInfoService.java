package com.example.simpleorder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.simpleorder.mapper.OrderInfoMapper;
import com.example.simpleorder.entity.OrderInfo;

@Service
@RequiredArgsConstructor
public class OrderInfoService {
    private final OrderInfoMapper orderInfoMapper;

    public OrderInfo getOrderInfoById(Long id) {
        return orderInfoMapper.selectById(id);
    }
}
