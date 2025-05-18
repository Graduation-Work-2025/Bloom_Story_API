CREATE TABLE `summary_keywords`
(
    `id`          INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    `start_date`  TIMESTAMP NOT NULL,
    `sunday`      TEXT,
    `monday`      TEXT,
    `tuesday`     TEXT,
    `wednesday`   TEXT,
    `thursday`    TEXT,
    `friday`      TEXT,
    `saturday`    TEXT,
    `created_at`  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    `updated_at`  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

ALTER TABLE `emotion_rates`
    ADD COLUMN `user_id` INT UNSIGNED NOT NULL;