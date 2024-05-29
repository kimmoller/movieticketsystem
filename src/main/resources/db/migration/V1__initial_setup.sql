CREATE TABLE IF NOT EXISTS identity(
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    date_of_birth TIMESTAMPTZ NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS theater(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE,
    location VARCHAR(255)
);

CREATE INDEX theater_id_location ON theater(id, location);

CREATE TABLE IF NOT EXISTS hall(
    id SERIAL PRIMARY KEY,
    number INT NOT NULL,
    theater_id INT REFERENCES theater(id) ON DELETE CASCADE
);

CREATE INDEX hall_theater_id ON hall(id, theater_id);
CREATE UNIQUE INDEX hall_theater_id_number ON hall(theater_id, number);

CREATE TABLE IF NOT EXISTS seat(
    id SERIAL PRIMARY KEY,
    row INT NOT NULL,
    number INT NOT NULL,
    hall_id INT REFERENCES hall(id)
);

CREATE INDEX seat_hall_id ON seat(id, hall_id);

CREATE TABLE IF NOT EXISTS movie(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    runtime VARCHAR(255) NOT NULL,
    description TEXT,
    genre VARCHAR(255)
);

CREATE INDEX movie_id_name ON movie(id, name);
CREATE INDEX movie_id_genre ON movie(id, genre);

CREATE TABLE IF NOT EXISTS movie_schedule(
    id SERIAL PRIMARY KEY,
    schedule TIMESTAMPTZ NOT NULL,
    movie_id INT REFERENCES movie(id),
    hall_id INT REFERENCES hall(id)
);

CREATE INDEX movie_schedule_id_schedule ON movie_schedule(id, schedule);
CREATE INDEX movie_schedule_id_movie_id ON movie_schedule(id, movie_id);
CREATE INDEX movie_schedule_id_hall_id ON movie_schedule(id, hall_id);

CREATE TABLE IF NOT EXISTS reservation(
    id SERIAL PRIMARY KEY,
    identity_id INT REFERENCES identity(id),
    movie_schedule_id INT REFERENCES movie_schedule(id),
    seat_id INT REFERENCES seat(id)
);

CREATE INDEX reservation_id_movie_schedule_id ON reservation(id, movie_schedule_id);
CREATE INDEX reservation_id_identity_id ON reservation(id, identity_id);
CREATE UNIQUE INDEX reservation_movie_schedule_seat_id ON reservation(movie_schedule_id, seat_id)
