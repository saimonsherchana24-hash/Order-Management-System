<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="aptProject.model.Order, aptProject.model.OrderItem, aptProject.model.User, java.util.List" %>
<%
    User adminUser   = (User) session.getAttribute("user");
    String adminInitial = (adminUser != null) ? adminUser.getFullName().substring(0,1).toUpperCase() : "A";
    String adminName    = (adminUser != null) ? adminUser.getFullName() : "Admin";
    List<Order> orders  = (List<Order>) request.getAttribute("orders");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Management – Amici De Gusto</title>
    <link rel="icon" href="../Resource/favicon.svg" type="image/svg+xml">
    <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@400;600;700&family=Inter:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="../css/AdminOrder.css">
</head>
<body>

<!-- SIDEBAR -->
<aside class="sidebar">
    <div class="logo-wrap">
        <div class="logo-icon">🍽️</div>
        <div class="logo-name">Amici<br>De Gusto</div>
        <div class="logo-sub">Italian Restaurant</div>
    </div>
    <div class="ornament">◆</div>
    <nav>
        <a class="nav-item" href="<%= request.getContextPath() %>/admin/dashboard"><span class="nav-icon">🏠</span> Dashboard</a>
        <a class="nav-item active" href="<%= request.getContextPath() %>/admin/orders"><span class="nav-icon">📋</span> Order Management</a>
        <a class="nav-item" href="<%= request.getContextPath() %>/admin/menu"><span class="nav-icon">🍴</span> Menu Management</a>
        <a class="nav-item" href="<%= request.getContextPath() %>/admin/billing"><span class="nav-icon">🧾</span> Billing System</a>
        <a class="nav-item" href="<%= request.getContextPath() %>/logout"><span class="nav-icon">🚪</span> Logout</a>
    </nav>
</aside>

<!-- MAIN -->
<main class="main">

    <!-- Topbar -->
    <div class="topbar">
        <div class="title-row">
            <div class="title-bar"></div>
            <div class="page-title">
                <h1>Order Management</h1>
                <span>Track and update all orders</span>
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

    <!-- Content -->
    <div class="content">

        <!-- Orders Table -->
        <div class="table-card">
            <div class="table-header">
                <h3>All Orders</h3>
            </div>

            <% if (orders == null || orders.isEmpty()) { %>
            <p class="empty-msg">No orders found.</p>
            <% } else { %>
            <table>
                <thead>
                    <tr>
                        <th>Token</th>
                        <th>Customer</th>
                        <th>Items</th>
                        <th>Total</th>
                        <th>Date</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                <% for (Order o : orders) {
                       boolean isPending   = "PENDING".equalsIgnoreCase(o.getStatus());
                       boolean isCompleted = "COMPLETED".equalsIgnoreCase(o.getStatus());
                %>
                <tr>
                    <td class="token"><%= o.getToken() %></td>
                    <td><%= o.getCustomerName() %></td>
                    <td class="items-cell">
                        <% if (o.getItems() != null) {
                               for (OrderItem oi : o.getItems()) { %>
                        <span><%= oi.getItemName() %> x<%= oi.getQuantity() %></span><br>
                        <%     }
                           } %>
                    </td>
                    <td>NPR <%= String.format("%.2f", o.getTotalPrice()) %></td>
                    <td><%= o.getCreatedAt() != null ? o.getCreatedAt().toString().substring(0,16) : "" %></td>
                    <td>
                        <span class="badge <%= isCompleted ? "badge-complete" : isPending ? "badge-pending" : "badge-active" %>">
                            <%= o.getStatus() %>
                        </span>
                    </td>
                    <td>
                        <% if (!isCompleted) { %>
                        <div class="action-btns">
                            <!-- Mark as Completed -->
                            <form method="post" action="<%= request.getContextPath() %>/admin/orders/updateStatus" style="display:inline">
                                <input type="hidden" name="orderId" value="<%= o.getId() %>">
                                <input type="hidden" name="status"  value="COMPLETED">
                                <button type="submit" class="btn-complete">✔ Complete</button>
                            </form>
                            <% if (!isPending) { %>
                            <!-- Mark back to Pending -->
                            <form method="post" action="<%= request.getContextPath() %>/admin/orders/updateStatus" style="display:inline">
                                <input type="hidden" name="orderId" value="<%= o.getId() %>">
                                <input type="hidden" name="status"  value="PENDING">
                                <button type="submit" class="btn-pending">↩ Pending</button>
                            </form>
                            <% } %>
                        </div>
                        <% } else { %>
                        <span class="done-text">✔ Done</span>
                        <% } %>
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
            <% } %>
        </div>

    </div>
</main>

</body>
</html>
