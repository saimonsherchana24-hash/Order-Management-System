-- ============================================================
--  Amici de Gusto — Order Management System
--  Database : order_management_system
--
--  HOW TO USE:
--    Run this entire file in MySQL Workbench or phpMyAdmin.
--    It will drop and recreate all tables cleanly.
--    Safe to re-run — existing data will be wiped.
-- ============================================================

CREATE DATABASE IF NOT EXISTS order_management_system
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE order_management_system;

-- Disable FK checks so we can drop tables in any order
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS menu_items;
DROP TABLE IF EXISTS users;

SET FOREIGN_KEY_CHECKS = 1;


-- ============================================================
--  TABLE 1: users
--
--  Stores both regular customers (role = 'USER')
--  and admin accounts (role = 'ADMIN').
--
--  Columns:
--    id            — auto-generated primary key
--    full_name     — display name shown in the UI
--    username      — unique login handle (auto-generated like user12345)
--    email         — unique email address used for login
--    password_hash — SHA-256 hashed password stored as:
--                    base64(salt) $ base64(hash)
--    role          — 'USER' or 'ADMIN'
--    profile_image — file path to uploaded profile photo (nullable)
--    created_at    — account creation timestamp
-- ============================================================
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


-- ============================================================
--  TABLE 2: menu_items
--
--  All items available on the restaurant menu.
--  Managed by admin through the Menu Management panel.
--
--  Columns:
--    id          — auto-generated primary key
--    name        — item name shown on the menu
--    category    — 'food', 'drinks', or 'dessert'
--    price       — price in NPR
--    description — short description shown on the menu card
--    image_url   — file path to item image (nullable)
--    created_at  — when the item was added
-- ============================================================
CREATE TABLE menu_items (
    id          INT            NOT NULL AUTO_INCREMENT,
    name        VARCHAR(150)   NOT NULL,
    category    VARCHAR(50)    NOT NULL,
    price       DECIMAL(10, 2) NOT NULL,
    description TEXT               NULL,
    image_url   VARCHAR(500)       NULL,
    created_at  TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (id),
    INDEX idx_menu_items_category (category)
);


-- ============================================================
--  TABLE 3: orders
--
--  One row per order placed by a user.
--  Links to users via user_id (foreign key).
--
--  Columns:
--    id             — auto-generated primary key
--                     Token shown to user = ADG-00001 format
--    user_id        — which user placed this order (FK → users)
--    status         — order progress:
--                     PENDING → ACCEPTED → PREPARING → READY → COMPLETED
--                     or REJECTED
--    payment_status — UNPAID (default) or PAID
--    total_price    — total amount in NPR
--    special_note   — optional note from the customer
--    created_at     — when the order was placed
-- ============================================================
CREATE TABLE orders (
    id             INT            NOT NULL AUTO_INCREMENT,
    user_id        INT            NOT NULL,
    status         VARCHAR(30)    NOT NULL DEFAULT 'PENDING',
    payment_status VARCHAR(20)    NOT NULL DEFAULT 'UNPAID',
    total_price    DECIMAL(10, 2) NOT NULL,
    special_note   TEXT               NULL,
    created_at     TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (id),
    INDEX idx_orders_user_id       (user_id),
    INDEX idx_orders_status        (status),
    INDEX idx_orders_payment_status(payment_status),

    CONSTRAINT fk_orders_user
        FOREIGN KEY (user_id) REFERENCES users (id)
        ON DELETE CASCADE   -- deleting a user removes their orders too
);


