<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="aptProject.model.User" %>
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
<link rel="icon" href="<%= request.getContextPath() %>/Resource/favicon.svg" type="image/svg+xml">

<link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@400;600;700&family=Lato:wght@300;400;700&display=swap" rel="stylesheet">

<!-- External CSS -->
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/AdminDasboard.css">

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
      <a class="stat-card" href="<%= request.getContextPath() %>/admin/orders">
        <div class="stat-icon-wrap icon-red">📋</div>
        <div class="stat-label">Total Orders</div>
        <div class="stat-value val-red"><%= request.getAttribute("totalOrders") != null ? request.getAttribute("totalOrders") : 0 %></div>
      </a>
      <a class="stat-card" href="<%= request.getContextPath() %>/admin/orders?filter=PENDING">
        <div class="stat-icon-wrap icon-gold">⏳</div>
        <div class="stat-label">Pending Orders</div>
        <div class="stat-value val-gold"><%= request.getAttribute("pendingOrders") != null ? request.getAttribute("pendingOrders") : 0 %></div>
      </a>
      <a class="stat-card" href="<%= request.getContextPath() %>/admin/orders?filter=COMPLETED">
        <div class="stat-icon-wrap icon-green">✅</div>
        <div class="stat-label">Completed Orders</div>
        <div class="stat-value val-green"><%= request.getAttribute("completedOrders") != null ? request.getAttribute("completedOrders") : 0 %></div>
      </a>
      <a class="stat-card" href="<%= request.getContextPath() %>/admin/billing">
        <div class="stat-icon-wrap icon-red">💲</div>
        <div class="stat-label">Total Revenue</div>
        <div class="stat-value val-red" style="font-size:28px;">NPR <%= request.getAttribute("totalRevenue") != null ? request.getAttribute("totalRevenue") : "0.00" %></div>
      </a>
    </div>

    <div class="bottom-row">
      <div class="chart-card">
        <div class="chart-header">
          <h3>Daily Revenue</h3>
          <span class="select-btn">Last 7 Days</span>
        </div>
        <%
          double[] rev = (double[]) request.getAttribute("dailyRevenue");
          String[] lbl = (String[]) request.getAttribute("dayLabels");
          // Find max for scaling bars
          double maxRev = 1; // avoid divide-by-zero
          for (double d : rev) if (d > maxRev) maxRev = d;
        %>
        <div class="bar-chart">
          <div class="y-axis">
            <span>NPR <%= String.format("%,.0f", maxRev) %></span>
            <span>NPR <%= String.format("%,.0f", maxRev * 0.75) %></span>
            <span>NPR <%= String.format("%,.0f", maxRev * 0.5) %></span>
            <span>NPR <%= String.format("%,.0f", maxRev * 0.25) %></span>
            <span>0</span>
          </div>
          <div class="gridlines">
            <div class="gridline"></div><div class="gridline"></div>
            <div class="gridline"></div><div class="gridline"></div>
            <div class="gridline"></div>
          </div>
          <div class="bars-wrap">
            <% for (int i = 0; i < 7; i++) {
                 int heightPct = (int) Math.round((rev[i] / maxRev) * 100);
                 boolean isToday = (i == 6);
                 String barStyle = isToday
                     ? "height:" + heightPct + "%;background:linear-gradient(180deg,#C0392B,#8B1A1A);"
                     : "height:" + heightPct + "%;";
                 String revFormatted = String.format("NPR %,.0f", rev[i]);
            %>
            <div class="bar-group">
              <div class="bar" style="<%= barStyle %>" data-val="<%= revFormatted %>"></div>
              <span class="bar-label"><%= lbl[i] %></span>
            </div>
            <% } %>
          </div>

          <%-- SVG line overlay connecting bar tops --%>
          <%
            StringBuilder points = new StringBuilder();
            int barCount = 7;
            double barGroupWidth = 100.0 / barCount;
            for (int i = 0; i < barCount; i++) {
                double x = barGroupWidth * i + barGroupWidth / 2.0;
                double y = 100.0 - Math.round((rev[i] / maxRev) * 100.0);
                if (i > 0) points.append(" ");
                points.append(String.format("%.1f,%.1f", x, y));
            }
          %>
          <svg class="line-overlay" viewBox="0 0 100 100" preserveAspectRatio="none">
            <polyline
              points="<%= points.toString() %>"
              fill="none"
              stroke="#C9A84C"
              stroke-width="1.5"
              stroke-linejoin="round"
              stroke-linecap="round"
              vector-effect="non-scaling-stroke"
            />
            <% for (int i = 0; i < barCount; i++) {
                 double x = barGroupWidth * i + barGroupWidth / 2.0;
                 double y = 100.0 - Math.round((rev[i] / maxRev) * 100.0);
            %>
            <circle cx="<%= String.format("%.1f", x) %>" cy="<%= String.format("%.1f", y) %>"
                    r="1.8" fill="#C9A84C" vector-effect="non-scaling-stroke"/>
            <% } %>
          </svg>
        </div>
      </div>

      <div class="promo-card">
        <div class="pasta-img">🍝</div>
        <div style="font-size:22px;margin-top:-6px;">🍷</div>
        <%
          String bestDay = (String) request.getAttribute("bestDay");
          double bestRev = request.getAttribute("bestDayRevenue") != null
                           ? (double) request.getAttribute("bestDayRevenue") : 0;
        %>
        <% if (bestRev > 0) { %>
        <div class="promo-label">Best day this week</div>
        <div class="promo-day"><%= bestDay %></div>
        <div class="promo-sub">NPR <%= String.format("%,.2f", bestRev) %> revenue</div>
        <% } else { %>
        <div class="promo-label">No paid orders yet</div>
        <div class="promo-day">—</div>
        <div class="promo-sub">Revenue will appear here once orders are marked paid.</div>
        <% } %>
        <div class="promo-ornament">◆</div>
      </div>
    </div>
  </div>
</main>
</body>
</html>
