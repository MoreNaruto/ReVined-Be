CREATE TABLE IF NOT EXISTS user_reset_tokens(
    id SERIAL PRIMARY KEY,
    token VARCHAR(2500) NOT NULL,
    user_id    UUID NOT NULL,
    expiration_date TIMESTAMP WITH TIME ZONE,
    CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES users(id)
)