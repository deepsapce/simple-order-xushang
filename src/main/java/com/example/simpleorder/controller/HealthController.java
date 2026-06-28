package com.example.simpleorder.controller;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${spring.rocketmq.name-server}")
    private String nameServer;

    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> result = new HashMap<>();

        // 1. 数据库测试
        try {
            Integer dbResult = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            result.put("database", "OK (result=" + dbResult + ")");
        } catch (Exception e) {
            result.put("database", "FAIL: " + e.getMessage());
        }

        // 2. Redis 测试
        try {
            redisTemplate.opsForValue().set("health-check", "hello");
            String redisValue = redisTemplate.opsForValue().get("health-check");
            result.put("redis", "OK (value=" + redisValue + ")");
        } catch (Exception e) {
            result.put("redis", "FAIL: " + e.getMessage());
        }

        // 3. RocketMQ 测试
        DefaultMQProducer producer = null;
        try {
            producer = new DefaultMQProducer("producer");
            producer.setNamesrvAddr(nameServer);
            producer.start();

            Message msg = new Message("health-topic", "TagA", "health-check-body".getBytes(StandardCharsets.UTF_8));
            producer.send(msg);
            result.put("rocketmq", "OK (message sent to health-topic)");
        } catch (Exception e) {
            result.put("rocketmq", "FAIL: " + e.getMessage());
        } finally {
            if (producer != null) {
                producer.shutdown();
            }
        }

        return result;
    }
}