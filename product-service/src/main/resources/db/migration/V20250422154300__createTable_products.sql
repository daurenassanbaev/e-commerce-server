CREATE TABLE IF NOT EXISTS products
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255)   NOT NULL,
    description TEXT,
    price       DECIMAL(10, 2) NOT NULL,
    category_id BIGINT         REFERENCES categories (id) ON DELETE SET NULL,
    attributes  JSONB,
    is_active   BOOLEAN DEFAULT TRUE,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE products IS 'Table of products';
COMMENT ON COLUMN products.id IS 'Primary key (product ID)';
COMMENT ON COLUMN products.name IS 'Product name';
COMMENT ON COLUMN products.description IS 'Detailed description of the product';
COMMENT ON COLUMN products.price IS 'Price of the product';
COMMENT ON COLUMN products.category_id IS 'Reference to the category (if available)';
COMMENT ON COLUMN products.attributes IS 'JSONB column to store dynamic attributes of the product';
COMMENT ON COLUMN products.is_active IS 'Is the product active';
COMMENT ON COLUMN products.created_at IS 'Timestamp of product creation';
COMMENT ON COLUMN products.updated_at IS 'Timestamp of last update';