CREATE TABLE IF NOT EXISTS inventory
(
    product_id    BIGINT       PRIMARY KEY,
    available_qty INTEGER      NOT NULL DEFAULT 0,
    reserved_qty  INTEGER      NOT NULL DEFAULT 0,
    updated_at    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE inventory IS 'Inventory records for each product';
COMMENT ON COLUMN inventory.product_id IS 'Product ID (linked to products.id)';
COMMENT ON COLUMN inventory.available_qty IS 'Number of units available for purchase';
COMMENT ON COLUMN inventory.reserved_qty IS 'Number of units reserved for pending orders';
COMMENT ON COLUMN inventory.updated_at IS 'Last time inventory was updated';
