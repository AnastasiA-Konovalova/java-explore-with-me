CREATE TABLE IF NOT EXISTS categories (
   id SERIAL PRIMARY KEY,
   name VARCHAR NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS users (
   id SERIAL PRIMARY KEY,
   name VARCHAR(250) NOT NULL,
   email VARCHAR(254) NOT NULL,
   CONSTRAINT unique_email UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS locations (
    id SERIAL PRIMARY KEY,
    lat FLOAT NOT NULL,
    lon FLOAT NOT NULL
);

CREATE TABLE IF NOT EXISTS events (
    id SERIAL PRIMARY KEY,
    annotation VARCHAR(2000) NOT NULL,
    category_id BIGINT NOT NULL,
    confirmed_requests BIGINT,
    created_on TIMESTAMP,
    description VARCHAR(7000),
    event_date TIMESTAMP NOT NULL,
    initiator_id BIGINT NOT NULL,
    location_id BIGINT NOT NULL,
    paid BOOLEAN DEFAULT FALSE,
    participant_limit BIGINT,
    published_on TIMESTAMP,
    request_moderation BOOLEAN DEFAULT TRUE,
    state VARCHAR(50),
    title VARCHAR(120) NOT NULL,
    views BIGINT,
    CONSTRAINT fk_event_category_id FOREIGN KEY (category_id) REFERENCES categories (id),
    CONSTRAINT fk_event_user_id FOREIGN KEY (initiator_id) REFERENCES users (id),
    CONSTRAINT fk_event_location_id FOREIGN KEY (location_id) REFERENCES locations (id)
);

