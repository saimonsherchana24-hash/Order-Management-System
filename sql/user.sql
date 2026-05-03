CREATE TABLE `users` (
                         `id` int(11) NOT NULL,
                         `full_name` varchar(100) NOT NULL,
                         `username` varchar(50) NOT NULL,
                         `email` varchar(100) DEFAULT NULL,
                         `password_hash` varchar(255) NOT NULL,
                         `role` varchar(20) NOT NULL DEFAULT 'USER'