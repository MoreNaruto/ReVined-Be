CREATE TABLE IF NOT EXISTS companies(
    id UUID NOT NULL PRIMARY KEY,
    name TEXT,
    description TEXT
);

ALTER TABLE users ADD COLUMN company_id UUID
CONSTRAINT users_compaines_fk_user_id REFERENCES companies(id)
ON UPDATE CASCADE
ON DELETE CASCADE;

ALTER TABLE inventories ADD COLUMN company_id UUID
CONSTRAINT inventories_compaines_fk_user_id REFERENCES companies(id)
ON UPDATE CASCADE
ON DELETE CASCADE;