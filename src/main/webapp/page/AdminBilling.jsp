<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Billing System</title>

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
    <a class="nav-item" href="${pageContext.request.contextPath}/page/AdminDashboard.jsp">
      <span class="nav-icon">🏠</span> Dashboard
    </a>

    <a class="nav-item" href="${pageContext.request.contextPath}/page/order.jsp">
      <span class="nav-icon">📋</span> Order Management
    </a>

    <a class="nav-item" href="${pageContext.request.contextPath}/page/AdminMenu.jsp">
      <span class="nav-icon">🍴</span> Menu Management
    </a>

    <a class="nav-item active" href="${pageContext.request.contextPath}/page/AdminBilling.jsp">
      <span class="nav-icon">🧾</span> Billing System
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

    <button class="admin-btn">
      <div class="admin-avatar">👤</div>
      Admin ▾
    </button>
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
        <tr>
          <td>ORD001</td>
          <td>13 May 2025</td>
          <td>₹560</td>
          <td class="paid">Paid</td>
          <td><a href="#r1" class="btn">View Bill</a></td>
        </tr>

        <tr>
          <td>ORD002</td>
          <td>13 May 2025</td>
          <td>₹820</td>
          <td class="unpaid">Unpaid</td>
          <td>
            <a href="#r2" class="btn">View Bill</a>
            <a href="#" class="btn paid-btn">Mark as Paid</a>
          </td>
        </tr>

        <tr>
          <td>ORD003</td>
          <td>13 May 2025</td>
          <td>₹340</td>
          <td class="progress">In Progress</td>
          <td>
            <a href="#r3" class="btn">View Bill</a>
            <a href="#" class="btn paid-btn">Mark as Paid</a>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>

  <div id="r1" class="popup">
    <div class="popup-content">
      <a href="#" class="close">×</a>
      <div class="receipt-header">
        <h2>Amici De Gusto</h2>
        <p>Receipt</p>
      </div>
      <div class="receipt-body">
        <p><b>Order ID:</b> ORD001</p>
        <p><b>Date:</b> 13 May 2025</p>
        <hr>
        <div class="item"><span>Pizza x2</span><span>₹112</span></div>
        <div class="item"><span>Pasta x3</span><span>₹234</span></div>
        <div class="item"><span>Tiramisu x2</span><span>₹64</span></div>
        <hr>
        <h3>Total: ₹560</h3>
        <p class="status paid">Paid</p>
      </div>
    </div>
  </div>

  <div id="r2" class="popup">
    <div class="popup-content">
      <a href="#" class="close">×</a>
      <div class="receipt-header">
        <h2>Amici De Gusto</h2>
        <p>Receipt</p>
      </div>
      <div class="receipt-body">
        <p><b>Order ID:</b> ORD002</p>
        <p><b>Date:</b> 13 May 2025</p>
        <hr>
        <div class="item"><span>Lasagna x2</span><span>₹180</span></div>
        <div class="item"><span>Pizza x3</span><span>₹252</span></div>
        <div class="item"><span>Tiramisu x4</span><span>₹128</span></div>
        <hr>
        <h3>Total: ₹820</h3>
        <p class="status unpaid">Unpaid</p>
      </div>
    </div>
  </div>

  <div id="r3" class="popup">
    <div class="popup-content">
      <a href="#" class="close">×</a>
      <div class="receipt-header">
        <h2>Amici De Gusto</h2>
        <p>Receipt</p>
      </div>
      <div class="receipt-body">
        <p><b>Order ID:</b> ORD003</p>
        <p><b>Date:</b> 13 May 2025</p>
        <hr>
        <div class="item"><span>Espresso x3</span><span>₹24</span></div>
        <div class="item"><span>Tiramisu x2</span><span>₹64</span></div>
        <hr>
        <h3>Total: ₹340</h3>
        <p class="status progress">In Progress</p>
      </div>
    </div>
  </div>

</main>
</body>
</html>