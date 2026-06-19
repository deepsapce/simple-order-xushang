package com.example.simpleorder.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.springframework.data.auditing.CurrentDateTimeProvider;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("product")
public class Product {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer stock;
    private BigDecimal price;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
