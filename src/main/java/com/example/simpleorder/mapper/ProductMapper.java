package com.example.simpleorder.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.example.simpleorder.entity.Product;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {
}
