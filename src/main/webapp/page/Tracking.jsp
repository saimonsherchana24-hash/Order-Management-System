<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="aptProject.model.Order, aptProject.model.OrderItem, java.util.List, java.util.Arrays" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Track Order - Amici de Gusto</title>
<link rel="icon" href="../Resource/favicon.svg" type="image/svg+xml">
<link rel="stylesheet" href="../css/Tracking.css">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
</head>
<body>

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

    <h1>Track Your Order</h1>

    <%-- Search form --%>
    <form method="get" action="<%= request.getContextPath() %>/tracking">
        <div class="track-box">
            <input type="text" name="orderId"
                   placeholder="Enter Order ID  e.g. 5"
                   value="<%= request.getParameter("orderId") != null ? request.getParameter("orderId") : "" %>">
            <button type="submit"><i class="fa fa-search"></i> Track</button>
        </div>
    </form>

    <%-- Nav buttons --%>
    <a href="<%= request.getContextPath() %>/profile" class="btn">👤 My Profile & Orders</a>
    <a href="<%= request.getContextPath() %>/menu"    class="btn">🍽️ Order More</a>

    <%-- Error message --%>
    <% if (request.getAttribute("error") != null) { %>
    <div class="status" style="border-color:#f5c6c2; background:#fdecea;">
        <h3 style="color:#c0392b;">⚠️ <%= request.getAttribute("error") %></h3>
    </div>
    <% } %>

    <%-- Order result --%>
    <%
      Order order = (Order) request.getAttribute("order");
      if (order != null) {
          List<String> doneStatuses = Arrays.asList("ACCEPTED","PREPARING","READY","COMPLETED");
    %>

    <%-- Status banner --%>
    <div class="status">
        <h3><i class="fa fa-utensils"></i> <%= order.getStatus() %></h3>
        <p>Token: <b><%= order.getToken() %></b> &nbsp;|&nbsp; Total: <b>NPR <%= String.format("%.2f", order.getTotalPrice()) %></b></p>
    </div>

    <%-- Progress steps --%>
    <div class="progress">
        <div class="step <%= doneStatuses.contains(order.getStatus()) ? "active-step" : "" %>">
            <div class="circle <%= doneStatuses.contains(order.getStatus()) ? "active" : "" %>">✔</div>
            Order Received
        </div>
        <div class="step <%= Arrays.asList("PREPARING","READY","COMPLETED").contains(order.getStatus()) ? "active-step" : "" %>">
            <div class="circle <%= Arrays.asList("PREPARING","READY","COMPLETED").contains(order.getStatus()) ? "active" : "" %>">✔</div>
            Preparing
        </div>
        <div class="step <%= Arrays.asList("READY","COMPLETED").contains(order.getStatus()) ? "active-step" : "" %>">
            <div class="circle <%= Arrays.asList("READY","COMPLETED").contains(order.getStatus()) ? "active" : "" %>">✔</div>
            Being Served
        </div>
        <div class="step <%= "COMPLETED".equals(order.getStatus()) ? "active-step" : "" %>">
            <div class="circle <%= "COMPLETED".equals(order.getStatus()) ? "active" : "" %>">✔</div>
            Completed
        </div>
    </div>

    <%-- Order items --%>
    <div class="order-box">
        <h3>Order Items</h3>
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

    <% } %>

</div>
</body>
</html>
