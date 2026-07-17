CREATE TABLE community (
   id UUID PRIMARY KEY,
   name VARCHAR(255) NOT NULL UNIQUE,
   description TEXT UNIQUE,
   avatar VARCHAR(255),
   created_at TIMESTAMPTZ,
   updated_at TIMESTAMPTZ
)