<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <title>User Profile - Amici de Gusto</title>
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
                    <div class="avatar">AD</div>
                    <div class="account-info">
                        <h2>Aarav Devkota</h2>
                        <dl>
                            <div><dt>Username</dt><dd>aarav.devkota</dd></div>
                            <div><dt>Email</dt><dd>aarav.devkota@example.com</dd></div>
                            <div><dt>Full Name</dt><dd>Aarav Devkota</dd></div>
                        </dl>
                        <button class="btn btn-gold" type="button">Edit Profile</button>
                    </div>
                </section>
                <section class="panel password-panel">
                    <div class="section-title">
                        <p class="eyebrow small">Security</p>
                        <h2>Change Password</h2>
                    </div>
                    <form class="profile-form" action="#" method="post">
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
                <p class="orders-copy">View your active order status, delivery progress, and previous order details from one place.</p>
                <a href="Tracking.jsp" class="btn btn-gold orders-btn">My Orders</a>
            </section>
            <div class="logout-row">
                <a href="Login.jsp" class="btn logout-btn">Logout</a>
            </div>
        </div>
    </section>
</main>
</body>
</html>