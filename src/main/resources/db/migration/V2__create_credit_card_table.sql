CREATE TABLE credit_card (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    card_number VARCHAR(16) UNIQUE NOT NULL,
    cvv VARCHAR(3) NOT NULL,
    expiration_date DATE NOT NULL,
    account_id INT NOT NULL,
    reissue_reason VARCHAR(255),
    created_at TIMESTAMP DEFAULT NOW(),
    type VARCHAR(10) NOT NULL,
    delivery_status VARCHAR(50),
    delivery_date TIMESTAMP,
    delivery_return_reason VARCHAR(255),
    delivery_address TEXT,
    next_cvv VARCHAR(3),
    next_cvv_expiration_date TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES account (id) ON DELETE CASCADE
);