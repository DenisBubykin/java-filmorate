CREATE TABLE IF NOT EXISTS rating (
    id      bigint          GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name    varchar(10)     NOT NULL,
    CONSTRAINT unique_rating_name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS genre (
    id      bigint           GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name    varchar(30)    NOT NULL,
    CONSTRAINT unique_genre_name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS film (
    id              bigint          GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name            varchar(50)     NOT NULL,
    description     varchar(200)    NOT NULL,
    release_date    date            NOT NULL,
    duration        int             NOT NULL,
    rating_id       bigint          NOT NULL,
	CONSTRAINT fk_film_rating FOREIGN KEY(rating_id) REFERENCES rating (id)
);

CREATE TABLE IF NOT EXISTS user_ (
    id          bigint          GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    email       varchar(50)     NOT NULL,
    login       varchar(50)     NOT NULL,
    name        varchar(50)     NOT NULL,
    birthday    date            NOT NULL,
    CONSTRAINT unique_user_email UNIQUE (email),
    CONSTRAINT unique_user_login UNIQUE (login)
);

CREATE TABLE IF NOT EXISTS film_user_mapping (
    film_id bigint  NOT NULL,
    user_id bigint  NOT NULL,
	CONSTRAINT fk_film_user_film FOREIGN KEY(film_id) REFERENCES film (id) ON DELETE CASCADE,
	CONSTRAINT fk_film_user_user FOREIGN KEY(user_id) REFERENCES user_ (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS friendship (
    user_id_first   bigint    NOT NULL,
    user_id_second  bigint    NOT NULL,
    is_confirmed    boolean   NOT NULL,
	CONSTRAINT fk_friendship_user_first FOREIGN KEY(user_id_first) REFERENCES user_ (id) ON DELETE CASCADE,
	CONSTRAINT fk_friendship_user_second FOREIGN KEY(user_id_second) REFERENCES user_ (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS film_genre (
    film_id     bigint  NOT NULL,
    genre_id    bigint  NOT NULL,
	CONSTRAINT fk_film_genre_film FOREIGN KEY(film_id) REFERENCES film (id) ON DELETE CASCADE,
	CONSTRAINT fk_film_genre_genre FOREIGN KEY(genre_id) REFERENCES genre (id) ON DELETE CASCADE
);