package com.example.simpleorder.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.example.simpleorder.entity.OrderInfo;
@Mapper
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {
}
