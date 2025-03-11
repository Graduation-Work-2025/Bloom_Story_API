ALTER TABLE `stories`
    ADD COLUMN `expired_at`     TIMESTAMP       NOT NULL,
    ADD COLUMN `sharing_type`   VARCHAR(255)    NOT NULL
;