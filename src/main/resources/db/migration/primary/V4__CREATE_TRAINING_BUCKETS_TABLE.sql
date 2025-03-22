CREATE TABLE training_buckets (
                                  id UUID PRIMARY KEY,
                                  user_id UUID NOT NULL REFERENCES users(id),
                                  s3_path VARCHAR(1024) NOT NULL,
                                  status VARCHAR(50) NOT NULL,
                                  insert_date TIMESTAMP WITH TIME ZONE,
                                  update_date TIMESTAMP WITH TIME ZONE,
                                  version BIGINT,
                                  order_unique BIGINT
);

CREATE INDEX idx_training_buckets_id ON training_buckets (id);
CREATE INDEX idx_training_buckets_user_id ON training_buckets (user_id);