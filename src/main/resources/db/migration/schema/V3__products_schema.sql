-- V3__products_schema.sql
-- Schema for products.
-- Generated from ProductEntity.

-- ── Sequences (allocationSize = 10 -> INCREMENT BY 10) ───────────────────────
CREATE SEQUENCE product_id_generator
    START WITH 1
    INCREMENT BY 10;

-- ── products ──────────────────────────────────────────────────────────────────
CREATE TABLE products (
    product_id      BIGINT            NOT NULL DEFAULT nextval('product_id_generator'),
    product_name    VARCHAR(180)      NOT NULL,
    description     VARCHAR(280)      NOT NULL,
    price           DOUBLE PRECISION  NOT NULL,
    cost            DOUBLE PRECISION  NOT NULL,
    stock_quantity  INTEGER           NOT NULL,
    unit            INTEGER           NOT NULL,
    created_at      TIMESTAMP,
    updated_at      TIMESTAMP,
    created_by      BIGINT,
    CONSTRAINT pk_products PRIMARY KEY (product_id),
    CONSTRAINT uq_products_product_name UNIQUE (product_name),
    CONSTRAINT fk_products_created_by FOREIGN KEY (created_by) REFERENCES users (id)
);
