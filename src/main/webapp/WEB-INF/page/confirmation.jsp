<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="aptProject.model.Order, aptProject.model.OrderItem" %>
<% Order order = (Order) request.getAttribute("order"); %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Confirmed - Amici de Gusto</title>
    <link rel="icon" href="<%= request.getContextPath() %>/Resource/favicon.svg" type="image/svg+xml">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/confirmation.css">
</head>
<body>

<!-- HEADER (EXACT MATCH TO CART.JSP) -->
<header class="site-header">
    <div class="header-inner">
        <a href="<%= request.getContextPath() %>/menu" class="brand">Amici <span class="de">de</span> Gusto</a>
        <div class="header-actions">
            <a href="<%= request.getContextPath() %>/profile" class="icon-btn" aria-label="Profile" title="Profile">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/>
                    <circle cx="12" cy="7" r="4"/>
                </svg>
            </a>
        </div>
    </div>
</header>

<div class="container">

    <!-- SUCCESS -->
    <div class="success">
        <div class="check">✔</div>
        <h1>Order Confirmed!</h1>
        <p>Thank you for your order, <b><%= order != null ? order.getCustomerName() : "Customer" %></b></p>
    </div>

    <!-- TOKEN -->
    <div class="order-box">
        <p>Your Token Number</p>
        <h2><%= order != null ? order.getToken() : "—" %></h2>
        <p>Show this token to collect your order</p>
    </div>

    <!-- ORDER DETAILS -->
    <div class="box">
        <h3>Order Details</h3>
        <table>
            <thead>
            <tr><th>Item</th><th>Qty</th><th>Price</th></tr>
            </thead>
            <tbody>
            <% if (order != null && order.getItems() != null) {
                for (OrderItem oi : order.getItems()) { %>
            <tr>
                <td><%= oi.getItemName() %></td>
                <td><%= oi.getQuantity() %></td>
                <td>NPR <%= String.format("%.2f", oi.getSubtotal()) %></td>
            </tr>
            <% } } %>
            </tbody>
        </table>
        <hr>
        <div class="summary-item total">
            <b>Total</b>
            <b>NPR <%= order != null ? String.format("%.2f", order.getTotalPrice()) : "0.00" %></b>
        </div>
    </div>

    <!-- BUTTONS -->
    <a href="<%= request.getContextPath() %>/tracking?orderId=<%= order != null ? order.getId() : "" %>" class="btn btn-primary">
         Track Order Status
    </a>
    <a href="<%= request.getContextPath() %>/profile" class="btn btn-secondary">
         My Profile & Orders
    </a>
    <a href="<%= request.getContextPath() %>/menu" class="btn btn-secondary">
        Order More Items
    </a>

</div>
</body>
</html>