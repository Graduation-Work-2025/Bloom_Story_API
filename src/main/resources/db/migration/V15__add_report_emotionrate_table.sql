CREATE TABLE `emotion_rates`
(
    `id`          INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    `happy`       INT UNSIGNED DEFAULT 0,
    `sad`         INT UNSIGNED DEFAULT 0,
    `fear`        INT UNSIGNED DEFAULT 0,
    `disgust`     INT UNSIGNED DEFAULT 0,
    `surprised`   INT UNSIGNED DEFAULT 0,
    `angry`       INT UNSIGNED DEFAULT 0,
    `neutral`     INT UNSIGNED DEFAULT 0,
    `created_at`  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    `updated_at`  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `recommend_activities`
(
    `id`          INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    `user_id`     INT UNSIGNED NOT NULL,
    `category`    TEXT,
    `content`     TEXT,
    `reason`      TEXT,
    `story_id`    INT UNSIGNED NOT NULL,
    `created_at`  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    `updated_at`  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT `FK_RECOMMEND_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);