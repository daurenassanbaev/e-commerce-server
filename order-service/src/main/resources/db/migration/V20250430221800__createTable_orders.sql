CREATE TABLE IF NOT EXISTS orders
(
    id           BIGSERIAL PRIMARY KEY,
    user_id      BIGINT         NOT NULL,
    status       VARCHAR(50)    NOT NULL NOT NULL DEFAULT 'NEW',
    total_amount DECIMAL(10, 2) NOT NULL,
    created_at   TIMESTAMP                        DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP                        DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE orders IS 'Table of customer orders';
COMMENT ON COLUMN orders.id IS 'Primary key (order ID)';
COMMENT ON COLUMN orders.user_id IS 'User ID (from external service)';
COMMENT ON COLUMN orders.status IS 'Order status (e.g. NEW, CONFIRMED, CANCELED)';
COMMENT ON COLUMN orders.total_amount IS 'Total amount of the order';
COMMENT ON COLUMN orders.created_at IS 'Timestamp when the order was created';
COMMENT ON COLUMN orders.updated_at IS 'Timestamp when the order was last updated';