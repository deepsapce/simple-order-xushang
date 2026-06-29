package com.example.simpleorder.rocketMQ;

import com.example.simpleorder.entity.OrderInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RocketMQProducer {
    private DefaultMQProducer producer;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);;

    @PostConstruct
    public void init() throws Exception {
        producer = new DefaultMQProducer("mq-producer");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();
        log.info("MQ 生产者启动");
    }

    public void SendOrderInfoMassage(OrderInfo orderInfo) {
        try{
            byte[] order = objectMapper.writeValueAsBytes(orderInfo);
            Message message = new Message("orderInfo", "order-tag",order);
            producer.send(message);
            log.info("订单消息发送成功，订单ID={}", orderInfo.getId());
        }catch (Exception e){
            log.error("订单消息发送失败");
            log.error(e.getMessage());
        }
    }

    @PreDestroy
    public void destroy() throws Exception {
        producer.shutdown();
    }
}
