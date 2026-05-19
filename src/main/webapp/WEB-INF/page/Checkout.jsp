<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Checkout - Amici de Gusto</title>
    <link rel="icon" href="<%= request.getContextPath() %>/Resource/favicon.svg" type="image/svg+xml">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/Checkout.css">
</head>
<body>

<!-- NAVBAR (EXACT SAME AS CART.JSP) -->
<header class="navbar">
    <a href="<%= request.getContextPath() %>/menu" class="brand">Amici <span class="de">de</span> Gusto</a>
    <div class="nav-end">
        <a href="<%= request.getContextPath() %>/profile" class="icon-btn" aria-label="Profile" title="Profile">
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/>
                <circle cx="12" cy="7" r="4"/>
            </svg>
        </a>
    </div>
</header>

<div class="title">Checkout</div>

<div class="container">

    <!-- ORDER SUMMARY -->
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
            <tbody id="cartTableBody"></tbody>
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

    <!-- Hidden form for servlet -->
    <form id="orderForm" method="post" action="<%= request.getContextPath() %>/order/place">
        <input type="hidden" name="totalPrice"   id="formTotal">
        <input type="hidden" name="specialNote"  id="formNote">
        <input type="hidden" name="itemCount"    id="formItemCount">
        <div id="formItems"></div>

        <button type="button" id="placeOrderBtn">
             Place Order
        </button>
    </form>

</div>

<script src="<%= request.getContextPath() %>/js/checkout.js"></script>
</body>
</html>