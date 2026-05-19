<%-- Page setup: configure JSP encoding and imports before rendering HTML. --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<%-- Head section: define metadata, page title, icons, fonts, and CSS links. --%>
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width,initial-scale=1" />
  <title>Amici de Gusto - Authentic Italian Dining in Kathmandu</title>
  <link rel="icon" href="<%= request.getContextPath() %>/Resource/favicon.svg" type="image/svg+xml">
  <meta name="description" content="Premium Italian dining in Kathmandu. Handcrafted pasta, wood-fired pizzas, and curated wines since 1972." />
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/home.css" />
</head>
<%-- Body section: start the visible page layout shown to the user. --%>
<body>
<%-- Header section: show branding, navigation links, and quick user actions. --%>
<header class="site-header transparent">
  <div class="container header-inner">
    <!-- Cart and Profile icons removed -->
  </div>
</header>
<%-- Main content: render the primary page information and actions. --%>
<main>
  <%-- Content section: group related page content for this part of the screen. --%>
  <section class="hero">
    <img src="<%= request.getContextPath() %>/Resource/hero.jpg" alt="Elegant Italian fine dining table with pasta and red wine" class="hero-img" />
    <div class="hero-overlay"></div>
    <div class="hero-content">
      <p class="eyebrow">Ristorante - Est. 1972</p>
      <h1>Amici <span class="it">de</span> Gusto</h1>
      <p class="hero-tag">Authentic Italian Taste in Every Bite</p>
      <p class="hero-desc">A premium Italian dining experience offering handcrafted pasta, wood-fired pizzas, and fine wines.</p>
      <a href="<%= request.getContextPath() %>/login?redirect=menu" class="btn btn-gold">Explore Menu</a>
    </div>
  </section>
  <section class="section section-cream">
    <div class="container container-sm center">
      <p class="eyebrow">La Nostra Storia</p>
      <h2>Three Generations of Flavor</h2>
      <p class="lead">Since 1972, the Russo family has served Pokhara with recipes carried across generations - slow-cooked sauces, hand-pulled pasta, and wines selected from small Italian estates. Every plate is a tribute to the Italian table, where food is family.</p>
    </div>
  </section>
</main>
<%-- Footer section: show closing restaurant information and support details. --%>
<footer class="site-footer">
  <div class="container footer-grid">
    <div>
      <h3>Amici <span class="accent">de</span> Gusto</h3>
      <p>Authentic Italian dining in the heart of Pokhara since 1972. Handcrafted pasta, wood-fired pizzas, and curated wines.</p>
    </div>
    <div>
      <h4>Contact</h4>
      <ul>
        <li>Pokhara, Lakeside, Nepal</li>
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
    <div class="container">&copy; 2026 Amici de Gusto - Crafted with passion in Pokahra, Nepal</div>
  </div>
</footer>
</body>
</html>
