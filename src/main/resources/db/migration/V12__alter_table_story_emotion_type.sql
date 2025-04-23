ALTER TABLE `stories`
    DROP FOREIGN KEY `FK_STORY_EMOTION_ID`;

ALTER TABLE `stories`
    CHANGE `emotion_id` `emotion_type` VARCHAR(30) NOT NULL;
