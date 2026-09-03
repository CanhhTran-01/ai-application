-- Extension cần thiết
CREATE EXTENSION IF NOT EXISTS vector;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Bảng lưu embedding
CREATE TABLE IF NOT EXISTS vector_store (
    id uuid DEFAULT uuid_generate_v4() NOT NULL,
    content text,
    metadata json,
    embedding vector(768),
    CONSTRAINT vector_store_pkey PRIMARY KEY (id)
);

-- Index HNSW
CREATE INDEX IF NOT EXISTS vector_store_embedding_hnsw_idx
ON vector_store
USING hnsw (embedding vector_cosine_ops);