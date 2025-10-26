CREATE TYPE user_role AS ENUM ('ADMIN', 'STATION_MANAGER');
CREATE TYPE wagon_type AS ENUM ('COUPE', 'SECOND_CLASS', 'COMMON');


CREATE TABLE stations (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL UNIQUE
);


CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       first_name VARCHAR(100) NOT NULL,
                       last_name VARCHAR(100) NOT NULL,
                       login VARCHAR(100) NOT NULL UNIQUE,
                       password_hash VARCHAR(255) NOT NULL,
                       role user_role NOT NULL
);


CREATE TABLE trains (
                        id BIGSERIAL PRIMARY KEY,
                        number INT NOT NULL,
                        departure_station_id BIGINT NOT NULL REFERENCES stations(id),
                        arrival_station_id BIGINT NOT NULL REFERENCES stations(id),
                        departure_time TIMESTAMP NOT NULL,
                        arrival_time TIMESTAMP NOT NULL,
                        CONSTRAINT chk_arrival_after_departure CHECK (arrival_time > departure_time)
);


CREATE TABLE wagons (
                        id BIGSERIAL PRIMARY KEY,
                        number INT NOT NULL,
                        wagon_type wagon_type NOT NULL,
                        seat_count INT NOT NULL,
                        train_id BIGINT NOT NULL REFERENCES trains(id) ON DELETE CASCADE,
                        CONSTRAINT uq_train_wagon_number UNIQUE (train_id, wagon_number)
);