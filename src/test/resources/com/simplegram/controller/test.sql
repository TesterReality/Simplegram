DROP TABLE IF EXISTS users;
CREATE TABLE users (
  "id" varchar(255) NOT NULL,
  "avatar" varchar(255),
  "login" varchar(255) NOT NULL,
  "password" varchar(255) NOT NULL,
  "username" varchar(255) NOT NULL,
  "online_date" timestamp(6) NOT NULL DEFAULT now()
)
;

-- ----------------------------
-- Uniques structure for table users
-- ----------------------------
ALTER TABLE users ADD CONSTRAINT "users_login_key" UNIQUE ("login");

-- ----------------------------
-- Primary Key structure for table users
-- ----------------------------
ALTER TABLE users ADD CONSTRAINT "users_pkey" PRIMARY KEY ("id");

INSERT INTO users VALUES ('d96b32c2-9650-4d0a-ac49-cb4fa862b72b', 'bd0a5881-2732-4833-9848-aa3cb29accc3.png', 'dudos', '$10$gopZGWVJPg2mk06dpAf2nuReImbVAw0U3Tn1MMV.G9C8N8JFQ83DO', 'fgdfgdfg', '2021-08-11 13:57:55.425');
