CREATE SCHEMA IF NOT EXISTS public;

CREATE TABLE IF NOT EXISTS public.users
(
    id       BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    "name"   VARCHAR(200) UNIQUE  NOT NULL,
    password VARCHAR(200)         NOT NULL,
    email    VARCHAR(200) UNIQUE NOT NULL,
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
);

-- ТОЛЬКО ДЛЯ ТЕСТА useruser
INSERT INTO public.users (name, password, email, role)
VALUES
    ('admin_user', '$2a$10$JGHo3PFS/q8rO60qoaNr3u0nN1Miu7SzBiPn7kJmgPNuCzKBiK3mq', 'admin@example.com', 'ROLE_ADMIN'),
    ('regular_user', '$2a$10$JGHo3PFS/q8rO60qoaNr3u0nN1Miu7SzBiPn7kJmgPNuCzKBiK3mq', 'user@example.com', 'ROLE_USER');