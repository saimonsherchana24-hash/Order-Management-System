<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width,initial-scale=1" />
  <title>Your Cart - Amici de Gusto</title>
  <link rel="icon" href="../Resource/favicon.svg" type="image/svg+xml">
  <link rel="stylesheet" href="../css/cart.css" />
</head>
<body>

<!-- NAVBAR - brand flush left, profile icon on right, no cart icon -->
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

<!-- MAIN -->
<main>
  <div class="cart-page">
    <div class="container">

      <div class="cart-header">
        <a href="<%= request.getContextPath() %>/menu" class="back-link">
          <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M19 12H5M12 19l-7-7 7-7"/>
          </svg>
          Continue shopping
        </a>
        <p class="eyebrow">Il Tuo Ordine</p>
        <h1>Your Cart</h1>
      </div>

      <!-- Empty state visible by default since no JS loads items -->
      <div class="empty-state" id="emptyCart">
        <h2>Your cart is empty</h2>
        <p>Add food, drinks, or dessert from the menu.</p>
        <a href="<%= request.getContextPath() %>/menu" class="btn">Browse Menu</a>
      </div>

    </div>
  </div>
</main>

<!-- FOOTER -->
<footer class="footer">
  <div class="container footer-grid">
    <div>
      <h3>Amici <span class="accent">de</span> Gusto</h3>
      <p>Authentic Italian dining in the heart of Kathmandu since 1972.</p>
    </div>
    <div>
      <h4>Contact</h4>
      <ul>
        <li>Thamel Marg, Kathmandu</li>
        <li>+977 01-4567890</li>
        <li>namaste@amicidegusto.com.np</li>
      </ul>
    </div>
    <div>
      <h4>Hours</h4>
      <ul>
        <li>Tuesday - Sunday</li>
        <li>12:00 - 23:00</li>
        <li>Closed Mondays</li>
      </ul>
    </div>
  </div>
  <div class="footer-bottom">
    <div class="container">&copy; 2026 Amici de Gusto</div>
  </div>
</footer>

</body>
</html>