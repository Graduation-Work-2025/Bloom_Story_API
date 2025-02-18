ALTER TABLE `stories`
DROP COLUMN `title`;

CREATE TABLE `emotion_bloom_map` (
    `emotion_id` INT UNSIGNED NOT NULL,
    `bloom_id`   INT UNSIGNED NOT NULL,
    PRIMARY KEY (`emotion_id`, `bloom_id`),
    FOREIGN KEY (`emotion_id`) REFERENCES `emotions` (`id`) ON DELETE CASCADE,
    FOREIGN KEY (`bloom_id`) REFERENCES `blooms` (`id`) ON DELETE CASCADE
);

INSERT INTO `emotion_bloom_map`
VALUES (1,1),
       (1,2),
       (1,3),
       (1,4),
       (1,5),
       (2,6),
       (2,7),
       (2,8),
       (2,9),
       (3,10),
       (3,11),
       (3,12),
       (3,13),
       (4,14),
       (4,15),
       (4,16),
       (4,17),
       (5,18),
       (5,19),
       (5,20),
       (5,21),
       (6,22),
       (6,23),
       (6,24),
       (6,25),
       (7,26),
       (7,27),
       (7,28),
       (7,29)
;