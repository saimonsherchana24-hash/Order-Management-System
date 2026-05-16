<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width,initial-scale=1" />
  <title>About Us - Amici de Gusto</title>
  <link rel="icon" href="<%= request.getContextPath() %>/Resource/favicon.svg" type="image/svg+xml">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/About.css" />
</head>
<body>

<!-- ── NAVBAR ── -->
<header class="site-header">
  <div class="header-inner">
    <a href="<%= request.getContextPath() %>/menu" class="brand">Amici <span class="de">de</span> Gusto</a>
    <nav class="header-nav">
      <a href="<%= request.getContextPath() %>/menu"             class="nav-link">Menu</a>
      <a href="<%= request.getContextPath() %>/page/About.jsp"   class="nav-link active">About Us</a>
      <a href="<%= request.getContextPath() %>/page/Contact.jsp" class="nav-link">Contact</a>
    </nav>
    <div class="header-actions">
      <a href="<%= request.getContextPath() %>/profile" class="icon-btn" aria-label="Profile" title="Profile">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/>
        </svg>
      </a>
      <a href="<%= request.getContextPath() %>/cart" class="icon-btn" aria-label="Cart">
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

  <!-- ── PAGE HEADER ── -->
  <section class="page-hero">
    <div class="container">
      <p class="eyebrow">Our Team</p>
      <h1>Meet the Developers</h1>
      <p class="lead">Five students who designed and built this Order Management System for the Advanced Programming Techniques coursework.</p>
    </div>
  </section>

  <!-- ── MEMBERS ── -->
  <section class="members-section">
    <div class="container">

      <!-- Member 1 — photo left -->
      <div class="member-row">
        <div class="member-photo-wrap">
          <img src="<%= request.getContextPath() %>/Resource/team/member1.jpg" alt="Member 1"
               class="member-photo"
               onerror="this.style.display='none';this.nextElementSibling.style.display='flex';" />
          <div class="member-initial" style="display:none;">A</div>
        </div>
        <div class="member-details">
          <h2>Member Name 1</h2>
          <span class="member-role">Full Stack Developer</span>
          <div class="member-divider"></div>
          <dl class="member-dl">
            <div><dt>Student ID</dt><dd>STU001</dd></div>
            <div><dt>Contribution</dt><dd>Backend servlet architecture, database design, order management logic</dd></div>
            <div><dt>Technologies</dt><dd>Java, JSP, MySQL, Apache Tomcat</dd></div>
          </dl>
          <p class="member-bio">Write a short bio or personal note about this member here. Describe their role in the project and what they learned.</p>
        </div>
      </div>

      <!-- Member 2 — photo right -->
      <div class="member-row reverse">
        <div class="member-photo-wrap">
          <img src="<%= request.getContextPath() %>/Resource/team/member2.jpg" alt="Member 2"
               class="member-photo"
               onerror="this.style.display='none';this.nextElementSibling.style.display='flex';" />
          <div class="member-initial" style="display:none;">B</div>
        </div>
        <div class="member-details">
          <h2>Member Name 2</h2>
          <span class="member-role">Frontend Developer</span>
          <div class="member-divider"></div>
          <dl class="member-dl">
            <div><dt>Student ID</dt><dd>STU002</dd></div>
            <div><dt>Contribution</dt><dd>UI design, CSS styling, responsive layouts across all pages</dd></div>
            <div><dt>Technologies</dt><dd>HTML, CSS, JavaScript, JSP</dd></div>
          </dl>
          <p class="member-bio">Write a short bio or personal note about this member here. Describe their role in the project and what they learned.</p>
        </div>
      </div>

      <!-- Member 3 — photo left -->
      <div class="member-row">
        <div class="member-photo-wrap">
          <img src="<%= request.getContextPath() %>/Resource/team/member3.jpg" alt="Member 3"
               class="member-photo"
               onerror="this.style.display='none';this.nextElementSibling.style.display='flex';" />
          <div class="member-initial" style="display:none;">C</div>
        </div>
        <div class="member-details">
          <h2>Member Name 3</h2>
          <span class="member-role">Database Engineer</span>
          <div class="member-divider"></div>
          <dl class="member-dl">
            <div><dt>Student ID</dt><dd>STU003</dd></div>
            <div><dt>Contribution</dt><dd>Relational database schema, SQL queries, DAO layer implementation</dd></div>
            <div><dt>Technologies</dt><dd>MySQL, JDBC, SQL</dd></div>
          </dl>
          <p class="member-bio">Write a short bio or personal note about this member here. Describe their role in the project and what they learned.</p>
        </div>
      </div>

      <!-- Member 4 — photo right -->
      <div class="member-row reverse">
        <div class="member-photo-wrap">
          <img src="<%= request.getContextPath() %>/Resource/team/member4.jpg" alt="Member 4"
               class="member-photo"
               onerror="this.style.display='none';this.nextElementSibling.style.display='flex';" />
          <div class="member-initial" style="display:none;">D</div>
        </div>
        <div class="member-details">
          <h2>Member Name 4</h2>
          <span class="member-role">Backend Developer</span>
          <div class="member-divider"></div>
          <dl class="member-dl">
            <div><dt>Student ID</dt><dd>STU004</dd></div>
            <div><dt>Contribution</dt><dd>Authentication system, session management, admin panel functionality</dd></div>
            <div><dt>Technologies</dt><dd>Java Servlets, BCrypt, Session API</dd></div>
          </dl>
          <p class="member-bio">Write a short bio or personal note about this member here. Describe their role in the project and what they learned.</p>
        </div>
      </div>

      <!-- Member 5 — photo left -->
      <div class="member-row">
        <div class="member-photo-wrap">
          <img src="<%= request.getContextPath() %>/Resource/team/member5.jpg" alt="Member 5"
               class="member-photo"
               onerror="this.style.display='none';this.nextElementSibling.style.display='flex';" />
          <div class="member-initial" style="display:none;">E</div>
        </div>
        <div class="member-details">
          <h2>Member Name 5</h2>
          <span class="member-role">QA &amp; Documentation</span>
          <div class="member-divider"></div>
          <dl class="member-dl">
            <div><dt>Student ID</dt><dd>STU005</dd></div>
            <div><dt>Contribution</dt><dd>Testing, bug tracking, project documentation, deployment configuration</dd></div>
            <div><dt>Technologies</dt><dd>Maven, Tomcat, Git</dd></div>
          </dl>
          <p class="member-bio">Write a short bio or personal note about this member here. Describe their role in the project and what they learned.</p>
        </div>
      </div>

    </div>
  </section>

  <!-- ── PROJECT SECTION ── -->
  <section class="project-section">
    <div class="container center">
      <p class="eyebrow">The Project</p>
      <h2>Order Management System</h2>
      <p class="lead">Built with Java Servlets, JSP, MySQL, and Apache Tomcat as part of the Advanced Programming Techniques module. Supports user registration, menu browsing, cart management, order placement, real-time tracking, and a full admin panel.</p>
    </div>
  </section>

</main>

<footer class="site-footer">
  <div class="container footer-grid">
    <div><h3>Amici <span class="accent">de</span> Gusto</h3><p>Authentic Italian dining in the heart of Kathmandu since 1972.</p></div>
    <div><h4>Contact</h4><ul><li>Pokhara, Lakeside, Nepal</li><li>+977 01-4567890</li><li>namaste@amicidegusto.com.np</li></ul></div>
    <div><h4>Hours</h4><ul><li>Tuesday - Sunday</li><li>12:00 - 23:00</li><li style="opacity:.6;">Closed Mondays</li></ul></div>
  </div>
  <div class="footer-bottom"><div class="container">&copy; 2026 Amici de Gusto &mdash; Crafted with passion in Kathmandu, Nepal</div></div>
</footer>

<script src="<%= request.getContextPath() %>/js/cart.js"></script>
</body>
</html>
