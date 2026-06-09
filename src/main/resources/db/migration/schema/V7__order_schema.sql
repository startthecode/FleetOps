-- V7__order_schema.sql
-- Schema for orders and order_items (a placed order and its line items).
-- Generated from OrderEntity / OrderItemsEntity.
-- NOTE: "order" is a reserved SQL keyword, so the table name is quoted.

-- ── Sequence (shared by OrderEntity and OrderItemsEntity; allocationSize = 10) ─
CREATE SEQUENCE order_id_generator
    START WITH 1
    INCREMENT BY 10;

-- ── order ─────────────────────────────────────────────────────────────────────
CREATE TABLE "order" (
    id            BIGINT            NOT NULL DEFAULT nextval('order_id_generator'),
    owner         BIGINT            NOT NULL,
    total_amount  DOUBLE PRECISION  NOT NULL,
    status        VARCHAR(20)       NOT NULL,
    user_state    BIGINT            NOT NULL,
    CONSTRAINT pk_order PRIMARY KEY (id),
    CONSTRAINT fk_order_owner FOREIGN KEY (owner) REFERENCES users (id),
    CONSTRAINT fk_order_user_state FOREIGN KEY (user_state) REFERENCES states (state_id),
    CONSTRAINT ck_order_status CHECK (status IN
        ('PENDING', 'CONFIRMED', 'PACKING', 'READY_FOR_PICKUP', 'ASSIGNED',
         'PICKED_UP', 'IN_TRANSIT', 'DELIVERED', 'CANCELLED', 'FAILED',
         'RETURNED', 'OUT_OF_STOCK'))
);

-- ── order_items ─────────────────────────────────────────────────────────────────
CREATE TABLE order_items (
    id          BIGINT            NOT NULL DEFAULT nextval('order_id_generator'),
    product     BIGINT            NOT NULL,
    quantity    INTEGER           NOT NULL,
    price       DOUBLE PRECISION  NOT NULL,
    user_order  BIGINT            NOT NULL,
    CONSTRAINT pk_order_items PRIMARY KEY (id),
    CONSTRAINT uq_order_items_user_order_product UNIQUE (user_order, product),
    CONSTRAINT fk_order_items_product FOREIGN KEY (product) REFERENCES products (product_id),
    CONSTRAINT fk_order_items_user_order FOREIGN KEY (user_order) REFERENCES "order" (id)
);
