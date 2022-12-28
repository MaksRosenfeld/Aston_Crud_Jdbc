INSERT INTO playground(playground_name)
VALUES ('XBOX'),
       ('SONY_PLAYSTATION'),
       ('PC');

INSERT INTO game(game_name, exclusive)
VALUES ('Half-Life 2', false),
       ('Doom', true),
       ('Gears of War 3', true),
       ('Last of Us', true);

INSERT INTO play(playground_id, game_id, price, amount)
VALUES (1, 1, 20.55, 137),
       (2, 1, 25.55, 99),
       (3, 1, 21.55, 22),
       (3, 2, 19.99, 5),
       (1, 3, 15.99, 35),
       (2, 4, 15.21, 44);

