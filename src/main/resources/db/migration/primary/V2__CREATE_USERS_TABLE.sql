CREATE TABLE users (
                       id UUID PRIMARY KEY,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255),
                       google_id VARCHAR(255),
                       insert_date TIMESTAMP WITH TIME ZONE,
                       update_date TIMESTAMP WITH TIME ZONE,
                       version BIGINT,
                       order_unique BIGINT
);

CREATE INDEX idx_users_email ON users (email);