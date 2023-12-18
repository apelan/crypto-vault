-- create table for storing users info
CREATE TABLE IF NOT EXISTS "users" (
	"id" serial NOT NULL,
	"username" varchar(255) NOT NULL,
	"password" varchar(255) NOT NULL,
    "created" timestamp without time zone,
    "updated" timestamp without time zone,
	CONSTRAINT "users_pk" PRIMARY KEY ("id"),
	CONSTRAINT "users_username_uk" UNIQUE ("username")
);
CREATE INDEX IF NOT EXISTS "users_username_index" ON "users" ("username");

-- create table for storing smart contracts
CREATE TABLE IF NOT EXISTS "smart_contracts" (
	"id" serial NOT NULL,
	"name" varchar(50) NOT NULL,
	"contract_address" varchar(255) NOT NULL,
    "created" timestamp without time zone,
    "updated" timestamp without time zone,
	CONSTRAINT "smart_contracts_pk" PRIMARY KEY ("id")
);

-- create bridge table between user and smart contracts
CREATE TABLE IF NOT EXISTS "user_smart_contracts" (
    "id" serial NOT NULL,
    "user_id" integer NOT NULL,
    "smart_contract_id" integer NOT NULL,
    CONSTRAINT "user_smart_contracts_pk" PRIMARY KEY ("id"),
    CONSTRAINT "user_smart_contracts_users_fk" FOREIGN KEY ("user_id") REFERENCES "users" ("id"),
    CONSTRAINT "user_smart_contracts_smart_contracts_fk" FOREIGN KEY ("smart_contract_id") REFERENCES "smart_contracts" ("id")
);


-- create table for storing cryptocurrency
CREATE TABLE IF NOT EXISTS "cryptocurrencies" (
	"id" serial NOT NULL,
	"name" varchar(50) NOT NULL,
	"code" varchar(10) NOT NULL,
	CONSTRAINT "cryptocurrencies_pk" PRIMARY KEY ("id")
);

-- create bridge table between user and cryptocurrencies
CREATE TABLE IF NOT EXISTS "user_cryptocurrencies" (
    "id" serial NOT NULL,
    "user_id" integer NOT NULL,
    "cryptocurrency_id" integer NOT NULL,
    "amount" decimal(16,2) NOT NULL,
    "created" timestamp without time zone,
    "updated" timestamp without time zone,
    CONSTRAINT "user_cryptocurrencies_pk" PRIMARY KEY ("id"),
    CONSTRAINT "user_cryptocurrencies_users_fk" FOREIGN KEY ("user_id") REFERENCES "users" ("id"),
    CONSTRAINT "user_cryptocurrencies_cryptocurrencies_fk" FOREIGN KEY ("cryptocurrency_id") REFERENCES "cryptocurrencies" ("id")
);