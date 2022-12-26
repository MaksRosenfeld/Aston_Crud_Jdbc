CREATE TABLE playground
(
    playground_id  IDENTITY NOT NULL,
    playground_name TEXT,
    PRIMARY KEY (playground_id)
);

CREATE TABLE game
(
    game_id   IDENTITY NOT NULL,
    game_name TEXT,
    exclusive boolean,
    PRIMARY KEY (game_id)
);

CREATE TABLE play
(
    play_id       IDENTITY NOT NULL,
    playground_id INTEGER,
    game_id       INTEGER,
    price         DOUBLE PRECISION,
    amount        INTEGER,
    PRIMARY KEY (play_id),
    FOREIGN KEY (playground_id) REFERENCES playground (playground_id) ON DELETE CASCADE,
    FOREIGN KEY (game_id) REFERENCES game (game_id) ON DELETE CASCADE

);

