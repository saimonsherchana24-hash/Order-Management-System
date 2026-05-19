<%-- Page setup: configure JSP encoding and imports before rendering HTML. --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<%-- Head section: define metadata, page title, icons, fonts, and CSS links. --%>
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width,initial-scale=1" />
  <title>Your Cart - Amici de Gusto</title>
  <link rel="icon" href="<%= request.getContextPath() %>/Resource/favicon.svg" type="image/svg+xml">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/cart.css" />
</head>
<%-- Body section: start the visible page layout shown to the user. --%>
<body>

<%-- Header section: show branding, navigation links, and quick user actions. --%>
<header class="navbar">
  <a href="<%= request.getContextPath() %>/menu" class="brand">Amici <span class="de">de</span> Gusto</a>
  <div class="nav-end">
    <a href="<%= request.getContextPath() %>/profile" class="icon-btn" aria-label="Profile" title="Profile">
      <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
        <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/>
        <circle cx="12" cy="7" r="4"/>
      </svg>
    </a>
  </div>
</header>

<%-- Main content: render the primary page information and actions. --%>
<main>
  <div class="cart-wrap">
    <div class="container">
      <div class="cart-head">
        <a href="<%= request.getContextPath() %>/menu" class="back-link">
          <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M19 12H5M12 19l-7-7 7-7"/>
          </svg>
          Continue shopping
        </a>
        <p class="eyebrow">Il Tuo Ordine</p>
        <h1>Your Cart</h1>
      </div>
      <ul class="cart-list" id="cartItems"></ul>
      <div class="empty" id="emptyCart" hidden>
        <h2>Your cart is empty</h2>
        <p>Add food, drinks, or dessert from the menu.</p>
        <a href="<%= request.getContextPath() %>/menu" class="btn btn-gold">Browse Menu</a>
      </div>
      <div class="cart-actions" id="cartActions" hidden>
        <a href="<%= request.getContextPath() %>/order/checkout" class="btn btn-gold btn-block">Place Order</a>
      </div>
    </div>
  </div>
</main>

<%-- Footer section: show closing restaurant information and support details. --%>
<footer class="site-footer">
  <div class="container footer-grid">
    <div>
      <h3>Amici <span class="accent">de</span> Gusto</h3>
      <p>Authentic Italian dining in the heart of Kathmandu since 1972. Handcrafted pasta, wood-fired pizzas, and curated wines.</p>
    </div>
    <div>
      <h4>Contact</h4>
      <ul>
        <li>Thamel Marg, Kathmandu, Nepal</li>
        <li>+977 01-4567890</li>
        <li>namaste@amicidegusto.com.np</li>
      </ul>
    </div>
    <div>
      <h4>Hours</h4>
      <ul>
        <li>Tuesday - Sunday</li>
        <li>12:00 - 23:00</li>
        <li style="opacity:.6;">Closed Mondays</li>
      </ul>
    </div>
  </div>
  <div class="footer-bottom">
    <div class="container">&copy; 2026 Amici de Gusto - Crafted with passion in Kathmandu, Nepal</div>
  </div>
</footer>

<%-- Script section: load JavaScript that supports page interaction. --%>
<script src="<%= request.getContextPath() %>/js/cart.js"></script>
</body>
</html>
