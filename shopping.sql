
CREATE TABLE product_color_amount (
    id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT NOT NULL,
    color_name VARCHAR(50) NOT NULL,
    stock_amount INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE,
    UNIQUE KEY unique_product_color (product_id, color_name)
);

-- 12.18 
ALTER TABLE `shop`.`order_detail` 
ADD COLUMN `product_color` VARCHAR(100) NULL DEFAULT NULL AFTER `product_price`,
ADD COLUMN `product_size` VARCHAR(45) NULL DEFAULT NULL AFTER `product_color`;


-- 12. 19 
alter table 'shop'.'account' 
CHANGE COLUMN `account_phone` `account_phone` VARCHAR(11) NULL DEFAULT NULL ;


ALTER TABLE `shop`.`order` 
ADD COLUMN `order_status` INT NULL  DEFAULT 0  AFTER `order_date_create`;


--12.20
-- 收货人信息表 (shipping_address)
CREATE TABLE `shipping_address` (
  -- 主键
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '收货地址ID',
  
  -- 用户关联信息
  `account_id` INT NOT NULL COMMENT '用户ID',
  
  -- 收货人姓名信息
  `Recipient_name` VARCHAR(50) NOT NULL COMMENT '收货人名',
 
  -- 联系方式
  `phone` VARCHAR(11) NOT NULL COMMENT '手机号码',
  
  -- 地址信息
  `address` VARCHAR(255) NOT NULL COMMENT '所在地区',
  `address_detail` VARCHAR(500) COMMENT '详细地址(楼号/门牌号等)',

  -- 地址类型和状态
  `is_default` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否默认地址(0:否, 1:是)',

  `address_label` VARCHAR(50) COMMENT '地址标签(如:家、公司、父母家等)',
  
   -- 主键和索引
  PRIMARY KEY (`id`),
  
   -- 外键约束（假设用户表名为user）
  CONSTRAINT `fk_shipping_address_account` 
    FOREIGN KEY (`account_id`) 
    REFERENCES `account`(`account_id`) 
    ON DELETE CASCADE ON UPDATE CASCADE
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='收货地址表';


-- 订单收货人信息表 (shipping_address)
CREATE TABLE `order_shipping_address` (
  -- 主键
  `order_addr_id` INT NOT NULL AUTO_INCREMENT COMMENT '收货地址ID',
  
  -- 用户关联信息
  `order_id` INT NOT NULL COMMENT 'Order ID',
  
  -- 收货人姓名信息
  `Recipient_name` VARCHAR(50) NOT NULL COMMENT '收货人名',
 
  -- 联系方式
  `phone` VARCHAR(11) NOT NULL COMMENT '手机号码',
  
  -- 地址信息
  `address_detail` VARCHAR(500) COMMENT '详细地址(楼号/门牌号等)',

  
   -- 主键和索引
  PRIMARY KEY (`order_addr_id`),
  
   -- 外键约束（假设用户表名为user）
  CONSTRAINT `fk_shipping_address_order` 
    FOREIGN KEY (`order_id`) 
    REFERENCES `order`(`order_id`) 
    ON DELETE CASCADE ON UPDATE CASCADE
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单收货地址表';