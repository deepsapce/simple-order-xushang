package com.example.simpleorder.rocketMQ;

import com.example.simpleorder.entity.OrderInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(nameServer = "127.0.0.1:9876",topic = "orderInfo", consumerGroup = "consumer")
public class RocketMQConsumer implements RocketMQListener<OrderInfo> {

    @Override
    public void onMessage(OrderInfo orderInfo) {
        log.info("id:{},产品id:{},数量{}，总价{}",orderInfo.getId(),orderInfo.getProductId(),orderInfo.getQuantity(),orderInfo.getTotal_price());
    }
}
