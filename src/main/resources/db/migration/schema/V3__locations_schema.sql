-- V3__locations_schema.sql
-- Schema for location hierarchy: countries -> states -> cities.
-- Generated from CountryEntity / StateEntity / CityEntity.

-- ── Sequences (allocationSize = 10 -> INCREMENT BY 10) ───────────────────────
CREATE SEQUENCE country_id_generator
    START WITH 1
    INCREMENT BY 10;

CREATE SEQUENCE state_id_generator
    START WITH 1
    INCREMENT BY 10;

CREATE SEQUENCE city_id_generator
    START WITH 1
    INCREMENT BY 10;

-- ── countries ─────────────────────────────────────────────────────────────────
CREATE TABLE countries (
    country_id    BIGINT       NOT NULL DEFAULT nextval('country_id_generator'),
    country_name  VARCHAR(30)  NOT NULL,
    CONSTRAINT pk_countries PRIMARY KEY (country_id),
    CONSTRAINT uq_countries_country_name UNIQUE (country_name)
);

-- ── states ────────────────────────────────────────────────────────────────────
CREATE TABLE states (
    state_id    BIGINT       NOT NULL DEFAULT nextval('state_id_generator'),
    state_name  VARCHAR(30)  NOT NULL,
    country     BIGINT,
    CONSTRAINT pk_states PRIMARY KEY (state_id),
    CONSTRAINT uq_states_state_name UNIQUE (state_name),
    CONSTRAINT fk_states_country FOREIGN KEY (country) REFERENCES countries (country_id)
);

-- ── cities ─────────────────────────────────────────────────────────────────────
CREATE TABLE cities (
    city_id    BIGINT       NOT NULL DEFAULT nextval('city_id_generator'),
    city_name  VARCHAR(30)  NOT NULL,
    state      BIGINT,
    CONSTRAINT pk_cities PRIMARY KEY (city_id),
    CONSTRAINT uq_cities_city_name UNIQUE (city_name),
    CONSTRAINT fk_cities_state FOREIGN KEY (state) REFERENCES states (state_id)
);
