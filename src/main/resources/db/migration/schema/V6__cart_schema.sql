-- V6__cart_schema.sql
-- Schema for cart and cart_items (a user's cart and its line items).
-- Generated from CartEntity / CartItemsEntity.

-- ── Sequences (no allocationSize on entity -> Hibernate default 50) ───────────
CREATE SEQUENCE cart_id_generator
    START WITH 1
    INCREMENT BY 50;

CREATE SEQUENCE cart_items_id_generator
    START WITH 1
    INCREMENT BY 50;

-- ── cart ──────────────────────────────────────────────────────────────────────
CREATE TABLE cart (
    id            BIGINT            NOT NULL DEFAULT nextval('cart_id_generator'),
    "user"        BIGINT            NOT NULL,
    total_amount  DOUBLE PRECISION  NOT NULL,
    total_items   INTEGER           NOT NULL,
    created_at    TIMESTAMP,
    updated_at    TIMESTAMP,
    CONSTRAINT pk_cart PRIMARY KEY (id),
    CONSTRAINT uq_cart_user UNIQUE ("user"),
    CONSTRAINT fk_cart_user FOREIGN KEY ("user") REFERENCES users (id)
);

-- ── cart_items ──────────────────────────────────────────────────────────────────
CREATE TABLE cart_items (
    id        BIGINT            NOT NULL DEFAULT nextval('cart_items_id_generator'),
    product   BIGINT            NOT NULL,
    cart      BIGINT            NOT NULL,
    quantity  INTEGER           NOT NULL,
    price     DOUBLE PRECISION  NOT NULL,
    CONSTRAINT pk_cart_items PRIMARY KEY (id),
    CONSTRAINT uq_cart_items_product_cart UNIQUE (product, cart),
    CONSTRAINT fk_cart_items_product FOREIGN KEY (product) REFERENCES products (product_id),
    CONSTRAINT fk_cart_items_cart FOREIGN KEY (cart) REFERENCES cart (id)
);
