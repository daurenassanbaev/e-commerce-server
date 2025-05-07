CREATE TABLE IF NOT EXISTS order_items
(
    id         BIGSERIAL PRIMARY KEY,
    order_id   BIGINT         NOT NULL REFERENCES orders (id) ON DELETE CASCADE,
    product_id BIGINT         NOT NULL,
    quantity   INTEGER        NOT NULL,
    price      DECIMAL(10, 2) NOT NULL
);

COMMENT ON TABLE order_items IS 'Table of items within orders';
COMMENT ON COLUMN order_items.id IS 'Primary key (order item ID)';
COMMENT ON COLUMN order_items.order_id IS 'Reference to order (orders.id)';
COMMENT ON COLUMN order_items.product_id IS 'Product ID (from external service)';
COMMENT ON COLUMN order_items.quantity IS 'Quantity of the product in the order';
COMMENT ON COLUMN order_items.price IS 'Price per unit at the time of order';