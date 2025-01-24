CREATE TABLE products (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          price DOUBLE PRECISION NOT NULL,
                          status VARCHAR(15),
                          quantity INT NOT NULL
);
