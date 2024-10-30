CREATE DATABASE code_kick_fc;
\c code_kick_fc

CREATE TABLE football_field
(
    field_id            SERIAL PRIMARY KEY NOT NULL,
    field_name          VARCHAR UNIQUE     NOT NULL,
    rental_price        NUMERIC(10, 2)     NOT NULL,
    max_players         SMALLINT           NOT NULL,
    field_postcode      SMALLINT           NOT NULL,
    field_city          VARCHAR            NOT NULL,
    field_street        VARCHAR            NOT NULL,
    field_street_number SMALLINT           NOT NULL
);

CREATE TABLE match
(
    match_id              SERIAL PRIMARY KEY NOT NULL,
    subscribed_players_id SMALLINT[],
    match_fee_per_player  NUMERIC(10, 2)     NOT NULL,
    field_id              SMALLINT           NOT NULL,
    match_date            TIMESTAMP          NOT NULL
);

CREATE TABLE "user"
(
    user_id       SERIAL PRIMARY KEY NOT NULL,
    username      VARCHAR UNIQUE     NOT NULL,
    first_name    VARCHAR            NOT NULL,
    last_name     VARCHAR            NOT NULL,
    user_password VARCHAR            NOT NULL,
    user_email    VARCHAR UNIQUE     NOT NULL,
    match_id      SMALLINT[]
);

CREATE TABLE user_match
(
    user_id  SMALLINT,
    match_id SMALLINT
)
