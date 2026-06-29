# simple-order

基于spring boot，创建订单的简单实现。
老师您好，如果想快速了解内容，可以查看根目录下的uml文件夹，里面有下单接口（核心内容）的时序图

- **基础框架**：Spring Boot 3.2.5（JDK 17）
- **持久层**：MyBatis-Plus 3.5.6 + MySQL 8.0
- **缓存**：Redis 7 + Spring Cache（`@Cacheable`）
- **消息队列**：Apache RocketMQ 5.1.4
- **分布式锁**：Redisson 3.27.2
- **AOP**：自定义 `@LogRecord` 注解记录操作日志
- **拦截器**：Token 校验（`HandlerInterceptor`）
- **事务**：`@Transactional` 保证库存扣减与订单创建的原子性
- **工具**：Docker Compose

## 接口

1. `/health`：检查 MySQL、Redis、RocketMQ 连通性
2. `POST /order/1`：下单减库存（事务、AOP 日志、分布式锁、RocketMQ 异步消息）

所有接口均需在 Header 中携带 `token: bingo`

### 环境准备

- JDK 17+
- Maven 3.8+
- Docker & Docker Compose

### 启动前
在根目录下的sql目录中有快速数据库设置脚本
```bash
docker-compose up -d
