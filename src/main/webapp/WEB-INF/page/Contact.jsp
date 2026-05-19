<%-- Page setup: configure JSP encoding and imports before rendering HTML. --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<%-- Head section: define metadata, page title, icons, fonts, and CSS links. --%>
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width,initial-scale=1" />
  <title>Contact - Amici de Gusto</title>
  <link rel="icon" href="<%= request.getContextPath() %>/Resource/favicon.svg" type="image/svg+xml">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/About.css" />
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/Contact.css" />
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
      <a href="<%= request.getContextPath() %>/about" class="nav-link">About Us</a>
      <a href="<%= request.getContextPath() %>/contact" class="nav-link active">Contact</a>
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

  <!-- ── PAGE HERO ── -->
  <%-- Content section: group related page content for this part of the screen. --%>
  <section class="page-hero">
    <div class="container">
      <p class="eyebrow">Get In Touch</p>
      <h1>Contact Us</h1>
      <p class="lead">We'd love to hear from you. Visit us, call us, or send us a message.</p>
    </div>
  </section>

  <!-- ── CONTACT CONTENT ── -->
  <section class="contact-section">
    <div class="container">
      <div class="contact-grid">

        <!-- ── INFO COLUMN ── -->
        <div class="contact-info">

          <div class="info-block">
            <div class="info-icon">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <path d="M21 10c0 7-9 13-9 13S3 17 3 10a9 9 0 0 1 18 0z"/><circle cx="12" cy="10" r="3"/>
              </svg>
            </div>
            <div>
              <h3>Address</h3>
              <p>Lakeside, Pokhara</p>
              <p>Gandaki Province, Nepal</p>
            </div>
          </div>

          <div class="info-block">
            <div class="info-icon">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <path d="M22 16.92v3a2 2 0 0 1-2.18 2 19.79 19.79 0 0 1-8.63-3.07A19.5 19.5 0 0 1 4.69 12 19.79 19.79 0 0 1 1.61 3.4 2 2 0 0 1 3.6 1.22h3a2 2 0 0 1 2 1.72c.127.96.361 1.903.7 2.81a2 2 0 0 1-.45 2.11L7.91 8.82a16 16 0 0 0 6.29 6.29l.96-.96a2 2 0 0 1 2.11-.45c.907.339 1.85.573 2.81.7A2 2 0 0 1 22 16.92z"/>
              </svg>
            </div>
            <div>
              <h3>Phone</h3>
              <p>+977 01-4567890</p>
              <p>+977 9800000000</p>
            </div>
          </div>

          <div class="info-block">
            <div class="info-icon">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <path d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z"/><polyline points="22,6 12,13 2,6"/>
              </svg>
            </div>
            <div>
              <h3>Email</h3>
              <p>namaste@amicidegusto.com.np</p>
            </div>
          </div>

          <div class="info-block">
            <div class="info-icon">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <circle cx="12" cy="12" r="10"/><polyline points="12 6 12 12 16 14"/>
              </svg>
            </div>
            <div>
              <h3>Opening Hours</h3>
              <div class="hours-table">
                <div class="hours-row"><span>Tuesday &ndash; Friday</span><span>12:00 &ndash; 23:00</span></div>
                <div class="hours-row"><span>Saturday &ndash; Sunday</span><span>11:00 &ndash; 23:30</span></div>
                <div class="hours-row closed"><span>Monday</span><span>Closed</span></div>
              </div>
            </div>
          </div>

        </div>

        <!-- ── RIGHT COLUMN ── -->
        <div class="contact-right">

          <!-- Map placeholder -->
          <div class="map-wrap">
            <div class="map-placeholder">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" width="48" height="48">
                <path d="M21 10c0 7-9 13-9 13S3 17 3 10a9 9 0 0 1 18 0z"/><circle cx="12" cy="10" r="3"/>
              </svg>
              <p>Lakeside, Pokhara, Nepal</p>
              <span>Map placeholder &mdash; embed Google Maps iframe here</span>
            </div>
          </div>

          <!-- Message form -->
          <div class="message-card">
            <h3>Send a Message</h3>
            <p class="message-sub">For reservations, feedback, or general enquiries.</p>
            <%-- Form section: collect user input and submit it to the matching servlet. --%>
            <form class="contact-form" onsubmit="handleSubmit(event)">
              <div class="form-row">
                <label>Full Name<input type="text" placeholder="Your name" required /></label>
                <label>Email<input type="email" placeholder="your@email.com" required /></label>
              </div>
              <label>Subject<input type="text" placeholder="Feedback/ others" /></label>
              <label>Message<textarea rows="4" placeholder="Write your message here..." required></textarea></label>
              <button type="submit" class="btn-gold-solid">Send Message</button>
            </form>
            <div class="form-success" id="formSuccess">
              <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" width="22" height="22">
                <polyline points="20 6 9 17 4 12"/>
              </svg>
              Message sent! We'll get back to you soon.
            </div>
          </div>

        </div>
      </div>
    </div>
  </section>

</main>

<%-- Footer section: show closing restaurant information and support details. --%>
<footer class="site-footer">
  <div class="container footer-grid">
    <div><h3>Amici <span class="accent">de</span> Gusto</h3><p>Authentic Italian dining in the heart of Pokhara since 1972.</p></div>
    <div><h4>Contact</h4><ul><li>Pokhara, Lakeside, Nepal</li><li>+977 01-4567890</li><li>namaste@amicidegusto.com.np</li></ul></div>
    <div><h4>Hours</h4><ul><li>Tuesday - Sunday</li><li>12:00 - 23:00</li><li style="opacity:.6;">Closed Mondays</li></ul></div>
  </div>
  <div class="footer-bottom"><div class="container">&copy; 2026 Amici de Gusto &mdash; Crafted with passion in Pokhara , Nepal</div></div>
</footer>

<%-- Script section: load JavaScript that supports page interaction. --%>
<script src="<%= request.getContextPath() %>/js/cart.js"></script>
<script>
  function handleSubmit(e) {
    e.preventDefault();
    document.getElementById('formSuccess').style.display = 'flex';
    e.target.reset();
    setTimeout(function() {
      document.getElementById('formSuccess').style.display = 'none';
    }, 4000);
  }
</script>
</body>
</html>
