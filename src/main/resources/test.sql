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
COMMENT ON COLUMN "public"."users"."avatar" IS 'Название файла с изображение профиля';
COMMENT ON TABLE "public"."users" IS 'Пользователи';

-- ----------------------------
-- Uniques structure for table users
-- ----------------------------
ALTER TABLE users ADD CONSTRAINT "users_login_key" UNIQUE ("login");

-- ----------------------------
-- Primary Key structure for table users
-- ----------------------------
ALTER TABLE users ADD CONSTRAINT "users_pkey" PRIMARY KEY ("id");