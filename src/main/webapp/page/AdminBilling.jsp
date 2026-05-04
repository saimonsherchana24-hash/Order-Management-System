<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, aptProject.model.Order, aptProject.model.OrderItem, aptProject.model.User" %>
<% User adminUser = (User) session.getAttribute("user");
   String adminInitial = (adminUser != null && adminUser.getFullName() != null)
                         ? adminUser.getFullName().substring(0,1).toUpperCase() : "A";
   String adminName = (adminUser != null) ? adminUser.getFullName() : "Admin"; %>
<!DOCTYPE html>
<html lang="en">x
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Billing System</title>
  <link rel="icon" href="../Resource/favicon.svg" type="image/svg+xml">

  <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@400;600;700&family=Lato:wght@300;400;700&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="../css/AdminBilling.css">
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
    <a class="nav-item" href="${pageContext.request.contextPath}/admin/dashboard">
      <span class="nav-icon">🏠</span> Dashboard
    </a>
    <a class="nav-item" href="${pageContext.request.contextPath}/admin/orders">
      <span class="nav-icon">📋</span> Order Management
    </a>
    <a class="nav-item" href="${pageContext.request.contextPath}/admin/menu">
      <span class="nav-icon">🍴</span> Menu Management
    </a>
    <a class="nav-item active" href="${pageContext.request.contextPath}/admin/billing">
      <span class="nav-icon">🧾</span> Billing System
    </a>
    <a class="nav-item" href="${pageContext.request.contextPath}/logout">
      <span class="nav-icon">🚪</span> Logout
    </a>
  </nav>
</aside>

<main class="main">
  <div class="topbar">
    <div class="title-row">
      <div class="title-bar"></div>
      <div class="page-title">
        <h1>Amici De Gusto</h1>
        <span>Billing System</span>
      </div>
    </div>

    <a href="<%= request.getContextPath() %>/admin/profile" class="admin-profile-link">
      <div class="admin-avatar"><%= adminInitial %></div>
      <div class="admin-profile-info">
        <span class="admin-profile-name"><%= adminName %></span>
        <span class="admin-profile-role">Administrator</span>
      </div>
    </a>
  </div>

  <div class="grid">
    <div class="table-card">
      <div class="table-header">
        <div class="table-title">All Bills</div>
      </div>

      <table>
        <thead>
        <tr>
          <th>Token No. (Order ID)</th>
          <th>Order Date & Time</th>
          <th>Total Price</th>
          <th>Status</th>
          <th>Actions</th>
        </tr>
        </thead>

        <tbody>
        <%
          List<Order> orders = (List<Order>) request.getAttribute("orders");
          if (orders != null) {
            for (Order order : orders) {
              boolean isPaid = "PAID".equalsIgnoreCase(order.getPaymentStatus());
        %>
        <tr>
          <td><%= order.getToken() %></td>
          <td><%= order.getCreatedAt() %></td>
          <td>NPR <%= String.format("%.2f", order.getTotalPrice()) %></td>
          <td class="<%= isPaid ? "paid" : "unpaid" %>"><%= order.getPaymentStatus() %></td>
          <td>
            <a href="#bill-<%= order.getId() %>" class="btn">View Bill</a>
            <% if (!isPaid) { %>
            <form method="post" action="<%= request.getContextPath() %>/admin/billing/markPaid" style="display:inline">
              <input type="hidden" name="orderId" value="<%= order.getId() %>">
              <button type="submit" class="btn paid-btn">Mark as Paid</button>
            </form>
            <% } %>
          </td>
        </tr>
        <% } } %>
        </tbody>
      </table>
    </div>
  </div>

  <%-- Dynamic receipt popups for each order --%>
  <%
    List<Order> billOrders = (List<Order>) request.getAttribute("orders");
    if (billOrders != null) {
      for (Order o : billOrders) {
  %>
  <div id="bill-<%= o.getId() %>" class="popup">
    <div class="popup-content">
      <a href="#" class="close">×</a>
      <div class="receipt-header">
        <h2>Amici De Gusto</h2>
        <p>Receipt</p>
      </div>
      <div class="receipt-body">
        <p><b>Order ID:</b> <%= o.getToken() %></p>
        <p><b>Customer:</b> <%= o.getCustomerName() %></p>
        <p><b>Date:</b> <%= o.getCreatedAt() %></p>
        <hr>
        <% if (o.getItems() != null) { for (aptProject.model.OrderItem oi : o.getItems()) { %>
        <div class="item">
          <span><%= oi.getItemName() %> x<%= oi.getQuantity() %></span>
          <span>NPR <%= String.format("%.2f", oi.getSubtotal()) %></span>
        </div>
        <% } } %>
        <hr>
        <h3>Total: NPR <%= String.format("%.2f", o.getTotalPrice()) %></h3>
        <p class="status <%= "PAID".equalsIgnoreCase(o.getPaymentStatus()) ? "paid" : "unpaid" %>">
          <%= o.getPaymentStatus() %>
        </p>
      </div>
    </div>
  </div>
  <% } } %>

</main>
</body>
</html>