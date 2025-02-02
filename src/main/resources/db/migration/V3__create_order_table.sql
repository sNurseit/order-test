CREATE TABLE IF NOT EXISTS orders (
    id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    total_price DOUBLE PRECISION NOT NULL,
    status VARCHAR(15),
    created_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS order_products (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT REFERENCES orders(id) ON DELETE CASCADE,
    product_id BIGINT REFERENCES products(id) ON DELETE CASCADE,
    quantity INT NOT NULL CHECK (quantity > 0)
);
