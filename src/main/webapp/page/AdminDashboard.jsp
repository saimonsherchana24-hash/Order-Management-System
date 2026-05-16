<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="aptProject.model.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% User adminUser = (User) session.getAttribute("user");
   String adminInitial = (adminUser != null && adminUser.getFullName() != null)
                         ? adminUser.getFullName().substring(0,1).toUpperCase() : "A";
   String adminName = (adminUser != null) ? adminUser.getFullName() : "Admin"; %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Amici De Gusto – Dashboard</title>
<link rel="icon" href="../Resource/favicon.svg" type="image/svg+xml">

<link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@400;600;700&family=Lato:wght@300;400;700&display=swap" rel="stylesheet">

<!-- External CSS -->
<link rel="stylesheet" href="../css/AdminDasboard.css">

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

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

      <a class="nav-item active" href="<%= request.getContextPath() %>/admin/dashboard"><span class="nav-icon">🏠</span> Dashboard</a>
      <a class="nav-item" href="<%= request.getContextPath() %>/admin/orders"><span class="nav-icon">📋</span> Order Management</a>
      <a class="nav-item" href="<%= request.getContextPath() %>/admin/menu"><span class="nav-icon">🍴</span> Menu Management</a>
      <a class="nav-item" href="<%= request.getContextPath() %>/admin/billing"><span class="nav-icon">🧾</span> Billing</a>
      <a class="nav-item" href="<%= request.getContextPath() %>/logout"><span class="nav-icon">🚪</span> Logout</a>

  </nav>
</aside>

<main class="main">
  <div class="topbar">
    <div class="title-row">
      <div class="title-bar"></div>
      <div class="page-title">
        <h1>Amici De Gusto</h1>
        <span>Admin Dashboard</span>
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

  <div class="content">
    <div class="welcome-row">
      <div>
        <h2>Welcome back, Admin!</h2>
        <p>Here's what's happening with your restaurant today.</p>
      </div>
      <button class="date-btn">📅 May 13, 2025 ▾</button>
    </div>

    <div class="stats-grid">
      <div class="stat-card">
        <div class="stat-icon-wrap icon-red">📋</div>
        <div class="stat-label">Total Orders</div>
        <div class="stat-value val-red"><%= request.getAttribute("totalOrders") != null ? request.getAttribute("totalOrders") : 0 %></div>
      </div>
      <div class="stat-card">
        <div class="stat-icon-wrap icon-gold">⏳</div>
        <div class="stat-label">Pending Orders</div>
        <div class="stat-value val-gold"><%= request.getAttribute("pendingOrders") != null ? request.getAttribute("pendingOrders") : 0 %></div>
      </div>
      <div class="stat-card">
        <div class="stat-icon-wrap icon-green">✅</div>
        <div class="stat-label">Completed Orders</div>
        <div class="stat-value val-green"><%= request.getAttribute("completedOrders") != null ? request.getAttribute("completedOrders") : 0 %></div>
      </div>
      <div class="stat-card">
        <div class="stat-icon-wrap icon-red">💲</div>
        <div class="stat-label">Total Revenue</div>
        <div class="stat-value val-red" style="font-size:28px;">NPR <%= request.getAttribute("totalRevenue") != null ? request.getAttribute("totalRevenue") : "0.00" %></div>
      </div>
    </div>

    <div class="bottom-row">
      <div class="chart-card">
        <div class="chart-header">
          <h3>Order Activity</h3>
          <button class="select-btn">This Week ▾</button>
        </div>
        
        <div style="height:350px; padding:20px;">
          <canvas id="revenueChart"></canvas>
      </div>


      <div class="promo-card">
        <div class="pasta-img">🍝</div>
        <div style="font-size:22px;margin-top:-6px;">🍷</div>
        <div class="promo-label">More orders on</div>
        <div class="promo-day">Saturday!</div>
        <div class="promo-sub">Keep up the great work!</div>
        <div class="promo-ornament">◆</div>
      </div>
    </div>
  </div>
</main>
      //Chart
<script>

  // Extracting X-axis labels (dates)
  // These come from DashboardServlet via request.setAttribute("dates", dates)
  // Example: ["2026-05-10", "2026-05-11", ...]
  const dates = [
  <c:forEach var="d" items="${dates}" varStatus="status">
      "${d}"${!status.last ? ',' : ''}
  </c:forEach>
  ];
  
  // Extracting Y-axis values (revenue)
  // These come from DashboardServlet via request.setAttribute("revenues", revenues)
  // Example: [1200, 2500, 1800, ...]
  const revenues = [
  <c:forEach var="r" items="${revenues}" varStatus="status">
      ${r}${!status.last ? ',' : ''}
  </c:forEach>
  ];
  
  
  // Get the canvas element where the chart will be drawn
  const ctx = document.getElementById('revenueChart');
  
  
  // Creating a new Chart.js bar chart
  new Chart(ctx, {
      type: 'bar', // Chart type: bar chart (can be line, pie, etc.)
  
      data: {
          // X-axis labels (dates of the week)
          labels: dates,
  
          datasets: [{
              // Label shown in chart legend
              label: 'Weekly Revenue (NPR)',
  
              // Y-axis data (revenue values for each date)
              data: revenues,
  
              // Thickness of bar border
              borderWidth: 2,
  
              // Bar color
              backgroundColor: '#C0392B'
          }]
      },
  
      options: {
          // Makes chart responsive to screen size
          responsive: true,
  
          // Prevents chart from stretching oddly
          maintainAspectRatio: false,
  
          scales: {
              y: {
                  // Ensures Y-axis starts from 0
                  beginAtZero: true
              }
          }
      }
  });
  
  </script>

</body>
</html>
