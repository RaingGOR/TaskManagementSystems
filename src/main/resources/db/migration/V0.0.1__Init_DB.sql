CREATE SCHEMA IF NOT EXISTS public;

CREATE TABLE IF NOT EXISTS public.users
(
    id       BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    "name"   VARCHAR(32) UNIQUE  NOT NULL,
    password VARCHAR(32)         NOT NULL,
    email    VARCHAR(100) UNIQUE NOT NULL,
    role     VARCHAR             NOT NULL
);

CREATE TABLE IF NOT EXISTS public.tasks
(
    id          BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    title       VARCHAR                                        NOT NULL,
    description VARCHAR,
    status      VARCHAR,
    priority    VARCHAR,

    assignee    BIGINT REFERENCES users (id) ON DELETE CASCADE NOT NULL,
    author      BIGINT REFERENCES users (id) ON DELETE CASCADE NOT NULL,

    created_at  TIMESTAMP WITH TIME ZONE                       NOT NULL,
    updated_at  TIMESTAMP WITH TIME ZONE                       NOT NULL
);

CREATE TABLE IF NOT EXISTS public.comments
(
    id         BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    text       VARCHAR,

    task       BIGINT REFERENCES tasks (id) ON DELETE CASCADE NOT NULL,
    author     BIGINT REFERENCES users (id) ON DELETE CASCADE NOT NULL,

    created_at TIMESTAMP WITH TIME ZONE                       NOT NULL
)
