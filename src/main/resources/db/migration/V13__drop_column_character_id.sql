ALTER TABLE `users`
    DROP COLUMN `character_id`;

ALTER TABLE `stories`
    CHANGE COLUMN `bloom_id` `bloom_id` INT UNSIGNED;

ALTER TABLE `stories`
    DROP CONSTRAINT `FK_STORY_BLOOM_ID`;