-- ============================================================
--  TABLE 4: order_items
--
--  Each row is one line item (one product) inside an order.
--  Links to orders via order_id and to menu_items via menu_item_id.
--
--  WHY we snapshot item_name and item_price:
--    If an admin later edits or deletes a menu item, the original
--    order receipt must still show the correct name and price.
--    So we copy the values at the moment the order is placed.
--
--  Columns:
--    id           — auto-generated primary key
--    order_id     — which order this item belongs to (FK → orders)
--    menu_item_id — which menu item was ordered (FK → menu_items)
--                   SET NULL if the menu item is deleted later
--    item_name    — snapshot of the item name at order time
--    item_price   — snapshot of the item price at order time (NPR)
--    quantity     — how many of this item were ordered
-- ============================================================
CREATE TABLE order_items (
    id           INT            NOT NULL AUTO_INCREMENT,
    order_id     INT            NOT NULL,
    menu_item_id INT                NULL,
    item_name    VARCHAR(150)   NOT NULL,
    item_price   DECIMAL(10, 2) NOT NULL,
    quantity     INT            NOT NULL DEFAULT 1,

    PRIMARY KEY (id),
    INDEX idx_order_items_order_id    (order_id),
    INDEX idx_order_items_menu_item_id(menu_item_id),

    CONSTRAINT fk_order_items_order
        FOREIGN KEY (order_id) REFERENCES orders (id)
        ON DELETE CASCADE,      -- deleting an order removes its items

    CONSTRAINT fk_order_items_menu
        FOREIGN KEY (menu_item_id) REFERENCES menu_items (id)
        ON DELETE SET NULL      -- keeps order history if menu item is deleted
);


-- ============================================================
--  RELATIONSHIP SUMMARY
--
--  users  ──< orders  ──< order_items >── menu_items
--
--  One user      → many orders
--  One order     → many order_items
--  One menu_item → many order_items (menu_item_id can be NULL
--                                    if item was deleted)
-- ============================================================


-- ============================================================
--  SEED DATA: default admin account
--
--  Username : admin
--  Password : admin123  (plain text — change after first login)
-- ============================================================
INSERT INTO users (full_name, username, email, password_hash, role)
VALUES (
    'Administrator',
    'admin',
    'admin@amicidegusto.com',
    'admin123',
    'ADMIN'
);


-- ============================================================
--  SEED DATA: sample menu items
-- ============================================================
INSERT INTO menu_items (name, category, price, description, image_url) VALUES

-- Food
('Margherita Pizza',    'food',    850.00,
 'Classic tomato base, fresh mozzarella and basil',
 '../Resource/default.jpg'),

('Spaghetti Carbonara', 'food',    950.00,
 'Creamy egg sauce, pancetta, parmesan, black pepper',
 '../Resource/default.jpg'),

('Penne Arrabbiata',    'food',    800.00,
 'Spicy tomato sauce, garlic, fresh chilli',
 '../Resource/default.jpg'),

('Lasagna al Forno',    'food',   1050.00,
 'Layers of pasta, beef ragu, bechamel, parmesan',
 '../Resource/default.jpg'),

('Risotto ai Funghi',   'food',    900.00,
 'Arborio rice, wild mushrooms, white wine, parmesan',
 '../Resource/default.jpg'),

-- Drinks
('Espresso',            'drinks',  150.00,
 'Single shot of rich Italian espresso',
 '../Resource/default.jpg'),

('Cappuccino',          'drinks',  200.00,
 'Espresso with steamed milk foam',
 '../Resource/default.jpg'),

('Sparkling Water',     'drinks',  120.00,
 'Chilled sparkling mineral water 500ml',
 '../Resource/default.jpg'),

('Fresh Lemonade',      'drinks',  250.00,
 'Freshly squeezed lemon, mint, soda',
 '../Resource/default.jpg'),

-- Dessert
('Tiramisu',            'dessert', 450.00,
 'Classic Italian dessert with mascarpone and espresso',
 '../Resource/default.jpg'),

('Panna Cotta',         'dessert', 380.00,
 'Vanilla cream with berry coulis',
 '../Resource/default.jpg'),

('Cannoli',             'dessert', 350.00,
 'Crispy pastry shells filled with sweet ricotta',
 '../Resource/default.jpg');
