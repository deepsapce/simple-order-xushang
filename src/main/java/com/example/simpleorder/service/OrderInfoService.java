package com.example.simpleorder.service;

import com.example.simpleorder.aop.LogRecord;
import com.example.simpleorder.entity.Product;
import com.example.simpleorder.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import com.example.simpleorder.mapper.OrderInfoMapper;
import com.example.simpleorder.entity.OrderInfo;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class OrderInfoService {
    private final OrderInfoMapper orderInfoMapper;
    private final ProductMapper productMapper;
    private final RedissonClient redissonClient;

    public OrderInfo getOrderInfoById(Long id) {
        return orderInfoMapper.selectById(id);
    }

    // 异常回滚，超时回滚，时间随便定的
    @Transactional(rollbackFor = Exception.class, timeout = 20)
    @LogRecord
    public OrderInfo createOrderInfo(Long productId, int quantity) {
        // 分布式锁处理商品库存
        String lockKey = "product"+productId;
        RLock rlock = redissonClient.getLock(lockKey);
        try {
            if(rlock.tryLock(5,10, TimeUnit.SECONDS)) {
                Product product = productMapper.selectById(productId);
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
                orderInfo.setCreateTime(LocalDateTime.now());
                orderInfo.setUpdateTime(LocalDateTime.now());
                orderInfoMapper.insert(orderInfo);

                // 删除缓存
                evictProductById(productId);
                return orderInfo;
            }
            else {
                throw new RuntimeException("繁忙");
            }
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
            if(rlock.isHeldByCurrentThread()) {
                rlock.unlock();
            }
        }
    }

    // 创建订单后库存变化，删除缓存
    @CacheEvict(value = "product",key = "#productId")
    public void evictProductById(Long productId) {
    }
}


