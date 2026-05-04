<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Amici De Gusto - Login</title>
    <link rel="icon" href="../Resource/favicon.svg" type="image/svg+xml">
    <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@400;600;700&family=Inter:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="../css/Login.css">
</head>
<body>

<!-- Restaurant Background -->
<div class="bg-restaurant"></div>

<!-- Login Card -->
<div class="login-wrapper">
    <div class="login-card">

        <!-- Header -->
        <div class="card-header">
            <h1 class="card-title">Welcome back</h1>
            <span class="brand-label">Amici De Gusto</span>
        </div>

        <!-- Form -->
            <form action="<%= request.getContextPath() %>/page/login" method="post" autocomplete="off">

            <% if (request.getAttribute("error") != null) { %>
            <div style="color:#e74c3c; background:#fdecea; border:1px solid #e74c3c; border-radius:6px; padding:10px 14px; margin-bottom:14px; font-size:0.9rem;">
                <%= request.getAttribute("error") %>
            </div>
            <% } %>
            <% if (request.getParameter("registered") != null) { %>
            <div style="color:#27ae60; background:#eafaf1; border:1px solid #27ae60; border-radius:6px; padding:10px 14px; margin-bottom:14px; font-size:0.9rem;">
                Account created successfully! Please log in.
            </div>
            <% } %>
            <!-- Username -->
            <div class="form-group">
                <label class="form-label" for="username">USERNAME</label>
                <div class="input-wrapper">
                    <input
                            class="form-input"
                            type="text"
                            id="username"
                            name="username"
                            placeholder="e.g. jsmith"
                            value="<%= request.getAttribute("rememberedUsername") != null ? request.getAttribute("rememberedUsername") : "" %>"
                            autocomplete="off"
                    />
                    <svg class="input-icon" viewBox="0 0 24 24">
                        <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/>
                        <circle cx="12" cy="7" r="4"/>
                    </svg>
                </div>
            </div>

            <!-- Password -->
            <div class="form-group">
                <label class="form-label" for="password">PASSWORD</label>
                <div class="input-wrapper">
                    <input
                            class="form-input"
                            type="password"
                            id="password"
                            name="password"
                            placeholder="Enter your password"
                            autocomplete="off"
                    />
                    <svg class="input-icon" viewBox="0 0 24 24">
                        <rect x="3" y="11" width="18" height="11" rx="2" ry="2"/>
                        <path d="M7 11V7a5 5 0 0 1 10 0v4"/>
                    </svg>
                </div>
            </div>

            <!-- Options Row: Remember me + Forgot -->
            <div class="options-row">
                <label class="remember-label">
                    <input type="checkbox" class="remember-checkbox" name="rememberMe" value="on" />
                    <span class="remember-checkmark"></span>
                    <span class="remember-text">Remember me</span>
                </label>
                <a href="#" class="forgot-link">Forgot password?</a>
            </div>

            <!-- Login Button -->
            <button type="submit" class="btn-login">
                LOGIN
            </button>

        </form>

        <!-- Register Link -->
        <p class="register-text">
            Don't have an account? <a href="UserRegister.jsp" class="register-link">Register here</a>
        </p>

    </div>
</div>

<!-- Page Footer -->
<div class="page-footer">
    <span>&copy; 2026 AMICI DE GUSTO</span>
</div>

</body>
</html>