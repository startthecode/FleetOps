-- V10__vendor_driver_schema.sql
-- Schema for vendors and drivers.
-- Generated from VendorEntity / DriverEntity.
--
-- Both are JOINED-inheritance subclasses of UserEntity, so each shares its
-- primary key with users(id) and adds the embedded UserChild fields
-- (height_in_inch, body_weight, have_disease). Drivers also add drive_rating
-- and have_driving_licence. Neither needs its own sequence.

-- ── vendors ───────────────────────────────────────────────────────────────────
CREATE TABLE vendors (
    id              BIGINT   NOT NULL,
    height_in_inch  INTEGER  NOT NULL,
    body_weight     INTEGER  NOT NULL,
    have_disease    BOOLEAN  NOT NULL,
    CONSTRAINT pk_vendors PRIMARY KEY (id),
    CONSTRAINT fk_vendors_user FOREIGN KEY (id) REFERENCES users (id)
);

-- ── drivers ───────────────────────────────────────────────────────────────────
CREATE TABLE drivers (
    id                   BIGINT   NOT NULL,
    height_in_inch       INTEGER  NOT NULL,
    body_weight          INTEGER  NOT NULL,
    have_disease         BOOLEAN  NOT NULL,
    drive_rating         INTEGER  NOT NULL,
    have_driving_licence BOOLEAN  NOT NULL,
    CONSTRAINT pk_drivers PRIMARY KEY (id),
    CONSTRAINT fk_drivers_user FOREIGN KEY (id) REFERENCES users (id)
);
