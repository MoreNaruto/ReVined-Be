create TABLE IF NOT EXISTS refresh_tokens(
    id              SERIAL PRIMARY KEY,
    token           VARCHAR(1000) NOT NULL UNIQUE,
    expiryDate      TIMESTAMP WITH TIME ZONE,
    created_at      TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    user_id         UUID NOT NULL UNIQUE REFERENCES users(id)
)