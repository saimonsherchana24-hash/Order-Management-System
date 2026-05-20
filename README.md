# Order Management System

This is a restaurant order management web application. Customers can view menu items, add food to the cart, place orders, and track their orders. Admin users can manage menu items, view orders, update order status, and manage billing.

## Technologies Used

- Java 17
- Jakarta Servlet
- JSP
- CSS
- JavaScript
- MySQL
- Maven
- Apache Tomcat

## Main Features

### Customer Features

- Register a new account
- Login and logout
- View the restaurant menu
- Add items to cart
- Checkout and place orders
- Track order status
- View and update profile

### Admin Features

- View admin dashboard
- Add, update, and delete menu items
- View customer orders
- Update order status
- Manage billing status
- Update admin profile

## Project Structure

```text
Order-Management-System
|-- pom.xml
|-- sql
|   |-- schema.sql
|-- src
|   |-- main
|       |-- Java
|       |   |-- aptProject
|       |       |-- Controller
|       |       |   |-- servlets
|       |       |-- dao
|       |       |-- filter
|       |       |-- model
|       |       |-- utilities
|       |-- webapp
|           |-- css
|           |-- js
|           |-- Resource
|           |-- WEB-INF
|               |-- page
|               |-- web.xml
```

## Folder Explanation

- `Controller/servlets` contains servlet files.
- `dao` contains database operation files.
- `model` contains Java model classes.
- `filter` contains authentication and authorization logic.
- `utilities` contains helper classes like database connection and session handling.
- `WEB-INF/page` contains JSP pages.
- `css` contains styling files.
- `js` contains JavaScript files.
- `Resource` contains images used in the project.
- `sql/schema.sql` contains the database script.

## Database Setup

1. Open MySQL Workbench or phpMyAdmin.
2. Open this file:

```text
sql/schema.sql
```

3. Run the full SQL script.
4. The script will create this database:

```text
order_management_system
```

5. It will also create these tables:

```text
users
menu_items
orders
order_items
```

## Database Connection

The database connection file is:

```text
src/main/Java/aptProject/utilities/DBConnection.java
```

Default database settings:

```text
Database name: order_management_system
Username: root
Password: empty
Host: localhost
Port: 3306
```

If your MySQL username or password is different, update `DBConnection.java`.

## Default Admin Account

The SQL file creates one admin account.

```text
Email: admin@amicidegusto.com
Username: admin
Password: admin123
```

## How to Run the Project

1. Install Java 17.
2. Install Maven.
3. Install MySQL.
4. Install Apache Tomcat 10 or newer.
5. Run `sql/schema.sql` in MySQL.
6. Open the project in IntelliJ IDEA or another Java IDE.
7. Build the project with Maven:

```bash
mvn clean package
```

8. Deploy the WAR file from the `target` folder to Tomcat.
9. Start Tomcat and open the project in the browser.

## Important URLs

### Public Pages

```text
/menu
/about
/contact
/login
/register
```

### Customer Pages

```text
/profile
/cart
/order/checkout
/tracking
```

### Admin Pages

```text
/admin/dashboard
/admin/menu
/admin/orders
/admin/billing
/admin/profile
```

## Notes

- JSP pages are stored inside `WEB-INF/page`.
- Pages are opened through servlet URLs.
- Admin pages are protected by `AuthFilter`.
- Menu item images are stored in `Resource/menu`.
- Profile images are stored in `Resource/profiles`.

## Project Name

Amici de Gusto Order Management System
