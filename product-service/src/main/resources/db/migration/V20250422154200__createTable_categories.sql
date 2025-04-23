CREATE TABLE IF NOT EXISTS categories
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    is_active   BOOLEAN DEFAULT TRUE,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON TABLE categories IS 'Table of product categories';
COMMENT ON COLUMN categories.id IS 'Primary key (category ID)';
COMMENT ON COLUMN categories.name IS 'Name of the product category';
COMMENT ON COLUMN categories.description IS 'Description of the product category';
COMMENT ON COLUMN categories.is_active IS 'Is the category active';
COMMENT ON COLUMN categories.created_at IS 'Timestamp of category creation';
COMMENT ON COLUMN categories.updated_at IS 'Timestamp of last update';