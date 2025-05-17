CREATE TABLE IF NOT EXISTS endpoint_hits (
    id SERIAL PRIMARY KEY,
    app LONG NOT NULL,
    uri VARCHAR(255) NOT NULL,
    ip VARCHAR(255) NOT NULL,
    act_time timestamp NOT NULL
);

CREATE TABLE IF NOT EXISTS apps (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT fk_app_endpoint FOREIGN KEY (id) REFERENCES endpoint_hits (app)
);