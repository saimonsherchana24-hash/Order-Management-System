<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Checkout - Amici de Gusto</title>
<link rel="icon" href="../Resource/favicon.svg" type="image/svg+xml">
<link rel="stylesheet" href="../css/Checkout.css">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
</head>
<body>

<!-- HEADER -->
<header>
    <div class="logo">
        <a href="<%= request.getContextPath() %>/menu" class="home-btn">
            <i class="fa-solid fa-house"></i>
        </a>
        <div class="app-name">Amici <span class="accent">de</span> Gusto</div>
    </div>
    <i class="fa-solid fa-utensils" style="color:#E8C96A;font-size:20px;"></i>
</header>

<div class="title">Checkout</div>

<div class="container">

    <!-- ORDER SUMMARY — filled by JavaScript from localStorage -->
    <div class="box">
        <h3>Order Summary</h3>
        <table>
            <thead>
                <tr>
                    <th>Item</th>
                    <th>Qty</th>
                    <th>Price</th>
                </tr>
            </thead>
            <tbody id="cartTableBody">
                <!-- rows added by JS below -->
            </tbody>
        </table>
        <hr>
        <div class="summary-item total">
            <b>Total</b>
            <b id="totalDisplay">NPR 0.00</b>
        </div>
    </div>

    <!-- SPECIAL NOTE -->
    <div class="box">
        <h3>Special Note</h3>
        <textarea id="specialNote" placeholder="Any special requests? (optional)" rows="3"></textarea>
    </div>

    <!-- Empty cart message -->
    <div id="emptyMsg" style="display:none; text-align:center; padding:20px;">
        <p>Your cart is empty. <a href="<%= request.getContextPath() %>/menu">Browse the menu →</a></p>
    </div>

    <!--
        Hidden form — JavaScript fills this with cart data before submitting.
        The servlet reads: totalPrice, specialNote, itemCount,
        and for each item: itemId_0, itemName_0, itemPrice_0, itemQty_0
    -->
    <form id="orderForm" method="post" action="<%= request.getContextPath() %>/order/place">
        <input type="hidden" name="totalPrice"   id="formTotal">
        <input type="hidden" name="specialNote"  id="formNote">
        <input type="hidden" name="itemCount"    id="formItemCount">
        <!-- item fields added dynamically by JS -->
        <div id="formItems"></div>

        <button type="button" id="placeOrderBtn">
            <i class="fa fa-check"></i> Place Order
        </button>
    </form>

</div>

<script>
    // Read cart from localStorage (same key used in cart.js)
    var CART_KEY = "amiciCart";

    function getCart() {
        try { return JSON.parse(localStorage.getItem(CART_KEY)) || []; }
        catch(e) { return []; }
    }

    // Show cart items in the table
    function showCart() {
        var cart = getCart();
        var tbody = document.getElementById("cartTableBody");
        var totalDisplay = document.getElementById("totalDisplay");
        var emptyMsg = document.getElementById("emptyMsg");
        var placeBtn = document.getElementById("placeOrderBtn");

        if (cart.length === 0) {
            emptyMsg.style.display = "block";
            placeBtn.style.display = "none";
            return;
        }

        var total = 0;
        tbody.innerHTML = "";

        for (var i = 0; i < cart.length; i++) {
            var item = cart[i];
            var subtotal = item.price * item.qty;
            total += subtotal;

            var row = document.createElement("tr");
            row.innerHTML =
                "<td>" + item.name + "</td>" +
                "<td>" + item.qty + "</td>" +
                "<td>NPR " + subtotal.toLocaleString() + "</td>";
            tbody.appendChild(row);
        }

        totalDisplay.textContent = "NPR " + total.toLocaleString();
    }

    // When Place Order is clicked — fill the hidden form and submit
    document.getElementById("placeOrderBtn").addEventListener("click", function() {
        var cart = getCart();

        if (cart.length === 0) {
            alert("Your cart is empty!");
            return;
        }

        var total = 0;
        for (var i = 0; i < cart.length; i++) {
            total += cart[i].price * cart[i].qty;
        }

        // Fill hidden fields
        document.getElementById("formTotal").value     = total.toFixed(2);
        document.getElementById("formNote").value      = document.getElementById("specialNote").value;
        document.getElementById("formItemCount").value = cart.length;

        // Add one hidden input per item
        var container = document.getElementById("formItems");
        container.innerHTML = "";
        for (var j = 0; j < cart.length; j++) {
            var item = cart[j];
            container.innerHTML +=
                "<input type='hidden' name='itemId_"    + j + "' value='0'>" +
                "<input type='hidden' name='itemName_"  + j + "' value='" + item.name  + "'>" +
                "<input type='hidden' name='itemPrice_" + j + "' value='" + item.price + "'>" +
                "<input type='hidden' name='itemQty_"   + j + "' value='" + item.qty   + "'>";
        }

        // Clear cart after placing order
        localStorage.removeItem(CART_KEY);

        // Submit the form to the servlet
        document.getElementById("orderForm").submit();
    });

    // Run on page load
    showCart();
</script>

</body>
</html>
