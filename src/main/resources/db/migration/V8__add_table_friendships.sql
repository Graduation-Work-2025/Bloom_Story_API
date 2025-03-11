CREATE TABLE `friendships`
(
    `id`            INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    `requester_id`  INT UNSIGNED    NOT NULL,
    `sender_id`     INT UNSIGNED    NOT NULL,
    `is_allowed`    TINYINT         NOT NULL        DEFAULT '0',
    `created_at`  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    `updated_at`  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT `FK_FRIENDSHIP_REQUESTER_ID` FOREIGN KEY (`requester_id`) REFERENCES `users` (`id`),
    CONSTRAINT `FK_FRIENDSHIP_SENDER_ID` FOREIGN KEY (`sender_id`) REFERENCES `users` (`id`),
    UNIQUE KEY `unique_friendship` (`requester_id`, `sender_id`)
);