const CART_KEY = "amiciCart";

function getCart() {
    try {
        return JSON.parse(localStorage.getItem(CART_KEY)) || [];
    } catch (error) {
        return [];
    }
}

function saveCart(cart) {
    localStorage.setItem(CART_KEY, JSON.stringify(cart));
    updateCartCount();
}

function updateCartCount() {
    const totalItems = getCart().reduce((sum, item) => sum + item.qty, 0);

    document.querySelectorAll("[data-cart-count]").forEach((badge) => {
        badge.textContent = totalItems;
        badge.hidden = totalItems === 0;
    });
}

function addItem(item) {
    const cart = getCart();
    const existing = cart.find((cartItem) => cartItem.id === item.id);

    if (existing) {
        existing.qty += 1;
    } else {
        cart.push({ ...item, qty: 1 });
    }

    saveCart(cart);
}

function changeQty(id, amount) {
    const cart = getCart()
        .map((item) => item.id === id ? { ...item, qty: item.qty + amount } : item)
        .filter((item) => item.qty > 0);

    saveCart(cart);
    renderCart();
}

function setupMenuFilter() {
    const tabs = document.querySelectorAll("[data-filter]");
    const cards = document.querySelectorAll(".menu-card");

    tabs.forEach((tab) => {
        tab.addEventListener("click", () => {
            const filter = tab.dataset.filter;

            tabs.forEach((button) => button.classList.remove("active"));
            tab.classList.add("active");

            cards.forEach((card) => {
                const shouldShow = filter === "all" || card.dataset.cat === filter;
                card.hidden = !shouldShow;
            });
        });
    });
}

function setupAddButtons() {
    document.querySelectorAll(".add-to-cart").forEach((button) => {
        button.addEventListener("click", () => {
            addItem({
                id: button.dataset.id,
                name: button.dataset.name,
                price: Number(button.dataset.price),
                img: button.dataset.img,
                desc: button.dataset.desc
            });

            button.textContent = "Added";
            setTimeout(() => {
                button.textContent = "+ Add to Cart";
            }, 700);
        });
    });
}

function renderCart() {
    const list = document.getElementById("cartItems");
    const empty = document.getElementById("emptyCart");
    const actions = document.getElementById("cartActions");

    if (!list || !empty || !actions) {
        return;
    }

    const cart = getCart();
    list.innerHTML = "";
    empty.hidden = cart.length !== 0;
    actions.hidden = cart.length === 0;

    cart.forEach((item) => {
        const row = document.createElement("li");
        row.className = "cart-item animate-fade-in-up";
        row.innerHTML = `
      <img src="${item.img}" alt="${item.name}" loading="lazy" />
      <div class="info">
        <div class="top">
          <div style="min-width:0;">
            <h3>${item.name}</h3>
            <p class="desc">${item.desc}</p>
          </div>
          <div class="line-total">NPR ${(item.price * item.qty).toLocaleString()}</div>
        </div>
        <div class="bottom">
          <div class="qty">
            <button type="button" data-qty-minus="${item.id}" aria-label="Decrease ${item.name}">-</button>
            <span class="count">${item.qty}</span>
            <button type="button" data-qty-plus="${item.id}" aria-label="Increase ${item.name}">+</button>
          </div>
        </div>
      </div>
    `;
        list.appendChild(row);
    });

    list.querySelectorAll("[data-qty-minus]").forEach((button) => {
        button.addEventListener("click", () => changeQty(button.dataset.qtyMinus, -1));
    });

    list.querySelectorAll("[data-qty-plus]").forEach((button) => {
        button.addEventListener("click", () => changeQty(button.dataset.qtyPlus, 1));
    });
}

document.addEventListener("DOMContentLoaded", () => {
    updateCartCount();
    setupMenuFilter();
    setupAddButtons();
    renderCart();
});