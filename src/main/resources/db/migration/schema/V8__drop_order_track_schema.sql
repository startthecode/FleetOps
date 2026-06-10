-- V8__drop_order_track_schema.sql
-- Removes tracking schema after TrackEntity was deleted.

DROP TABLE IF EXISTS order_track;

DROP SEQUENCE IF EXISTS track_id_generator;
