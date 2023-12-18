INSERT INTO "users" (
    "username",
    "password",
    "created"
) VALUES (
    'user',
    '$2a$12$wZ90JiDNb3c1GelyrE8iTuBCfVJfFu/jkBfFPUu6j9/vxywZhjwwe',
    current_timestamp
);

INSERT INTO "smart_contracts" (
    "name",
    "contract_address",
    "created"
) VALUES (
    'My Smart Contract',
    '0x00000000000000000000000000000000001a2b3c',
    current_timestamp
);

INSERT INTO "user_smart_contracts" ("user_id", "smart_contract_id") VALUES (1, 1);


INSERT INTO "cryptocurrencies" (
    "name",
    "code"
) VALUES ('Bitcoin','BTC'), ('Ethereum','ETH'), ('Solana','SOL'), ('Cardano','ADA'), ('Dogecoin','DOGE');

INSERT INTO "user_cryptocurrencies" ("user_id", "cryptocurrency_id", "amount", "created")
VALUES (1, 1, 0.01, current_timestamp);
INSERT INTO "user_cryptocurrencies" ("user_id", "cryptocurrency_id", "amount", "created")
VALUES (1, 2, 3.22, current_timestamp);
INSERT INTO "user_cryptocurrencies" ("user_id", "cryptocurrency_id", "amount", "created")
VALUES (1, 3, 20.0, current_timestamp);










