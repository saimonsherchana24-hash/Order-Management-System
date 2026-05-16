<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="aptProject.model.User" %>
<% User adminUser = (User) request.getAttribute("adminUser");
   if (adminUser == null) adminUser = (User) session.getAttribute("user"); %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <title>Admin Profile - Amici de Gusto</title>
    <link rel="icon" href="<%= request.getContextPath() %>/Resource/favicon.svg" type="image/svg+xml">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/AdminProfile.css" />
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
            width: 22px;
            height: 22px;
            background: #C9A84C;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 13px;
            flex-shrink: 0;
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

<!-- ── TOP NAVBAR ── -->
<nav class="profile-navbar">
  <a href="<%= request.getContextPath() %>/admin/dashboard" class="back-btn">
    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5"
         stroke-linecap="round" stroke-linejoin="round" width="20" height="20">
      <path d="M19 12H5M12 19l-7-7 7-7"/>
    </svg>
    Back to Dashboard
  </a>
</nav>

<main>
    <section class="admin-wrap">
        <div class="container">
            <div class="profile-head">
                <p class="eyebrow">Admin Profile</p>
                <h1>Admin Account</h1>
                <p class="lead">Manage administrator details and review the latest login activity.</p>
            </div>
            <div class="admin-grid">
                <section class="panel admin-panel">
                    <%-- Show profile image if uploaded, otherwise show initial letter --%>
                    <div class="avatar-wrap">
                        <% if (adminUser != null && adminUser.getProfileImage() != null && !adminUser.getProfileImage().isEmpty()) { %>
                        <img class="avatar-img" src="<%= adminUser.getProfileImage() %>" alt="Profile" />
                        <% } else { %>
                        <div class="avatar"><%= adminUser != null ? adminUser.getInitial() : "A" %></div>
                        <% } %>
                    </div>
                    <div class="admin-info">
                        <div class="title-row">
                            <div>
                                <h2 id="displayName"><%= adminUser != null ? adminUser.getFullName() : "" %></h2>
                                <p class="role" id="displayRole"><%= adminUser != null ? adminUser.getRole() : "ADMIN" %></p>
                            </div>
                            <a href="#editPanel" class="btn btn-gold">Edit Profile</a>
                        </div>
                        <dl>
                            <div><dt>Name</dt><dd id="detailName"><%= adminUser != null ? adminUser.getFullName() : "" %></dd></div>
                            <div><dt>Role</dt><dd id="detailRole"><%= adminUser != null ? adminUser.getRole() : "" %></dd></div>
                            <div><dt>Email</dt><dd id="detailEmail"><%= adminUser != null ? adminUser.getEmail() : "" %></dd></div>
                        </dl>
                    </div>
                </section>
                <section class="panel activity-panel">
                    <div class="section-title">
                        <p class="eyebrow small">Account Activity</p>
                        <h2>Last Login Time</h2>
                    </div>
                    <div class="login-card">
                        <div class="login-icon">
                            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <circle cx="12" cy="12" r="10"/><path d="M12 6v6l4 2"/>
                            </svg>
                        </div>
                        <div>
                            <strong>May 2, 2026 - 23:49</strong>
                            <p>Last successful administrator login.</p>
                        </div>
                    </div>
                </section>
            </div>
            <section class="panel edit-panel" id="editPanel">
                <div class="section-title">
                    <p class="eyebrow small">Edit Details</p>
                    <h2>Update Admin Profile</h2>
                </div>
                <form class="admin-form" action="<%= request.getContextPath() %>/admin/profile/update" method="post" enctype="multipart/form-data">
                    <label>
                        Admin Name
                        <input type="text" name="adminName" id="adminName" value="<%= adminUser != null ? adminUser.getFullName() : "" %>" />
                    </label>
                    <label>
                        Email
                        <input type="email" name="adminEmail" id="adminEmail" value="<%= adminUser != null ? adminUser.getEmail() : "" %>" />
                    </label>
                    <label>
                        Role
                        <input type="text" id="adminRole" value="<%= adminUser != null ? adminUser.getRole() : "" %>" readonly />
                    </label>
                    <label>
                        Profile Picture
                        <% if (adminUser != null && adminUser.getProfileImage() != null && !adminUser.getProfileImage().isEmpty()) { %>
                        <div style="margin-bottom:8px;">
                            <img src="<%= adminUser.getProfileImage() %>" alt="Current"
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
        </div>
    </section>
</main>

<% if (request.getAttribute("success") != null) { %>
<div class="toast" id="toast">
    <div class="toast-icon">✔</div>
    <span><%= request.getAttribute("success") %></span>
</div>
<script>
    // Auto remove toast after 3 seconds
    setTimeout(function() {
        var t = document.getElementById('toast');
        if (t) t.remove();
    }, 3000);
</script>
<% } %>

</body>
</html>