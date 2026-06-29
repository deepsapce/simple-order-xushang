CREATE DATABASE IF NOT EXISTS simple_order DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE simple_order;

DROP TABLE IF EXISTS order_info;
DROP TABLE IF EXISTS product;

CREATE TABLE product (
                         id          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '商品ID',
                         name        VARCHAR(200)   NOT NULL COMMENT '商品名称',
                         stock       INT            NOT NULL DEFAULT 0 COMMENT '库存数量',
                         price       DECIMAL(10,2)  NOT NULL COMMENT '单价',
                         create_time DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                         update_time DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

CREATE TABLE order_info (
                            id          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '订单ID',
                            product_id  BIGINT         NOT NULL COMMENT '商品ID',
                            quantity    INT            NOT NULL COMMENT '购买数量',
                            total_price DECIMAL(10,2)  NOT NULL COMMENT '总价',
                            status      VARCHAR(50)    NOT NULL DEFAULT 'CREATED' COMMENT '订单状态',
                            create_time DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            update_time DATETIME       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            INDEX idx_product_id (product_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

INSERT INTO product (name, stock, price) VALUES ('shawn', 10000, 1.00);