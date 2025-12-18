
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
