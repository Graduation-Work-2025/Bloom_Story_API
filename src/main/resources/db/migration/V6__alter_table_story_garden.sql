ALTER TABLE `stories`
ADD COLUMN `is_highlight`   tinyint(1)  DEFAULT 0    NOT NULL comment '하이라이트 여부';