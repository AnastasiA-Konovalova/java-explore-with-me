CREATE TABLE IF NOT EXISTS categories (
   id SERIAL PRIMARY KEY,
   name VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
   id SERIAL PRIMARY KEY,
   name VARCHAR(250) NOT NULL,
   email VARCHAR(254) NOT NULL
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
    description VARCHAR(7000) NOT NULL,
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

CREATE TABLE IF NOT EXISTS requests (
    id SERIAL PRIMARY KEY,
    created TIMESTAMP NOT NULL,
    event_id BIGINT NOT NULL,
    requester_id BIGINT NOT NULL,
    status VARCHAR NOT NULL,
    CONSTRAINT fk_request_event_id FOREIGN KEY (event_id) REFERENCES events (id),
    CONSTRAINT fk_request_requester_id FOREIGN KEY (requester_id) REFERENCES users (id),
    UNIQUE (event_id, requester_id)
);

CREATE TABLE IF NOT EXISTS compilations (
    id SERIAL PRIMARY KEY,
    pinned BOOLEAN DEFAULT FALSE,
    title VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS compilation_events (
    compilation_id BIGINT NOT NULL,
    event_id BIGINT NOT NULL,
    CONSTRAINT pk_compilation_events PRIMARY KEY (compilation_id, event_id),
    CONSTRAINT fk_ce_compilation_id FOREIGN KEY (compilation_id) REFERENCES compilations (id),
    CONSTRAINT fk_ce_event_id FOREIGN KEY (event_id) REFERENCES events (id)
);

CREATE TABLE IF NOT EXISTS comments (
    id SERIAL PRIMARY KEY,
    text VARCHAR NOT NULL,
    event_id BIGINT NOT NULL,
    commentator_id BIGINT NOT NULL,
    status VARCHAR NOT NULL,
    comment_time TIMESTAMP DEFAULT now(),
    CONSTRAINT fk_comment_users FOREIGN KEY (commentator_id) REFERENCES users (id),
    CONSTRAINT fk_comment_events FOREIGN KEY (event_id) REFERENCES events (id)
);