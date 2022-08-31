DROP TABLE IF EXISTS wines_inventories;

CREATE TABLE IF NOT EXISTS wines_inventories(
  wine_inventory_id SERIAL PRIMARY KEY,
  count integer DEFAULT 0,
  notes TEXT,
  wine_id UUID references wines(id),
  inventory_id UUID references inventories(id),
  created_at      TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
  updated_at      TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
)