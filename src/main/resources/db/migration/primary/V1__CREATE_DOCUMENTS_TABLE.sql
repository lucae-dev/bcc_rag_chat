-- Enable pg_vector extension
CREATE EXTENSION IF NOT EXISTS vector;

-- Create documents table
CREATE TABLE documents (
                           id UUID PRIMARY KEY,
                           document TEXT NOT NULL,
                           owner_id UUID NOT NULL,
                           parent_document_id UUID NOT NULL,
                           group_id UUID NOT NULL,
                           embedding VECTOR(1536) NOT NULL,
                           insert_date TIMESTAMP WITH TIME ZONE NOT NULL,
                           update_date TIMESTAMP,
                           version INTEGER NOT NULL DEFAULT 0,
                           order_unique INTEGER NOT NULL
);

-- Indexes for filtering
CREATE INDEX idx_documents_owner_id_group_id_parent_document_id ON documents(owner_id, group_id, parent_document_id);

-- Vector index for similarity search
CREATE INDEX idx_documents_embedding ON documents USING ivfflat (embedding vector_l2_ops) WITH (lists = 100);