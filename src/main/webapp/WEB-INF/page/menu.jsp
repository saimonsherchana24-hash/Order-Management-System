<%-- Page setup: configure JSP encoding and imports before rendering HTML. --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="aptProject.model.MenuItem, java.util.List" %>
<%-- Server-side data step: read servlet/session values before displaying dynamic content. --%>
<%
    List<MenuItem> menuItems = (List<MenuItem>) request.getAttribute("menuItems");
    String activeCategory    = (String) request.getAttribute("activeCategory");
    if (activeCategory == null) activeCategory = "all";
%>
<!DOCTYPE html>
<html lang="en">
<%-- Head section: define metadata, page title, icons, fonts, and CSS links. --%>
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width,initial-scale=1" />
  <title>Our Menu - Amici de Gusto</title>
  <link rel="icon" href="<%= request.getContextPath() %>/Resource/favicon.svg" type="image/svg+xml">
  <meta name="description" content="Browse the full Amici de Gusto menu - savory plates, curated drinks, and sweet endings." />
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/menu.css" />
</head>
<%-- Body section: start the visible page layout shown to the user. --%>
<body>
<%-- Header section: show branding, navigation links, and quick user actions. --%>
<header class="site-header">
  <div class="container header-inner">
    <a href="<%= request.getContextPath() %>/menu" class="brand">Amici <span class="de">de</span> Gusto</a>
    <nav class="header-nav">
      <a href="<%= request.getContextPath() %>/menu"    class="nav-link active">Menu</a>
      <a href="<%= request.getContextPath() %>/about"   class="nav-link">About Us</a>
      <a href="<%= request.getContextPath() %>/contact" class="nav-link">Contact</a>
    </nav>
    <div class="header-actions">
      <a href="<%= request.getContextPath() %>/profile" class="icon-btn" aria-label="Profile" title="Profile">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/>
        </svg>
      </a>
      <a href="<%= request.getContextPath() %>/cart" class="icon-btn cart-icon" aria-label="Cart">
        <span class="cart-count" data-cart-count>0</span>
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <circle cx="9" cy="21" r="1"/><circle cx="20" cy="21" r="1"/>
          <path d="M1 1h4l2.7 13.4a2 2 0 0 0 2 1.6h9.7a2 2 0 0 0 2-1.6L23 6H6"/>
        </svg>
      </a>
    </div>
  </div>
</header>
<%-- Main content: render the primary page information and actions. --%>
<main>
  <%-- Content section: group related page content for this part of the screen. --%>
  <section class="section section-gradient">
    <div class="container">
      <div class="page-header">
        <p class="eyebrow">La Carta</p>
        <h1>Our Menu</h1>
        <p class="lead">Browse our full menu - savory plates, curated drinks, and sweet endings.</p>
      </div>
      <div class="tabs" aria-label="Menu category filter">
        <div class="tabs-inner">
          <button class="tab active" type="button" data-filter="all">All</button>
          <button class="tab" type="button" data-filter="food">Food</button>
          <button class="tab" type="button" data-filter="drinks">Drinks</button>
          <button class="tab" type="button" data-filter="dessert">Dessert</button>
        </div>
      </div>
      <div class="grid menu-grid animate-fade-in-up">
        <%-- Server-side data step: read servlet/session values before displaying dynamic content. --%>
        <% if (menuItems == null || menuItems.isEmpty()) { %>
        <p style="text-align:center;color:#4d5a52;padding:3rem 0;grid-column:1/-1;">
            No menu items available yet.
        </p>
        <% } else {
               for (MenuItem item : menuItems) {
                   String rawImg = item.getImageUrl();
                   String img;
                   if (rawImg == null || rawImg.isBlank()) {
                       img = request.getContextPath() + "/Resource/default.jpg";
                   } else if (rawImg.startsWith("/Resource") || rawImg.startsWith("/uploads")) {
                       // Context-relative path stored by UploadUtil — prepend context path
                       img = request.getContextPath() + rawImg;
                   } else {
                       // Already absolute or external URL
                       img = rawImg;
                   }
        %>
        <article class="menu-card" data-cat="<%= item.getCategory() %>">
          <div class="img-wrap">
            <img src="<%= img %>" alt="<%= item.getName() %>" loading="lazy" width="800" height="600" />
          </div>
          <div class="body">
            <div class="row">
              <h3><%= item.getName() %></h3>
              <span class="price">NPR <%= String.format("%,.0f", item.getPrice()) %></span>
            </div>
            <p><%= item.getDescription() != null ? item.getDescription() : "" %></p>
            <button class="btn btn-gold add-to-cart" type="button"
                    data-id="<%= item.getId() %>"
                    data-name="<%= item.getName() %>"
                    data-price="<%= (int) item.getPrice() %>"
                    data-img="<%= img %>"
                    data-desc="<%= item.getDescription() != null ? item.getDescription() : "" %>">
              + Add to Cart
            </button>
          </div>
        </article>
        <% } } %>
      </div>
    </div>
  </section>
</main>
<%-- Footer section: show closing restaurant information and support details. --%>
<footer class="site-footer">
  <div class="container footer-grid">
    <div><h3>Amici <span class="accent">de</span> Gusto</h3><p>Authentic Italian dining in the heart of Pokhara since 1972. Handcrafted pasta, wood-fired pizzas, and curated wines.</p></div>
    <div><h4>Contact</h4><ul><li>Pokhara,Lakeside, Nepal</li><li>+977 01-4567890</li><li>namaste@amicidegusto.com.np</li></ul></div>
    <div><h4>Hours</h4><ul><li>Tuesday - Sunday</li><li>12:00 - 23:00</li><li style="opacity:.6;">Closed Mondays</li></ul></div>
  </div>
  <div class="footer-bottom"><div class="container">&copy; 2026 Amici de Gusto - Crafted with passion in Pokhara, Nepal</div></div>
</footer>
<%-- Script section: load JavaScript that supports page interaction. --%>
<script src="<%= request.getContextPath() %>/js/cart.js"></script>
</body>
</html>
