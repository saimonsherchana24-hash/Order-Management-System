<%-- Page setup: configure JSP encoding and imports before rendering HTML. --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<%-- Head section: define metadata, page title, icons, fonts, and CSS links. --%>
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width,initial-scale=1" />
  <title>About Us - Amici de Gusto</title>
  <link rel="icon" href="<%= request.getContextPath() %>/Resource/favicon.svg" type="image/svg+xml">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/About.css" />
</head>
<%-- Body section: start the visible page layout shown to the user. --%>
<body>

<!-- ── NAVBAR ── -->
<%-- Header section: show branding, navigation links, and quick user actions. --%>
<header class="site-header">
  <div class="header-inner">
    <a href="<%= request.getContextPath() %>/menu" class="brand">Amici <span class="de">de</span> Gusto</a>
    <nav class="header-nav">
      <a href="<%= request.getContextPath() %>/menu"             class="nav-link">Menu</a>
      <a href="<%= request.getContextPath() %>/about"   class="nav-link active">About Us</a>
      <a href="<%= request.getContextPath() %>/contact" class="nav-link">Contact</a>
    </nav>
    <div class="header-actions">
      <a href="<%= request.getContextPath() %>/profile" class="icon-btn" aria-label="Profile" title="Profile">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/>
        </svg>
      </a>
      <a href="<%= request.getContextPath() %>/cart" class="icon-btn" aria-label="Cart">
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

  <!-- ── HERO ── -->
  <%-- Content section: group related page content for this part of the screen. --%>
  <section class="page-hero">
    <div class="container">
      <p class="eyebrow">Our Story</p>
      <h1>About Amici de Gusto</h1>
      <p class="lead">A family-owned Italian restaurant bringing authentic flavours of Italy to the heart of Pokhara since 1972.</p>
    </div>
  </section>

  <!-- ── ABOUT CONTENT ── -->
  <section class="about-section">
    <div class="container">

      <!-- ── ROW 1: Our Story (image left) ── -->
      <div class="about-row">
        <div class="about-img-wrap">
          <img src="<%= request.getContextPath() %>/Resource/hero.jpg"
               alt="Amici de Gusto restaurant interior"
               class="about-img"
               onerror="this.style.display='none';this.nextElementSibling.style.display='flex';" />
          <div class="about-img-fallback" style="display:none;">
            <span>🍝</span>
          </div>
        </div>
        <div class="about-details">
          <p class="eyebrow">Est. 1972</p>
          <h2>Our Story</h2>
          <div class="about-divider"></div>
          <p class="about-text">Amici de Gusto was founded in 1972 by the Russo family, who brought their cherished recipes from Naples to the lakeside city of Pokhara. What began as a small trattoria with just eight tables has grown into one of Nepal's most beloved Italian dining destinations.</p>
          <p class="about-text">The name <em>Amici de Gusto</em> — meaning "Friends of Taste" in Italian — reflects our core belief: that great food brings people together. Every dish we serve is a tribute to that philosophy.</p>
          <dl class="about-dl">
            <div><dt>Founded</dt><dd>1972</dd></div>
            <div><dt>Location</dt><dd>Lakeside, Pokhara, Nepal</dd></div>
            <div><dt>Cuisine</dt><dd>Authentic Italian</dd></div>
            <div><dt>Seating</dt><dd>Indoor &amp; Outdoor, 80 covers</dd></div>
          </dl>
        </div>
      </div>

      <!-- ── ROW 3: The Institute / System (image left) ── -->
      <div class="about-row">
        <div class="about-img-wrap">
          <img src="<%= request.getContextPath() %>/Resource/loginImage.png"
               alt="Digital ordering system"
               class="about-img"
               onerror="this.style.display='none';this.nextElementSibling.style.display='flex';" />
          <div class="about-img-fallback" style="display:none;">
            <span>💻</span>
          </div>
        </div>
        <div class="about-details">
          <p class="eyebrow">Digital Transformation</p>
          <h2>Our Order Management System</h2>
          <div class="about-divider"></div>
          <p class="about-text">This Order Management System was developed as part of the Advanced Programming Techniques coursework at <strong>Islington College</strong>, affiliated with <strong>London Metropolitan University</strong>.</p>
          <p class="about-text">The system enables customers to browse the menu, manage their cart, place orders, and track order status in real time — while giving administrators full control over menu management, order processing, and billing.</p>
          <dl class="about-dl">
            <div><dt>Institution</dt><dd>Informatics College Pokhara, Pokhara</dd></div>
            <div><dt>Affiliation</dt><dd>London Metropolitan University</dd></div>
            <div><dt>Module</dt><dd>Advanced Programming Technology</dd></div>
            <div><dt>Technology</dt><dd>Java, JSP, MySQL, Apache Tomcat</dd></div>
          </dl>
        </div>
      </div>

    </div>
  </section>


</main>

<!-- ── TEAM STRIP ── -->
<%-- Content section: group related page content for this part of the screen. --%>
<section class="team-strip">
  <div class="container">
    <p class="eyebrow center">The Team</p>
    <h2 class="team-title center">Built by</h2>
    <div class="team-cards">
      <div class="team-card">
        <div class="team-avatar">
          <img src="<%= request.getContextPath() %>/Resource/SubarnaShrestha.jpg" alt="Subarna Shrestha" />
        </div>
        <div class="team-name">Subarna Shrestha</div>
        <div class="team-role">Full Stack Developer</div>
      </div>

      <div class="team-card">
        <div class="team-avatar">
          <img src="<%= request.getContextPath() %>/Resource/Soniya.JPG" alt="Soniya Thapa" />
        </div>
        <div class="team-name">Soniya Thapa</div>
        <div class="team-role">Frontend Developer and Documentation</div>
      </div>

      <div class="team-card">
        <div class="team-avatar">
          <img src="<%= request.getContextPath() %>/Resource/Saimon.jpg" alt="Saimon Sherchan" />
        </div>
        <div class="team-name">Saimon Sherchan</div>
        <div class="team-role">Database and Backend</div>
      </div>

      <div class="team-card">
        <div class="team-avatar">
          <img src="<%= request.getContextPath() %>/Resource/Pragati.jpeg" alt="Pragati Kunwar" />
        </div>
        <div class="team-name">Pragati Kunwar</div>
        <div class="team-role">Backend Developer And Documentation</div>
      </div>

      <div class="team-card">
        <div class="team-avatar">
          <img src="<%= request.getContextPath() %>/Resource/Tsering.png" alt="Tsering Tashi Gurung" />
        </div>
        <div class="team-name">Tsering Tashi Gurung</div>
        <div class="team-role">Frontend</div>
      </div>
    </div>
  </div>
</section>

<%-- Footer section: show closing restaurant information and support details. --%>
<footer class="site-footer">
  <div class="container footer-grid">
    <div><h3>Amici <span class="accent">de</span> Gusto</h3><p>Authentic Italian dining in the heart of Pokhara since 1972.</p></div>
    <div><h4>Contact</h4><ul><li>Pokhara, Lakeside, Nepal</li><li>+977 01-4567890</li><li>namaste@amicidegusto.com.np</li></ul></div>
    <div><h4>Hours</h4><ul><li>Tuesday - Sunday</li><li>12:00 - 23:00</li><li style="opacity:.6;">Closed Mondays</li></ul></div>
  </div>
  <div class="footer-bottom"><div class="container">&copy; 2026 Amici de Gusto &mdash; Crafted with passion in Kathmandu, Nepal</div></div>
</footer>

<%-- Script section: load JavaScript that supports page interaction. --%>
<script src="<%= request.getContextPath() %>/js/cart.js"></script>
</body>
</html>
