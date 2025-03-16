ALTER TABLE `users`
    CHANGE `email` `user_id` VARCHAR(255) NOT NULL UNIQUE;
