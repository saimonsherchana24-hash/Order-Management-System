<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="aptProject.model.Order, aptProject.model.OrderItem, java.util.List, java.util.Arrays" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Track Order - Amici de Gusto</title>
    <link rel="icon" href="../Resource/favicon.svg" type="image/svg+xml">
    <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@400;600;700&family=Inter:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="../css/Tracking.css">
</head>
<body>

<header class="navbar">
    <span class="brand">Amici <span class="de">de</span> Gusto</span>
</header>

<main>
    <div class="container">

        <div class="page-header">
            <p class="eyebrow">Il Tuo Ordine</p>
            <h1>Order Status</h1>
        </div>

        <% if (request.getAttribute("error") != null) { %>
        <div class="error-box">
            ⚠️ <%= request.getAttribute("error") %>
        </div>
        <% } %>

        <%
            Order order = (Order) request.getAttribute("order");
            if (order != null) {
                boolean isPending   = "PENDING".equalsIgnoreCase(order.getStatus());
                boolean isCompleted = "COMPLETED".equalsIgnoreCase(order.getStatus());
                List<String> doneStatuses = Arrays.asList("ACCEPTED","PREPARING","READY","COMPLETED");
        %>

        <div class="status-card">
            <div class="token-label">Token Number</div>
            <div class="token-number"><%= order.getToken() %></div>
            <div class="status-badge <%= isCompleted ? "badge-complete" : isPending ? "badge-pending" : "badge-active" %>">
                <%= order.getStatus() %>
            </div>
            <div class="order-total">Total: <b>NPR <%= String.format("%.2f", order.getTotalPrice()) %></b></div>
        </div>

        <div class="order-box">
            <h3>Your Order</h3>
            <div class="order-header">
                <span>Item</span><span>Qty</span><span>Price</span>
            </div>
            <% if (order.getItems() != null) {
                for (OrderItem oi : order.getItems()) { %>
            <div class="order-row">
                <span><%= oi.getItemName() %></span>
                <span><%= oi.getQuantity() %></span>
                <span>NPR <%= String.format("%.2f", oi.getSubtotal()) %></span>
            </div>
            <% } } %>
            <div class="total-row">
                <span>Total</span><span></span>
                <span>NPR <%= String.format("%.2f", order.getTotalPrice()) %></span>
            </div>
        </div>

        <% } else if (request.getAttribute("error") == null) { %>
        <div class="empty-box">
            <p>No order to display. Go to your profile to view your orders.</p>
            <a href="<%= request.getContextPath() %>/profile" class="btn btn-gold">View My Orders</a>
        </div>
        <% } %>

        <div class="nav-links">
            <a href="<%= request.getContextPath() %>/profile" class="btn btn-outline">My Profile</a>
            <a href="<%= request.getContextPath() %>/menu"    class="btn btn-gold">Order More</a>
        </div>

    </div>
</main>

</body>
</html>