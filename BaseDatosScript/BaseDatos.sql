/*
 Navicat Premium Data Transfer

 Source Server         : postGrest
 Source Server Type    : PostgreSQL
 Source Server Version : 140007
 Source Host           : localhost:5432
 Source Catalog        : BaseDatos
 Source Schema         : banco

 Target Server Type    : PostgreSQL
 Target Server Version : 140007
 File Encoding         : 65001

 Date: 02/05/2023 08:32:47
*/


-- ----------------------------
-- Sequence structure for cliente_id_cliente_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "banco"."cliente_id_cliente_seq";
CREATE SEQUENCE "banco"."cliente_id_cliente_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for cuenta_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "banco"."cuenta_id_seq";
CREATE SEQUENCE "banco"."cuenta_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for movimientos_id_movimiento_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "banco"."movimientos_id_movimiento_seq";
CREATE SEQUENCE "banco"."movimientos_id_movimiento_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Table structure for cliente
-- ----------------------------
DROP TABLE IF EXISTS "banco"."cliente";
CREATE TABLE "banco"."cliente" (
  "id_cliente" int4 NOT NULL DEFAULT nextval('"banco".cliente_id_cliente_seq'::regclass),
  "direccion" varchar(500) COLLATE "pg_catalog"."default" NOT NULL,
  "edad" varchar(3) COLLATE "pg_catalog"."default" NOT NULL,
  "genero" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "identificacion" varchar(13) COLLATE "pg_catalog"."default" NOT NULL,
  "nombre" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "telefono" varchar(13) COLLATE "pg_catalog"."default" NOT NULL,
  "contrasena" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "estado" bool NOT NULL
)
;

-- ----------------------------
-- Table structure for cuenta
-- ----------------------------
DROP TABLE IF EXISTS "banco"."cuenta";
CREATE TABLE "banco"."cuenta" (
  "id_cuenta" int4 NOT NULL DEFAULT nextval('"banco".cuenta_id_seq'::regclass),
  "estado" bool NOT NULL,
  "numero_cuenta" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "saldo_inicial" float4 NOT NULL,
  "tipo_cuenta" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "id_cliente" int4 NOT NULL
)
;

-- ----------------------------
-- Table structure for movimientos
-- ----------------------------
DROP TABLE IF EXISTS "banco"."movimientos";
CREATE TABLE "banco"."movimientos" (
  "id_movimiento" int4 NOT NULL DEFAULT nextval('"banco".movimientos_id_movimiento_seq'::regclass),
  "fecha" timestamp(6) NOT NULL,
  "saldo" float4 NOT NULL,
  "tipo_movimiento" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "valor" float4 NOT NULL,
  "id" int4 NOT NULL
)
;

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "banco"."cliente_id_cliente_seq"
OWNED BY "banco"."cliente"."id_cliente";
SELECT setval('"banco"."cliente_id_cliente_seq"', 8, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "banco"."cuenta_id_seq"
OWNED BY "banco"."cuenta"."id_cuenta";
SELECT setval('"banco"."cuenta_id_seq"', 9, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "banco"."movimientos_id_movimiento_seq"
OWNED BY "banco"."movimientos"."id_movimiento";
SELECT setval('"banco"."movimientos_id_movimiento_seq"', 8, true);

-- ----------------------------
-- Uniques structure for table cliente
-- ----------------------------
ALTER TABLE "banco"."cliente" ADD CONSTRAINT "uk_147rikqkb68rxqijmxxgpjwot" UNIQUE ("identificacion");

-- ----------------------------
-- Primary Key structure for table cliente
-- ----------------------------
ALTER TABLE "banco"."cliente" ADD CONSTRAINT "cliente_pkey" PRIMARY KEY ("id_cliente");

-- ----------------------------
-- Primary Key structure for table cuenta
-- ----------------------------
ALTER TABLE "banco"."cuenta" ADD CONSTRAINT "cuenta_pkey" PRIMARY KEY ("id_cuenta");

-- ----------------------------
-- Primary Key structure for table movimientos
-- ----------------------------
ALTER TABLE "banco"."movimientos" ADD CONSTRAINT "movimientos_pkey" PRIMARY KEY ("id_movimiento");

-- ----------------------------
-- Foreign Keys structure for table cuenta
-- ----------------------------
ALTER TABLE "banco"."cuenta" ADD CONSTRAINT "fkmkmi3xf6wrp0y1mdn8nm4weim" FOREIGN KEY ("id_cliente") REFERENCES "banco"."cliente" ("id_cliente") ON DELETE NO ACTION ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table movimientos
-- ----------------------------
ALTER TABLE "banco"."movimientos" ADD CONSTRAINT "fkiseddnwkqiub05s90cv3o8gph" FOREIGN KEY ("id") REFERENCES "banco"."cuenta" ("id_cuenta") ON DELETE NO ACTION ON UPDATE NO ACTION;
