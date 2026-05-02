<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width,initial-scale=1" />
  <title>Your Cart · Amici de Gusto</title>

  <!-- FIXED PATHS -->
  <link rel="stylesheet" href="../css/base.css" />
  <link rel="stylesheet" href="../css/cart.css" />
</head>
<body>
  <header class="site-header">
    <div class="container header-inner">
      <a href="home.jsp" class="brand">Amici <span class="de">de</span> Gusto</a>
      <div class="header-actions">
        <a href="profile.html" class="icon-btn" aria-label="Profile" title="Profile">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/>
          </svg>
        </a>
        <a href="cart.html" class="icon-btn" aria-label="Cart">
          <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <circle cx="9" cy="21" r="1"/><circle cx="20" cy="21" r="1"/>
            <path d="M1 1h4l2.7 13.4a2 2 0 0 0 2 1.6h9.7a2 2 0 0 0 2-1.6L23 6H6"/>
          </svg>
        </a>
      </div>
    </div>
  </header>

  <main>
    <div class="cart-wrap">
      <div class="container">
        <div class="cart-head">
          <a href="menu.jsp" class="back-link">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M19 12H5M12 19l-7-7 7-7"/>
            </svg>
            Continue shopping
          </a>
          <p class="eyebrow">Il Tuo Ordine</p>
          <h1>Your Cart</h1>
        </div>

        <div class="cart-grid">
          <ul class="cart-list">

            <!-- FIXED IMAGE PATH -->
            <li class="cart-item animate-fade-in-up">
              <img src="../resource/pizza.jpg" alt="Margherita Pizza" loading="lazy" />
              <div class="info">
                <div class="top">
                  <div style="min-width:0;">
                    <h3>Margherita Pizza</h3>
                    <p class="desc">San Marzano tomatoes, fresh buffalo mozzarella, basil, extra virgin olive oil.</p>
                  </div>
                </div>
                <div class="bottom">
                  <div class="qty">
                    <a href="#">−</a>
                    <span class="count">2</span>
                    <a href="#">+</a>
                  </div>
                  <div class="line-total">NPR 1,700</div>
                </div>
              </div>
            </li>

            <!-- FIXED IMAGE PATH -->
            <li class="cart-item animate-fade-in-up">
              <img src="../resource/tiramisu.jpg" alt="Tiramisù" loading="lazy" />
              <div class="info">
                <div class="top">
                  <div style="min-width:0;">
                    <h3>Tiramisù</h3>
                    <p class="desc">Espresso-soaked ladyfingers layered with mascarpone cream and cocoa.</p>
                  </div>
                </div>
                <div class="bottom">
                  <div class="qty">
                    <a href="#">−</a>
                    <span class="count">1</span>
                    <a href="#">+</a>
                  </div>
                  <div class="line-total">NPR 480</div>
                </div>
              </div>
            </li>

          </ul>

          <aside class="summary">
            <h2>Order Summary</h2>
            <dl>
              <div><dt>Subtotal</dt><dd>NPR 2,180</dd></div>
              <div><dt>Delivery</dt><dd>NPR 150</dd></div>
              <div class="total"><dt>Total</dt><dd>NPR 2,330</dd></div>
            </dl>
            <a href="home.jsp" class="btn btn-gold btn-block">Place Order</a>
            <p class="note">Secure checkout · Free delivery over NPR 5,000</p>
          </aside>
        </div>
      </div>
    </div>
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
          <li>Tuesday – Sunday</li>
          <li>12:00 – 23:00</li>
          <li style="opacity:.6;">Closed Mondays</li>
        </ul>
      </div>
    </div>

    <div class="footer-bottom">
      <div class="container">&copy; 2026 Amici de Gusto · Crafted with passion in Kathmandu, Nepal</div>
    </div>
  </footer>
</body>
</html>