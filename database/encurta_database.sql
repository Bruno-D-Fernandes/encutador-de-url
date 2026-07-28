CREATE DATABASE IF NOT EXISTS encurta_db;
USE encurta_db;

CREATE TABLE users_tb(
    id VARCHAR(36) NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password VARCHAR(250) NOT NULL
    credit DECIMAL(16, 2) NOT NULL DEFAULT 0.00,
    COSTRAING minumum_credit
        CHECK (credit > 0.00)
);

CREATE TABLE urls_tb (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    original_uri VARCHAR(450) NOT NULL,
    short_uri VARCHAR(450) UNIQUE NOT NULL,
    id_owner VARCHAR(36) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_user
        FOREIGN KEY (id_owner) REFERENCES users_tb(id)
    ON DELETE RESTRICT
);

