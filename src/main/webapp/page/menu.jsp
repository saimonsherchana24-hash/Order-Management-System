<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width,initial-scale=1" />
  <title>Our Menu - Amici de Gusto</title>
  <link rel="icon" href="../Resource/favicon.svg" type="image/svg+xml">
  <meta name="description" content="Browse the full Amici de Gusto menu - savory plates, curated drinks, and sweet endings." />
  <link rel="stylesheet" href="../css/menu.css" />
</head>
<body>
<header class="site-header">
  <div class="container header-inner">
    <a href="<%= request.getContextPath() %>/menu" class="brand">Amici <span class="de">de</span> Gusto</a>
    <div class="header-actions">
      <a href="<%= request.getContextPath() %>/profile" class="icon-btn" aria-label="Profile" title="Profile">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
          <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/>
        </svg>
      </a>
      <a href=""<%= request.getContextPath() %>/cart" class="icon-btn cart-icon" aria-label="Cart">
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
        <article class="menu-card" data-cat="food">
          <div class="img-wrap"><img src="../Resource/pizza.jpg" alt="Margherita Pizza" loading="lazy" width="800" height="600" /></div>
          <div class="body"><div class="row"><h3>Margherita Pizza</h3><span class="price">NPR 850</span></div><p>San Marzano tomatoes, fresh buffalo mozzarella, basil, extra virgin olive oil.</p><button class="btn btn-gold add-to-cart" type="button" data-id="pizza" data-name="Margherita Pizza" data-price="850" data-img="../Resource/pizza.jpg" data-desc="San Marzano tomatoes, fresh buffalo mozzarella, basil, extra virgin olive oil.">+ Add to Cart</button></div>
        </article>
        <article class="menu-card" data-cat="food">
          <div class="img-wrap"><img src="../Resource/pasta.jpg" alt="Creamy Alfredo Pasta" loading="lazy" width="800" height="600" /></div>
          <div class="body"><div class="row"><h3>Creamy Alfredo Pasta</h3><span class="price">NPR 920</span></div><p>Hand-rolled fettuccine in a velvet parmesan and butter sauce.</p><button class="btn btn-gold add-to-cart" type="button" data-id="pasta" data-name="Creamy Alfredo Pasta" data-price="920" data-img="../Resource/pasta.jpg" data-desc="Hand-rolled fettuccine in a velvet parmesan and butter sauce.">+ Add to Cart</button></div>
        </article>
        <article class="menu-card" data-cat="food">
          <div class="img-wrap"><img src="../Resource/lasagna.jpg" alt="Lasagna Classico" loading="lazy" width="800" height="600" /></div>
          <div class="body"><div class="row"><h3>Lasagna Classico</h3><span class="price">NPR 1,100</span></div><p>Slow-braised beef ragu layered with bechamel and aged pecorino.</p><button class="btn btn-gold add-to-cart" type="button" data-id="lasagna" data-name="Lasagna Classico" data-price="1100" data-img="../Resource/lasagna.jpg" data-desc="Slow-braised beef ragu layered with bechamel and aged pecorino.">+ Add to Cart</button></div>
        </article>
        <article class="menu-card" data-cat="food">
          <div class="img-wrap"><img src="../Resource/risotto.jpg" alt="Mushroom Risotto" loading="lazy" width="800" height="600" /></div>
          <div class="body"><div class="row"><h3>Mushroom Risotto</h3><span class="price">NPR 1,050</span></div><p>Carnaroli rice, wild porcini, white wine, and shaved Grana Padano.</p><button class="btn btn-gold add-to-cart" type="button" data-id="risotto" data-name="Mushroom Risotto" data-price="1050" data-img="../Resource/risotto.jpg" data-desc="Carnaroli rice, wild porcini, white wine, and shaved Grana Padano.">+ Add to Cart</button></div>
        </article>
        <article class="menu-card" data-cat="food">
          <div class="img-wrap"><img src="../Resource/gnocchi.jpg" alt="Gnocchi al Pesto" loading="lazy" width="800" height="600" /></div>
          <div class="body"><div class="row"><h3>Gnocchi al Pesto</h3><span class="price">NPR 880</span></div><p>Pillowy potato gnocchi tossed in fragrant Genovese basil pesto.</p><button class="btn btn-gold add-to-cart" type="button" data-id="gnocchi" data-name="Gnocchi al Pesto" data-price="880" data-img="../Resource/gnocchi.jpg" data-desc="Pillowy potato gnocchi tossed in fragrant Genovese basil pesto.">+ Add to Cart</button></div>
        </article>
        <article class="menu-card" data-cat="food">
          <div class="img-wrap"><img src="../Resource/caprese.jpg" alt="Caprese Salad" loading="lazy" width="800" height="600" /></div>
          <div class="body"><div class="row"><h3>Caprese Salad</h3><span class="price">NPR 720</span></div><p>Heirloom tomatoes, buffalo mozzarella, basil, balsamic reduction.</p><button class="btn btn-gold add-to-cart" type="button" data-id="caprese" data-name="Caprese Salad" data-price="720" data-img="../Resource/caprese.jpg" data-desc="Heirloom tomatoes, buffalo mozzarella, basil, balsamic reduction.">+ Add to Cart</button></div>
        </article>
        <article class="menu-card" data-cat="food">
          <div class="img-wrap"><img src="../Resource/bruschetta.jpg" alt="Bruschetta al Pomodoro" loading="lazy" width="800" height="600" /></div>
          <div class="body"><div class="row"><h3>Bruschetta al Pomodoro</h3><span class="price">NPR 540</span></div><p>Toasted sourdough topped with tomato, garlic, basil and olive oil.</p><button class="btn btn-gold add-to-cart" type="button" data-id="bruschetta" data-name="Bruschetta al Pomodoro" data-price="540" data-img="../Resource/bruschetta.jpg" data-desc="Toasted sourdough topped with tomato, garlic, basil and olive oil.">+ Add to Cart</button></div>
        </article>
        <article class="menu-card" data-cat="drinks">
          <div class="img-wrap"><img src="../Resource/red-wine.jpg" alt="Chianti Classico" loading="lazy" width="800" height="600" /></div>
          <div class="body"><div class="row"><h3>Chianti Classico</h3><span class="price">NPR 650</span></div><p>Tuscan Sangiovese, notes of cherry, leather, and warm spice.</p><button class="btn btn-gold add-to-cart" type="button" data-id="red-wine" data-name="Chianti Classico" data-price="650" data-img="../Resource/red-wine.jpg" data-desc="Tuscan Sangiovese, notes of cherry, leather, and warm spice.">+ Add to Cart</button></div>
        </article>
        <article class="menu-card" data-cat="drinks">
          <div class="img-wrap"><img src="../Resource/white-wine.jpg" alt="Pinot Grigio" loading="lazy" width="800" height="600" /></div>
          <div class="body"><div class="row"><h3>Pinot Grigio</h3><span class="price">NPR 600</span></div><p>Crisp Veneto white with green apple and citrus finish.</p><button class="btn btn-gold add-to-cart" type="button" data-id="white-wine" data-name="Pinot Grigio" data-price="600" data-img="../Resource/white-wine.jpg" data-desc="Crisp Veneto white with green apple and citrus finish.">+ Add to Cart</button></div>
        </article>
        <article class="menu-card" data-cat="drinks">
          <div class="img-wrap"><img src="../Resource/water.jpg" alt="Acqua Frizzante" loading="lazy" width="800" height="600" /></div>
          <div class="body"><div class="row"><h3>Acqua Frizzante</h3><span class="price">NPR 220</span></div><p>Sparkling mineral water with a slice of Amalfi lemon.</p><button class="btn btn-gold add-to-cart" type="button" data-id="water" data-name="Acqua Frizzante" data-price="220" data-img="../Resource/water.jpg" data-desc="Sparkling mineral water with a slice of Amalfi lemon.">+ Add to Cart</button></div>
        </article>
        <article class="menu-card" data-cat="drinks">
          <div class="img-wrap"><img src="../Resource/espresso.jpg" alt="Espresso" loading="lazy" width="800" height="600" /></div>
          <div class="body"><div class="row"><h3>Espresso</h3><span class="price">NPR 180</span></div><p>Single-origin Italian roast, pulled to perfection.</p><button class="btn btn-gold add-to-cart" type="button" data-id="espresso" data-name="Espresso" data-price="180" data-img="../Resource/espresso.jpg" data-desc="Single-origin Italian roast, pulled to perfection.">+ Add to Cart</button></div>
        </article>
        <article class="menu-card" data-cat="drinks">
          <div class="img-wrap"><img src="../Resource/aperol.jpg" alt="Aperol Spritz" loading="lazy" width="800" height="600" /></div>
          <div class="body"><div class="row"><h3>Aperol Spritz</h3><span class="price">NPR 520</span></div><p>Aperol, prosecco and a splash of soda over ice with orange.</p><button class="btn btn-gold add-to-cart" type="button" data-id="aperol" data-name="Aperol Spritz" data-price="520" data-img="../Resource/aperol.jpg" data-desc="Aperol, prosecco and a splash of soda over ice with orange.">+ Add to Cart</button></div>
        </article>
        <article class="menu-card" data-cat="drinks">
          <div class="img-wrap"><img src="../Resource/aperol.jpg" alt="Aperol Spritz" loading="lazy" width="800" height="600" /></div>
          <div class="body"><div class="row"><h3>Aperol Spritz</h3><span class="price">NPR 520</span></div><p>Aperol, prosecco and a splash of soda over ice with orange.</p><button class="btn btn-gold add-to-cart" type="button" data-id="aperol" data-name="Aperol Spritz" data-price="520" data-img="../Resource/aperol.jpg" data-desc="Aperol, prosecco and a splash of soda over ice with orange.">+ Add to Cart</button></div>
        </article>
        <article class="menu-card" data-cat="drinks">
          <div class="img-wrap"><img src="../Resource/limoncello.jpg" alt="Limoncello" loading="lazy" width="800" height="600" /></div>
          <div class="body"><div class="row"><h3>Limoncello</h3><span class="price">NPR 380</span></div><p>Chilled Amalfi-coast lemon liqueur, bright and refreshing.</p><button class="btn btn-gold add-to-cart" type="button" data-id="limoncello" data-name="Limoncello" data-price="380" data-img="../Resource/limoncello.jpg" data-desc="Chilled Amalfi-coast lemon liqueur, bright and refreshing.">+ Add to Cart</button></div>
        </article>
        <article class="menu-card" data-cat="dessert">
          <div class="img-wrap"><img src="../Resource/tiramisu.jpg" alt="Tiramisu" loading="lazy" width="800" height="600" /></div>
          <div class="body"><div class="row"><h3>Tiramisu</h3><span class="price">NPR 480</span></div><p>Espresso-soaked ladyfingers layered with mascarpone cream and cocoa.</p><button class="btn btn-gold add-to-cart" type="button" data-id="tiramisu" data-name="Tiramisu" data-price="480" data-img="../Resource/tiramisu.jpg" data-desc="Espresso-soaked ladyfingers layered with mascarpone cream and cocoa.">+ Add to Cart</button></div>
        </article>
        <article class="menu-card" data-cat="dessert">
          <div class="img-wrap"><img src="../Resource/panna-cotta.jpg" alt="Panna Cotta" loading="lazy" width="800" height="600" /></div>
          <div class="body"><div class="row"><h3>Panna Cotta</h3><span class="price">NPR 450</span></div><p>Silky vanilla cream set with gelatin, finished with berry compote.</p><button class="btn btn-gold add-to-cart" type="button" data-id="panna-cotta" data-name="Panna Cotta" data-price="450" data-img="../Resource/panna-cotta.jpg" data-desc="Silky vanilla cream set with gelatin, finished with berry compote.">+ Add to Cart</button></div>
        </article>
        <article class="menu-card" data-cat="dessert">
          <div class="img-wrap"><img src="../Resource/cannoli.jpg" alt="Sicilian Cannoli" loading="lazy" width="800" height="600" /></div>
          <div class="body"><div class="row"><h3>Sicilian Cannoli</h3><span class="price">NPR 460</span></div><p>Crisp pastry shells filled with sweet ricotta and pistachio crumbs.</p><button class="btn btn-gold add-to-cart" type="button" data-id="cannoli" data-name="Sicilian Cannoli" data-price="460" data-img="../Resource/cannoli.jpg" data-desc="Crisp pastry shells filled with sweet ricotta and pistachio crumbs.">+ Add to Cart</button></div>
        </article>
        <article class="menu-card" data-cat="dessert">
          <div class="img-wrap"><img src="../Resource/gelato.jpg" alt="Artisan Gelato" loading="lazy" width="800" height="600" /></div>
          <div class="body"><div class="row"><h3>Artisan Gelato</h3><span class="price">NPR 420</span></div><p>Two scoops of house-churned gelato - pistachio and dark chocolate.</p><button class="btn btn-gold add-to-cart" type="button" data-id="gelato" data-name="Artisan Gelato" data-price="420" data-img="../Resource/gelato.jpg" data-desc="Two scoops of house-churned gelato - pistachio and dark chocolate.">+ Add to Cart</button></div>
        </article>
      </div>
    </div>
  </section>
</main>
<footer class="site-footer">
  <div class="container footer-grid">
    <div><h3>Amici <span class="accent">de</span> Gusto</h3><p>Authentic Italian dining in the heart of Kathmandu since 1972. Handcrafted pasta, wood-fired pizzas, and curated wines.</p></div>
    <div><h4>Contact</h4><ul><li>Thamel Marg, Kathmandu, Nepal</li><li>+977 01-4567890</li><li>namaste@amicidegusto.com.np</li></ul></div>
    <div><h4>Hours</h4><ul><li>Tuesday - Sunday</li><li>12:00 - 23:00</li><li style="opacity:.6;">Closed Mondays</li></ul></div>
  </div>
  <div class="footer-bottom"><div class="container">&copy; 2026 Amici de Gusto - Crafted with passion in Kathmandu, Nepal</div></div>
</footer>
<script src="../js/cart.js"></script>
</body>
</html>