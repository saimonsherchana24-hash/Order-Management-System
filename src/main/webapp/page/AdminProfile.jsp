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
    <link rel="icon" href="data:image/svg+xml,<svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 100 100'><rect width='100' height='100' rx='18' fill='%238B1A1A'/><text x='50' y='68' text-anchor='middle' font-family='Georgia,serif' font-size='48' font-weight='700' fill='%23C9A84C'>AdG</text></svg>">
    <link rel="stylesheet" href="../css/AdminProfile.css" />
    <style>
        /* Hide edit panel by default, show when targeted via #editPanel anchor */
        .edit-panel          { display: none; }
        .edit-panel:target   { display: block; }
    </style>
</head>
<body>
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
                    <div class="avatar"><%= adminUser != null ? adminUser.getFullName().substring(0,1).toUpperCase() : "A" %></div>
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
                <form class="admin-form" action="<%= request.getContextPath() %>/admin/profile/update" method="post">
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
                    <% if (request.getAttribute("success") != null) { %>
                    <p style="color:green"><%= request.getAttribute("success") %></p>
                    <% } %>
                    <div class="form-actions">
                        <button class="btn btn-gold" type="submit">Save Changes</button>
                        <a href="#" class="btn btn-soft">Cancel</a>
                    </div>
                </form>
            </section>
        </div>
    </section>
</main>
</body>
</html>