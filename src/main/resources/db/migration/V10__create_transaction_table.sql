create TABLE IF NOT EXISTS transactions(
    id              UUID NOT NULL PRIMARY KEY,
    inventory_id    UUID REFERENCES inventories(id),
    wine_id         UUID REFERENCES wines(id),
    user_id         UUID REFERENCES users(id),
    event           TEXT NOT NULL,
    created_at      TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
)