-- V2__init_schema.sql
-- Schema for users and users_profile.
-- Generated from UserEntity / UserProfileEntity.
-- Runs after V1 locations so users.city can reference cities inline.

-- ── Sequences (allocationSize = 10 -> INCREMENT BY 10) ───────────────────────
CREATE SEQUENCE user_id_generator
    START WITH 1
    INCREMENT BY 10;

CREATE SEQUENCE user_profile_id_generator
    START WITH 1
    INCREMENT BY 10;

-- ── users ────────────────────────────────────────────────────────────────────
CREATE TABLE users (
    id                  BIGINT       NOT NULL DEFAULT nextval('user_id_generator'),
    username            VARCHAR(30)  NOT NULL,
    email               VARCHAR(50)  NOT NULL,
    password            VARCHAR(80)  NOT NULL,
    phone               VARCHAR(12)  NOT NULL,
    is_account_blocked  BOOLEAN      NOT NULL DEFAULT FALSE,
    role                VARCHAR(20)  NOT NULL,
    city                BIGINT,
    CONSTRAINT pk_users PRIMARY KEY (id),
    CONSTRAINT uq_users_username UNIQUE (username),
    CONSTRAINT uq_users_email UNIQUE (email),
    CONSTRAINT uq_users_phone UNIQUE (phone),
    CONSTRAINT fk_users_city FOREIGN KEY (city) REFERENCES cities (city_id),
    CONSTRAINT ck_users_role CHECK (role IN
        ('SUPER_ADMIN', 'ADMIN', 'VENDOR', 'WAREHOUSE_MANAGER', 'DRIVER', 'CUSTOMER'))
);

-- ── users_profile ─────────────────────────────────────────────────────────────
CREATE TABLE users_profile (
    id               BIGINT        NOT NULL DEFAULT nextval('user_profile_id_generator'),
    name             VARCHAR(20)   NOT NULL,
    last_name        VARCHAR(20)   NOT NULL,
    profile_picture  VARCHAR(500)  NOT NULL,
    user_id          BIGINT,
    CONSTRAINT pk_users_profile PRIMARY KEY (id),
    CONSTRAINT fk_users_profile_user FOREIGN KEY (user_id) REFERENCES users (id)
);
