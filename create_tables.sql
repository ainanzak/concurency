-- CREATE DATABASE concurency;
-- CREATE USER concurency WITH PASSWORD 'postgres';
-- GRANT ALL PRIVILEGES ON DATABASE "concurency" to concurency;

CREATE DATABASE sonar;
CREATE USER sonar WITH PASSWORD 'xaexohquaetiesoo';
GRANT ALL PRIVILEGES ON DATABASE "sonar" to sonar;

CREATE SEQUENCE "public"."tr_exchange_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 99999999999999
 START 1
 CACHE 1;
ALTER TABLE "public"."tr_exchange_seq" OWNER TO "postgres";

CREATE SEQUENCE "public"."ms_exchange_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 99999999999999
 START 1
 CACHE 1;
ALTER TABLE "public"."ms_exchange_seq" OWNER TO "postgres";


-- Create Table ms_tipe_exchage
CREATE TABLE "public"."ms_tipe_exchange" (
"id_tipe_exchange" int8 DEFAULT nextval('ms_exchange_seq'::regclass) NOT NULL,
"from" varchar(255) COLLATE "default",
"to" varchar(255) COLLATE "default",
"date" timestamp(6) DEFAULT NOW(),
CONSTRAINT "ms_tipe_exchage_pkey" PRIMARY KEY ("id_tipe_exchange")
)
WITH (OIDS=FALSE);
ALTER TABLE "public"."ms_tipe_exchange" OWNER TO "postgres";
CREATE UNIQUE INDEX "idx_id_tipe_exchange" ON "public"."ms_tipe_exchange" USING btree ("id_tipe_exchange");


-- Create Table tr_exchange
CREATE TABLE "public"."tr_exchange" (
"id_exchange" int8 DEFAULT nextval('tr_exchange_seq'::regclass) NOT NULL,
"id_tipe_exchange" int8,
"rate" float4,
"date" date,
"createat" timestamp(6) DEFAULT NOW(),
CONSTRAINT "tr_exchage_pkey" PRIMARY KEY ("id_exchange"),
CONSTRAINT "fr_id_tipe_exchange" FOREIGN KEY ("id_tipe_exchange") REFERENCES "public"."ms_tipe_exchange" ("id_tipe_exchange") ON DELETE RESTRICT ON UPDATE NO ACTION
)
WITH (OIDS=FALSE);
ALTER TABLE "public"."tr_exchange" OWNER TO "postgres";
CREATE UNIQUE INDEX "idx_id_exchange" ON "public"."tr_exchange" USING btree ("id_exchange");


-- Insert statement
INSERT INTO "public"."ms_tipe_exchange" ("from", "to", "date") VALUES ('GBP', 'USD', now());
INSERT INTO "public"."ms_tipe_exchange" ("from", "to", "date") VALUES ('USD', 'GBP', now());
INSERT INTO "public"."ms_tipe_exchange" ("from", "to", "date") VALUES ('USD', 'IDR', now());
INSERT INTO "public"."ms_tipe_exchange" ("from", "to", "date") VALUES ('JPY', 'IDR', now());

INSERT INTO "public"."tr_exchange" ("id_tipe_exchange", "rate", "date", "createat") VALUES ('1', '1.417', '2018-08-07', now());
INSERT INTO "public"."tr_exchange" ("id_tipe_exchange", "rate", "date", "createat") VALUES ('1', '1.295', '2018-08-08', now());
INSERT INTO "public"."tr_exchange" ("id_tipe_exchange", "rate", "date", "createat") VALUES ('1', '1.199', '2018-08-09', now());
INSERT INTO "public"."tr_exchange" ("id_tipe_exchange", "rate", "date", "createat") VALUES ('1', '1.288', '2018-08-10', now());
INSERT INTO "public"."tr_exchange" ("id_tipe_exchange", "rate", "date", "createat") VALUES ('1', '1.317', '2018-08-11', now());
INSERT INTO "public"."tr_exchange" ("id_tipe_exchange", "rate", "date", "createat") VALUES ('1', '1.105', '2018-08-12', now());
INSERT INTO "public"."tr_exchange" ("id_tipe_exchange", "rate", "date", "createat") VALUES ('1', '1.265', '2018-08-13', now());
INSERT INTO "public"."tr_exchange" ("id_tipe_exchange", "rate", "date", "createat") VALUES ('2', '0.7069', '2018-08-13', now());
INSERT INTO "public"."tr_exchange" ("id_tipe_exchange", "rate", "date", "createat") VALUES ('3', '14347', '2018-08-13', now());