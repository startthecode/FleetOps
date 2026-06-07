-- V5__inventory_schema.sql
-- Schema for inventory (stock of a product, per vendor and/or warehouse).
-- Generated from InventoryEntity.

-- ── Sequences (allocationSize = 10 -> INCREMENT BY 10) ───────────────────────
CREATE SEQUENCE inventory_id_generator
    START WITH 1
    INCREMENT BY 10;

-- ── inventory ─────────────────────────────────────────────────────────────────
CREATE TABLE inventory (
    inventory_id     BIGINT    NOT NULL DEFAULT nextval('inventory_id_generator'),
    stock_vendor     INTEGER   NOT NULL,
    stock_warehouse  INTEGER   NOT NULL,
    vendor           BIGINT,
    warehouse        BIGINT,
    product          BIGINT    NOT NULL,
    CONSTRAINT pk_inventory PRIMARY KEY (inventory_id),
    CONSTRAINT uq_inventory_vendor_warehouse_product UNIQUE (vendor, warehouse, product),
    CONSTRAINT fk_inventory_vendor FOREIGN KEY (vendor) REFERENCES users (id),
    CONSTRAINT fk_inventory_warehouse FOREIGN KEY (warehouse) REFERENCES warehouse (warehouse_id),
    CONSTRAINT fk_inventory_product FOREIGN KEY (product) REFERENCES products (product_id)
);
