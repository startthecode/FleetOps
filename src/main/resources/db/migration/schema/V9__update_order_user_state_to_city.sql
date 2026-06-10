-- V9__update_order_user_state_to_city.sql
-- Migration to update order table: rename user_state to user_city
-- and update foreign key reference from states to cities table

-- ── Drop existing foreign key constraint ──────────────────────────────────────
ALTER TABLE "order" DROP CONSTRAINT fk_order_user_state;

-- ── Rename column from user_state to user_city ────────────────────────────────
ALTER TABLE "order" RENAME COLUMN user_state TO user_city;

-- ── Add new foreign key constraint pointing to cities table ───────────────────
ALTER TABLE "order" ADD CONSTRAINT fk_order_user_city
    FOREIGN KEY (user_city) REFERENCES cities (city_id);