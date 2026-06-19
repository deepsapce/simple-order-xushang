package com.example.simpleorder.service;

import com.example.simpleorder.entity.Product;
import com.example.simpleorder.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.simpleorder.mapper.OrderInfoMapper;
import com.example.simpleorder.entity.OrderInfo;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderInfoService {
    private final OrderInfoMapper orderInfoMapper;
    private final ProductMapper productMapper;

    public OrderInfo getOrderInfoById(Long id) {
        return orderInfoMapper.selectById(id);
    }

    // 异常回滚，超时回滚，时间随便定的
    @Transactional(rollbackFor = Exception.class, timeout = 20)
    public OrderInfo createOrderInfo(Long productId, int quantity) {
        // 悲观锁查询，在mapper中实现sql行锁
        Product product = productMapper.selectByIdForUpdate(productId);
        if (product == null) {
            throw new RuntimeException("没有");
        }
        // 检查库存够不够
        if (quantity > product.getStock()) {
            throw new RuntimeException("不够");
        }
        // 减少库存并更新数据库
        product.setStock(product.getStock() - quantity);
        productMapper.updateById(product);
        //创建订单
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setProductId(productId);
        orderInfo.setQuantity(quantity);
        orderInfo.setTotal_price(product.getPrice().multiply(new BigDecimal(quantity)));
        orderInfo.setStatus("CREATED");
        orderInfoMapper.insert(orderInfo);
        return orderInfo;
    }
}
