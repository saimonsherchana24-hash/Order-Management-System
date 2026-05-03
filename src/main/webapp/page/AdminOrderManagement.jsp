<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Amici De Gusto - Order Management</title>
    <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@400;600;700&family=Lato:wght@300;400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="../css/AdminOrderManagement.css">
</head>
<body>
<aside class="sidebar">
    <div class="logo-wrap">
        <div class="logo-icon">🍽️</div>
        <div class="logo-name">Amici<br>De Gusto</div>
        <div class="logo-sub">Italian Restaurant</div>
    </div>
    <div class="ornament">◆</div>
    <nav>
        <a class="nav-item" href="AdminDashboard.jsp"><span class="nav-icon">🏠</span> Dashboard</a>
        <a class="nav-item active" href="AdminOrderManagement.jsp"><span class="nav-icon">📋</span> Order Management</a>
        <a class="nav-item" href="AdminMenu.jsp"><span class="nav-icon">🍴</span> Menu Management</a>
        <a class="nav-item" href="AdminBilling.jsp"><span class="nav-icon">🧾</span> Billing System</a>
    </nav>
</aside>
<main class="main">
    <div class="topbar">
        <div class="title-row">
            <div class="title-bar"></div>
            <div class="page-title">
                <h1>Order Management</h1>
                <span>Real-time Order Tracking</span>
            </div>
        </div>
        <a class="admin-btn" href="AdminProfile.jsp">
            <div class="admin-avatar">👤</div>
            Admin ▾
        </a>
    </div>
    <div class="order-content">
        <div class="order-header-row">
            <div>
                <h2>All Orders</h2>
                <p>Track and manage all incoming restaurant orders.</p>
            </div>
            <div class="live-badge">
                <span class="live-dot"></span> Live Tracking
            </div>
        </div>
        <div class="order-stats">
            <div class="ostat-card">
                <div class="ostat-icon total">
                    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                        <circle cx="9" cy="21" r="1"/><circle cx="20" cy="21" r="1"/>
                        <path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"/>
                    </svg>
                </div>
                <div class="ostat-label">Total Orders</div>
                <div class="ostat-value v-red">7</div>
            </div>
            <div class="ostat-card">
                <div class="ostat-icon pending">
                    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                        <circle cx="12" cy="12" r="10"/><polyline points="12 6 12 12 16 14"/>
                    </svg>
                </div>
                <div class="ostat-label">Pending Orders</div>
                <div class="ostat-value v-gold">2</div>
            </div>
            <div class="ostat-card">
                <div class="ostat-icon ready">
                    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                        <path d="M21 16V8a2 2 0 0 0-1-1.73l-7-4a2 2 0 0 0-2 0l-7 4A2 2 0 0 0 3 8v8a2 2 0 0 0 1 1.73l7 4a2 2 0 0 0 2 0l7-4A2 2 0 0 0 21 16z"/>
                    </svg>
                </div>
                <div class="ostat-label">Ready Orders</div>
                <div class="ostat-value v-blue">1</div>
            </div>
            <div class="ostat-card">
                <div class="ostat-icon done">
                    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                        <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"/>
                        <polyline points="22 4 12 14.01 9 11.01"/>
                    </svg>
                </div>
                <div class="ostat-label">Completed</div>
                <div class="ostat-value v-green">1</div>
            </div>
        </div>
        <div class="order-table-card">
            <div class="order-table-head">
                <h3>Order List</h3>
                <div class="table-date-badge">📅 Today</div>
            </div>
            <div class="order-table-wrap">
                <table class="orders">
                    <thead>
                    <tr>
                        <th>Order ID</th>
                        <th>Items</th>
                        <th>Total Price</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td class="o-id">Order #101</td>
                        <td>Burger x2, Coke x1</td>
                        <td class="o-price">Rs. 500</td>
                        <td><span class="sbadge sb-pending">Pending</span></td>
                        <td>
                            <div class="act-wrap">
                                <button class="abtn abtn-accept" type="button">Accept</button>
                                <button class="abtn abtn-completed" type="button">Completed</button>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="o-id">Order #102</td>
                        <td>Pizza x1, Fries x2</td>
                        <td class="o-price">Rs. 750</td>
                        <td><span class="sbadge sb-accepted">Accepted</span></td>
                        <td>
                            <div class="act-wrap">
                                <button class="abtn abtn-preparing" type="button">Preparing</button>
                                <button class="abtn abtn-completed" type="button">Completed</button>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="o-id">Order #103</td>
                        <td>Chicken Wings x3, Sprite x2</td>
                        <td class="o-price">Rs. 950</td>
                        <td><span class="sbadge sb-preparing">Preparing</span></td>
                        <td>
                            <div class="act-wrap">
                                <button class="abtn abtn-ready" type="button">Ready</button>
                                <button class="abtn abtn-completed" type="button">Completed</button>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="o-id">Order #104</td>
                        <td>Sandwich x1, Coffee x1</td>
                        <td class="o-price">Rs. 350</td>
                        <td><span class="sbadge sb-ready">Ready</span></td>
                        <td>
                            <div class="act-wrap">
                                <button class="abtn abtn-completed" type="button">Completed</button>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="o-id">Order #105</td>
                        <td>Nuggets x2, Pepsi x1</td>
                        <td class="o-price">Rs. 450</td>
                        <td><span class="sbadge sb-pending">Pending</span></td>
                        <td>
                            <div class="act-wrap">
                                <button class="abtn abtn-accept" type="button">Accept</button>
                                <button class="abtn abtn-completed" type="button">Completed</button>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="o-id">Order #106</td>
                        <td>Wrap x1, Juice x1</td>
                        <td class="o-price">Rs. 400</td>
                        <td><span class="sbadge sb-accepted">Accepted</span></td>
                        <td>
                            <div class="act-wrap">
                                <button class="abtn abtn-preparing" type="button">Preparing</button>
                                <button class="abtn abtn-completed" type="button">Completed</button>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="o-id">Order #107</td>
                        <td>Combo Meal x2</td>
                        <td class="o-price">Rs. 1200</td>
                        <td><span class="sbadge sb-completed">Completed</span></td>
                        <td>
                            <div class="act-wrap">
                                <span class="abtn-done-text">Order Complete</span>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</main>
</body>
</html>