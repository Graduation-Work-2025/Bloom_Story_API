ALTER TABLE `stories`
    ADD COLUMN `emotion_detail_type` VARCHAR(255);

ALTER TABLE `emotion_rates`
    DROP COLUMN `neutral`;