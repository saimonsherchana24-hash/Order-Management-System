<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="aptProject.model.User, aptProject.model.Order, aptProject.dao.OrderDAO, java.util.List" %>
<%
   /* Get logged-in user from session */
   User profileUser = (User) session.getAttribute("user");

   /* Load this user's orders directly from DB */
   List<Order> myOrders = null;
   if (profileUser != null) {
       myOrders = new OrderDAO().getOrdersByUserId(profileUser.getId());
   }

   /* Get error or success message if any */
   String errorMsg   = (String) request.getAttribute("error");
   String successMsg = (String) request.getAttribute("success");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <title>User Profile - Amici de Gusto</title>
    <link rel="icon" href="../Resource/favicon.svg" type="image/svg+xml">
    <link rel="stylesheet" href="../css/UserProfile.css" />
</head>
<body>
<main>
    <section class="profile-wrap">
        <div class="container">
            <div class="profile-head">
                <p class="eyebrow">User Profile</p>
                <h1>Your Profile</h1>
                <p class="lead">Manage your account details, password, and order activity.</p>
            </div>
            <div class="profile-grid">
                <section class="panel account-panel">
                    <div class="avatar"><%= profileUser != null ? profileUser.getFullName().substring(0,1).toUpperCase() : "U" %></div>
                    <div class="account-info">
                        <h2><%= profileUser != null ? profileUser.getFullName() : "" %></h2>
                        <dl>
                            <div><dt>Username</dt><dd><%= profileUser != null ? profileUser.getUsername() : "" %></dd></div>
                            <div><dt>Email</dt><dd><%= profileUser != null ? profileUser.getEmail() : "" %></dd></div>
                            <div><dt>Full Name</dt><dd><%= profileUser != null ? profileUser.getFullName() : "" %></dd></div>
                        </dl>
                    </div>
                </section>
                <section class="panel password-panel">
                    <div class="section-title">
                        <p class="eyebrow small">Security</p>
                        <h2>Change Password</h2>
                    </div>
                    <form class="profile-form" action="<%= request.getContextPath() %>/profile/changePassword" method="post">
                        <% if (request.getAttribute("error") != null) { %>
                        <p style="color:red;margin-bottom:10px;"><%= errorMsg %></p>
                        <% } %>
                        <% if (request.getAttribute("success") != null) { %>
                        <p style="color:green;margin-bottom:10px;"><%= successMsg %></p>
                        <% } %>
                        <label>
                            Current Password
                            <input type="password" name="currentPassword" placeholder="Enter current password" />
                        </label>
                        <label>
                            New Password
                            <input type="password" name="newPassword" placeholder="Enter new password" />
                        </label>
                        <label>
                            Confirm Password
                            <input type="password" name="confirmPassword" placeholder="Confirm new password" />
                        </label>
                        <button class="btn btn-gold btn-block" type="submit">Update Password</button>
                    </form>
                </section>
            </div>
            <section class="panel orders-panel">
                <div class="section-title">
                    <p class="eyebrow small">Orders</p>
                    <h2>My Orders</h2>
                </div>

                <% if (myOrders == null || myOrders.isEmpty()) { %>
                <p class="orders-copy">You have no orders yet. <a href="<%= request.getContextPath() %>/menu" class="track-link">Browse the menu →</a></p>

                <% } else { %>
                <div class="order-list">
                    <% for (Order o : myOrders) {
                        String statusClass = "COMPLETED".equalsIgnoreCase(o.getStatus()) ? "complete" : "active";
                    %>
                    <div class="order-card">
                        <!-- Token & date -->
                        <div>
                            <h3><%= o.getToken() %></h3>
                            <p><%= o.getCreatedAt() != null ? o.getCreatedAt().toString().substring(0,16) : "" %></p>
                        </div>

                        <!-- Status badge -->
                        <span class="order-status <%= statusClass %>"><%= o.getStatus() %></span>

                        <!-- Total & track link -->
                        <div style="text-align:right;">
                            <p style="font-weight:700;margin-bottom:.4rem;">NPR <%= String.format("%.2f", o.getTotalPrice()) %></p>
                            <a href="<%= request.getContextPath() %>/tracking?orderId=<%= o.getId() %>"
                               class="track-link">Track →</a>
                        </div>
                    </div>
                    <% } %>
                </div>
                <% } %>
            </section>
            <div class="logout-row">
                <a href="<%= request.getContextPath() %>/logout" class="btn logout-btn">Logout</a>
            </div>
        </div>
    </section>
</main>
</body>
</html>