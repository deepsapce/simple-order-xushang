package com.example.simpleorder.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("order_info")
public class OrderInfo {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long productId;
    private Integer quantity;
    private BigDecimal total_price;
    private String status;  //这里可能需要一个枚举类，取决于后续的设计
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
