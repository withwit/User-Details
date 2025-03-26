DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS rides CASCADE;


CREATE TABLE IF NOT EXISTS "users" (
    id BIGSERIAL PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    create_dt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_dt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    name VARCHAR(100) NOT NULL,
    mobile_number VARCHAR(20) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    current_loc VARCHAR(100),
    rides JSONB,
    password CHAR(68) NOT NULL
);

CREATE TABLE IF NOT EXISTS "rides" (
    id BIGSERIAL PRIMARY KEY,
    passenger BIGINT,
    driver BIGINT,
    location_data VARCHAR(255)[],
    price_estimate VARCHAR(50),
    distance VARCHAR(50),
    FOREIGN KEY (passenger) REFERENCES "users"(id) ON DELETE CASCADE,
    FOREIGN KEY (driver) REFERENCES "users"(id) ON DELETE CASCADE
);

-- Trigger to update update_dt on row modification
--CREATE OR REPLACE FUNCTION update_timestamp()
--RETURNS TRIGGER AS $$
--BEGIN
--    NEW.update_dt = CURRENT_TIMESTAMP;
--    RETURN NEW;
--END;
--$$ LANGUAGE plpgsql;
--
--CREATE TRIGGER users_update_trigger
--BEFORE UPDATE ON "users"
--FOR EACH ROW EXECUTE FUNCTION update_timestamp();
