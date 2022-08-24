CREATE TABLE IF NOT EXISTS wines(
    id UUID NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    aloha_code VARCHAR(100),
    color VARCHAR(30),
    producer VARCHAR(100),
    vintage VARCHAR(6),
    grapes TEXT[],
    aromas TEXT[],
    effervescence VARCHAR(100),
    country TEXT,
    region TEXT,
    sub_region TEXT,
    farming_practices TEXT,
    body TEXT,
    photo_link VARCHAR(1000),
    food_pairing TEXT[]
);