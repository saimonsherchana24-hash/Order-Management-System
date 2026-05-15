CREATE TABLE `users` (
    `id`            int(11)      NOT NULL AUTO_INCREMENT,
    `full_name`     varchar(100) NOT NULL,
    `username`      varchar(50)  NOT NULL,
    `email`         varchar(100) DEFAULT NULL,
    `password_hash` varchar(255) NOT NULL,
    `role`          varchar(20)  NOT NULL DEFAULT 'USER',
    PRIMARY KEY (`id`),
    UNIQUE KEY `username` (`username`),
    UNIQUE KEY `email` (`email`)
);

INSERT INTO `users` (`full_name`, `username`, `email`, `password_hash`, `role`) VALUES
('Admin', 'admin', 'admin@gmail.com', 'admin123', 'ADMIN');
