CREATE TABLE files (
                       id UUID PRIMARY KEY,
                       agent_id UUID,
                       chat_id UUID,
                       file_name VARCHAR(255) NOT NULL,
                       file_type VARCHAR(100) NOT NULL,
                       status VARCHAR(50) NOT NULL,
                       insert_date TIMESTAMP WITH TIME ZONE NOT NULL,
                       update_date TIMESTAMP WITH TIME ZONE,
                       version BIGINT NOT NULL DEFAULT 0,
                       order_unique BIGINT NOT NULL
);

CREATE INDEX idx_files_status_order_unique ON files (status, order_unique);