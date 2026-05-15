<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="aptProject.model.User, aptProject.model.Order, aptProject.dao.OrderDAO, java.util.List" %>
<%
   User profileUser = (User) session.getAttribute("user");
   if (request.getAttribute("profileUser") != null) {
       profileUser = (User) request.getAttribute("profileUser");
   }

   List<Order> myOrders = null;
   if (profileUser != null) {
       myOrders = new OrderDAO().getOrdersByUserId(profileUser.getId());
   }

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
    <style>
        .edit-panel        { display: none; }
        .edit-panel:target { display: block; }

        /* ── TOAST NOTIFICATION ── */
        .toast {
            position: fixed;
            bottom: 2rem;
            right: 2rem;
            background: #1c3d2c;
            color: #f4eedd;
            padding: 14px 24px;
            border-radius: 10px;
            font-family: 'Inter', sans-serif;
            font-size: 14px;
            font-weight: 600;
            box-shadow: 0 8px 24px rgba(20,40,28,.3);
            display: flex;
            align-items: center;
            gap: 10px;
            z-index: 9999;
            animation: slideIn .3s ease, fadeOut .4s ease 2.6s forwards;
        }
        .toast-icon {
            width: 22px; height: 22px;
            background: #C9A84C;
            border-radius: 50%;
            display: flex; align-items: center; justify-content: center;
            font-size: 13px; flex-shrink: 0;
        }
        @keyframes slideIn {
            from { opacity: 0; transform: translateY(20px); }
            to   { opacity: 1; transform: translateY(0); }
        }
        @keyframes fadeOut {
            from { opacity: 1; }
            to   { opacity: 0; pointer-events: none; }
        }
    </style>
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
                <%-- ── ACCOUNT INFO PANEL ── --%>
                <section class="panel account-panel">
                    <div class="avatar-wrap">
                        <% if (profileUser != null && profileUser.getProfileImage() != null && !profileUser.getProfileImage().isEmpty()) { %>
                        <img class="avatar-img" src="<%= profileUser.getProfileImage() %>" alt="Profile" />
                        <% } else { %>
                        <div class="avatar"><%= profileUser != null ? profileUser.getFullName().substring(0,1).toUpperCase() : "U" %></div>
                        <% } %>
                    </div>
                    <div class="account-info">
                        <div class="title-row">
                            <div>
                                <h2><%= profileUser != null ? profileUser.getFullName() : "" %></h2>
                                <p class="role-tag"><%= profileUser != null ? profileUser.getRole() : "USER" %></p>
                            </div>
                            <a href="#editPanel" class="btn btn-gold">Edit Profile</a>
                        </div>
                        <dl>
                            <div><dt>Username</dt><dd><%= profileUser != null ? profileUser.getUsername() : "" %></dd></div>
                            <div><dt>Email</dt><dd><%= profileUser != null ? profileUser.getEmail() : "" %></dd></div>
                            <div><dt>Full Name</dt><dd><%= profileUser != null ? profileUser.getFullName() : "" %></dd></div>
                        </dl>
                    </div>
                </section>

                <%-- ── PASSWORD PANEL ── --%>
                <section class="panel password-panel">
                    <div class="section-title">
                        <p class="eyebrow small">Security</p>
                        <h2>Change Password</h2>
                    </div>
                    <form class="profile-form" action="<%= request.getContextPath() %>/profile/changePassword" method="post">
                        <% if (errorMsg != null) { %>
                        <div class="form-msg form-msg-error"><%= errorMsg %></div>
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

            <%-- ── EDIT PROFILE PANEL (admin-style, revealed by #editPanel) ── --%>
            <section class="panel edit-panel" id="editPanel">
                <div class="section-title">
                    <p class="eyebrow small">Edit Details</p>
                    <h2>Update Your Profile</h2>
                </div>
                <form class="user-edit-form" action="<%= request.getContextPath() %>/profile/update" method="post" enctype="multipart/form-data">
                    <label>
                        Full Name
                        <input type="text" name="fullName" value="<%= profileUser != null ? profileUser.getFullName() : "" %>" />
                    </label>
                    <label>
                        Email
                        <input type="email" name="email" value="<%= profileUser != null ? profileUser.getEmail() : "" %>" />
                    </label>
                    <label>
                        Username
                        <input type="text" value="<%= profileUser != null ? profileUser.getUsername() : "" %>" readonly />
                    </label>
                    <label>
                        Profile Picture
                        <% if (profileUser != null && profileUser.getProfileImage() != null && !profileUser.getProfileImage().isEmpty()) { %>
                        <div style="margin-bottom:8px;">
                            <img src="<%= profileUser.getProfileImage() %>" alt="Current"
                                 style="width:60px;height:60px;border-radius:50%;object-fit:cover;border:2px solid #C9A84C;" />
                            <span style="font-size:12px;color:#5A4A42;margin-left:8px;">Current photo</span>
                        </div>
                        <% } %>
                        <input type="file" name="profileImage" accept="image/*" />
                        <small style="color:#8B7B74;">Leave empty to keep current photo</small>
                    </label>
                    <div class="form-actions">
                        <button class="btn btn-gold" type="submit">Save Changes</button>
                        <a href="#" class="btn btn-soft">Cancel</a>
                    </div>
                </form>
            </section>

            <%-- ── MY ORDERS PANEL ── --%>
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
                        <div>
                            <h3><%= o.getToken() %></h3>
                            <p><%= o.getCreatedAt() != null ? o.getCreatedAt().toString().substring(0,16) : "" %></p>
                        </div>
                        <span class="order-status <%= statusClass %>"><%= o.getStatus() %></span>
                        <div style="text-align:right;">
                            <p style="font-weight:700;margin-bottom:.4rem;">NPR <%= String.format("%.2f", o.getTotalPrice()) %></p>
                            <a href="<%= request.getContextPath() %>/tracking?orderId=<%= o.getId() %>" class="track-link">Track →</a>
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

<%-- ── TOAST for success messages ── --%>
<% if (successMsg != null) { %>
<div class="toast" id="toast">
    <div class="toast-icon">✔</div>
    <span><%= successMsg %></span>
</div>
<script>
    setTimeout(function() {
        var t = document.getElementById('toast');
        if (t) t.remove();
    }, 3000);
</script>
<% } %>

</body>
</html>
