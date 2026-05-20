/* checkout.js — Checkout page logic */

var CART_KEY = "amiciCart";

function getCart() {
    try { return JSON.parse(localStorage.getItem(CART_KEY)) || []; }
    catch(e) { return []; }
}

function showCart() {
    var cart       = getCart();
    var tbody      = document.getElementById("cartTableBody");
    var total      = document.getElementById("totalDisplay");
    var emptyMsg   = document.getElementById("emptyMsg");
    var placeBtn   = document.getElementById("placeOrderBtn");

    if (cart.length === 0) {
        emptyMsg.style.display = "block";
        placeBtn.style.display = "none";
        return;
    }

    var sum = 0;
    tbody.innerHTML = "";

    for (var i = 0; i < cart.length; i++) {
        var item     = cart[i];
        var subtotal = item.price * item.qty;
        sum += subtotal;

        var row = document.createElement("tr");
        row.innerHTML =
            "<td>" + item.name + "</td>" +
            "<td>" + item.qty  + "</td>" +
            "<td>NPR " + subtotal.toLocaleString() + "</td>";
        tbody.appendChild(row);
    }

    total.textContent = "NPR " + sum.toLocaleString();
}

document.addEventListener("DOMContentLoaded", function () {
    showCart();

    document.getElementById("placeOrderBtn").addEventListener("click", function () {
        var cart = getCart();

        if (cart.length === 0) {
            alert("Your cart is empty!");
            return;
        }

        var total = 0;
        for (var i = 0; i < cart.length; i++) {
            total += cart[i].price * cart[i].qty;
        }

        document.getElementById("formTotal").value     = total.toFixed(2);
        document.getElementById("formNote").value      = document.getElementById("specialNote").value;
        document.getElementById("formItemCount").value = cart.length;

        var container = document.getElementById("formItems");
        container.innerHTML = "";
        for (var j = 0; j < cart.length; j++) {
            var item = cart[j];
            container.innerHTML +=
                "<input type='hidden' name='itemId_"    + j + "' value='" + item.id    + "'>" +
                "<input type='hidden' name='itemName_"  + j + "' value='" + item.name  + "'>" +
                "<input type='hidden' name='itemPrice_" + j + "' value='" + item.price + "'>" +
                "<input type='hidden' name='itemQty_"   + j + "' value='" + item.qty   + "'>";
        }

        localStorage.removeItem(CART_KEY);
        document.getElementById("orderForm").submit();
    });
});
