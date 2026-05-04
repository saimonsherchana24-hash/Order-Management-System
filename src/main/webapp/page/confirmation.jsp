<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="aptProject.model.Order, aptProject.model.OrderItem" %>
<% Order order = (Order) request.getAttribute("order"); %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Order Confirmed - Amici de Gusto</title>
<link rel="icon" href="../Resource/favicon.svg" type="image/svg+xml">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@400;600;700&family=Inter:wght@300;400;500;600&display=swap" rel="stylesheet">
<link rel="stylesheet" href="../css/confirmation.css">
</head>
<body>

<!-- HEADER -->
<header>
    <div class="logo">
        <a href="<%= request.getContextPath() %>/menu" class="home-btn">
            <i class="fa-solid fa-house"></i>
        </a>
        <div class="app-name">Amici <span class="accent">de</span> Gusto</div>
    </div>
    <i class="fa-solid fa-utensils" style="color:#E8C96A;font-size:20px;"></i>
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
        <i class="fa fa-location-dot"></i> Track Order Status
    </a>
    <a href="<%= request.getContextPath() %>/profile" class="btn btn-secondary">
        <i class="fa fa-user"></i> My Profile & Orders
    </a>
    <a href="<%= request.getContextPath() %>/menu" class="btn btn-secondary">
        <i class="fa fa-utensils"></i> Order More Items
    </a>

</div>
</body>
</html>
