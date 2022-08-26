CREATE TABLE IF NOT EXISTS inventories(
    id UUID NOT NULL PRIMARY KEY,
    name TEXT,
    description TEXT,
    active BOOLEAN default true
);

CREATE TABLE IF NOT EXISTS wines_inventories(
    wine_id UUID references wines(id),
    inventory_id UUID references inventories(id),
    CONSTRAINT id PRIMARY KEY (wine_id, inventory_id)
);