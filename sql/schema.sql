CREATE DATABASE IF NOT EXISTS order_management_system
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE order_management_system;

SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS menu_items;
DROP TABLE IF EXISTS users;
SET FOREIGN_KEY_CHECKS = 1;


CREATE TABLE users (
    id            INT           NOT NULL AUTO_INCREMENT,
    full_name     VARCHAR(100)  NOT NULL,
    username      VARCHAR(50)   NOT NULL,
    email         VARCHAR(150)  NOT NULL,
    password_hash VARCHAR(255)  NOT NULL,
    role          VARCHAR(20)   NOT NULL DEFAULT 'USER',
    profile_image VARCHAR(500)      NULL,
    created_at    TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uq_users_username (username),
    UNIQUE KEY uq_users_email    (email)
);


CREATE TABLE menu_items (
    id          INT            NOT NULL AUTO_INCREMENT,
    name        VARCHAR(150)   NOT NULL,
    category    VARCHAR(50)    NOT NULL,
    price       DECIMAL(10,2)  NOT NULL,
    description TEXT               NULL,
    image_url   VARCHAR(500)       NULL,
    created_at  TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);


CREATE TABLE orders (
    id             INT            NOT NULL AUTO_INCREMENT,
    user_id        INT            NOT NULL,
    status         VARCHAR(30)    NOT NULL DEFAULT 'PENDING',
    payment_status VARCHAR(20)    NOT NULL DEFAULT 'UNPAID',
    total_price    DECIMAL(10,2)  NOT NULL,
    special_note   TEXT               NULL,
    created_at     TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_orders_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);


CREATE TABLE order_items (
    id           INT            NOT NULL AUTO_INCREMENT,
    order_id     INT            NOT NULL,
    menu_item_id INT                NULL,
    item_name    VARCHAR(150)   NOT NULL,
    item_price   DECIMAL(10,2)  NOT NULL,
    quantity     INT            NOT NULL DEFAULT 1,
    PRIMARY KEY (id),
    CONSTRAINT fk_order_items_order FOREIGN KEY (order_id)     REFERENCES orders     (id) ON DELETE CASCADE,
    CONSTRAINT fk_order_items_menu  FOREIGN KEY (menu_item_id) REFERENCES menu_items (id) ON DELETE SET NULL
);


-- Admin account
-- Username : admin
-- Password : admin123  (SHA-256 hex)
INSERT INTO users (full_name, username, email, password_hash, role) VALUES
('Administrator', 'admin', 'admin@amicidegusto.com', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 'ADMIN');


-- Sample menu items
INSERT INTO menu_items (name, category, price, description, image_url) VALUES
('Margherita Pizza',    'food',    850.00, 'Classic tomato base, fresh mozzarella and basil',          '../Resource/default.jpg'),
('Spaghetti Carbonara', 'food',    950.00, 'Creamy egg sauce, pancetta, parmesan, black pepper',       '../Resource/default.jpg'),
('Penne Arrabbiata',    'food',    800.00, 'Spicy tomato sauce, garlic, fresh chilli',                 '../Resource/default.jpg'),
('Lasagna al Forno',    'food',   1050.00, 'Layers of pasta, beef ragu, bechamel, parmesan',           '../Resource/default.jpg'),
('Risotto ai Funghi',   'food',    900.00, 'Arborio rice, wild mushrooms, white wine, parmesan',       '../Resource/default.jpg'),
('Espresso',            'drinks',  150.00, 'Single shot of rich Italian espresso',                     '../Resource/default.jpg'),
('Cappuccino',          'drinks',  200.00, 'Espresso with steamed milk foam',                          '../Resource/default.jpg'),
('Sparkling Water',     'drinks',  120.00, 'Chilled sparkling mineral water 500ml',                    '../Resource/default.jpg'),
('Fresh Lemonade',      'drinks',  250.00, 'Freshly squeezed lemon, mint, soda',                      '../Resource/default.jpg'),
('Tiramisu',            'dessert', 450.00, 'Classic Italian dessert with mascarpone and espresso',     '../Resource/default.jpg'),
('Panna Cotta',         'dessert', 380.00, 'Vanilla cream with berry coulis',                          '../Resource/default.jpg'),
('Cannoli',             'dessert', 350.00, 'Crispy pastry shells filled with sweet ricotta',           '../Resource/default.jpg');
