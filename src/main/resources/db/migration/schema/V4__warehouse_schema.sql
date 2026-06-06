-- V4__warehouse_schema.sql
-- Schema for warehouse.
-- Generated from WarehouseEntity.

-- ── Sequences (allocationSize = 10 -> INCREMENT BY 10) ───────────────────────
CREATE SEQUENCE warehouse_id_generator
    START WITH 1
    INCREMENT BY 10;

-- ── warehouse ─────────────────────────────────────────────────────────────────
CREATE TABLE warehouse (
    warehouse_id    BIGINT       NOT NULL DEFAULT nextval('warehouse_id_generator'),
    warehouse_name  VARCHAR(60)  NOT NULL,
    city_id  BIGINT       NOT NULL,
    CONSTRAINT pk_warehouse PRIMARY KEY (warehouse_id),
    CONSTRAINT uq_warehouse_warehouse_name UNIQUE (warehouse_name),
    CONSTRAINT fk_warehouse_city FOREIGN KEY (city_id) REFERENCES cities (city_id)
);
