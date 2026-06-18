package com.example.simpleorder.controller;

import com.example.simpleorder.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.simpleorder.service.ProductService;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }
}
