package com.example.simpleorder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.simpleorder.mapper.ProductMapper;
import com.example.simpleorder.entity.Product;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductMapper productMapper;

    public Product getProductById(Long id) {
        return productMapper.selectById(id);
    }
}
