<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width,initial-scale=1" />
  <title>Amici de Gusto - Authentic Italian Dining in Kathmandu</title>
  <meta name="description" content="Premium Italian dining in Kathmandu. Handcrafted pasta, wood-fired pizzas, and curated wines since 1972." />
  <link rel="stylesheet" href="../css/home.css" />
</head>
<body>
<header class="site-header transparent">
  <div class="container header-inner">
    <a href="home.jsp" class="brand">Amici <span class="de">de</span> Gusto</a>
    <div class="header-actions">
      <a href="profile.html" class="icon-btn" aria-label="Profile" title="Profile">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/>
        </svg>
      </a>
      <a href="cart.jsp" class="icon-btn cart-icon" aria-label="Cart">
        <span class="cart-count" data-cart-count>0</span>
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <circle cx="9" cy="21" r="1"/><circle cx="20" cy="21" r="1"/>
          <path d="M1 1h4l2.7 13.4a2 2 0 0 0 2 1.6h9.7a2 2 0 0 0 2-1.6L23 6H6"/>
        </svg>
      </a>
    </div>
  </div>
</header>
<main>
  <section class="hero">
    <img src="../Resource/hero.jpg" alt="Elegant Italian fine dining table with pasta and red wine" class="hero-img" />
    <div class="hero-overlay"></div>
    <div class="hero-content">
      <p class="eyebrow">Ristorante - Est. 1972</p>
      <h1>Amici <span class="it">de</span> Gusto</h1>
      <p class="hero-tag">Authentic Italian Taste in Every Bite</p>
      <p class="hero-desc">A premium Italian dining experience offering handcrafted pasta, wood-fired pizzas, and fine wines.</p>
      <a href="menu.jsp" class="btn btn-gold">Explore Menu</a>
    </div>
  </section>
  <section class="section section-cream">
    <div class="container container-sm center">
      <p class="eyebrow">La Nostra Storia</p>
      <h2>Three Generations of Flavor</h2>
      <p class="lead">Since 1972, the Russo family has served Kathmandu with recipes carried across generations - slow-cooked sauces, hand-pulled pasta, and wines selected from small Italian estates. Every plate is a tribute to the Italian table, where food is family.</p>
    </div>
  </section>
</main>
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
<script src="../js/cart.js"></script>
</body>
</html>