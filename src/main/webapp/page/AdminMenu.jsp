<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Amici De Gusto – Menu Management</title>

<link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@400;600;700&family=Lato:wght@300;400;700&display=swap" rel="stylesheet">

<!-- LINK CSS FILE -->
<link rel="stylesheet" href="../css/AdminMenu.css">

</head>
<body>

<aside class="sidebar">
  <div class="logo-wrap">
    <div class="logo-icon">🍽️</div>
    <div class="logo-name">Amici<br>De Gusto</div>
    <div class="logo-sub">Italian Restaurant</div>
  </div>
  <div class="ornament">◆</div>
  <nav>
    <a class="nav-item" href="AdminDashboard.jsp"><span class="nav-icon">🏠</span> Dashboard</a>
    <a class="nav-item" href="order.html"><span class="nav-icon">📋</span> Order Management</a>
    <a class="nav-item active" href="AdminMenu.html"><span class="nav-icon">🍴</span> Menu Management</a>
    <a class="nav-item" href="AdminBilling.jsp"><span class="nav-icon">🧾</span> Billing System</a>
  </nav>
</aside>

<main class="main">
  <div class="topbar">
    <div class="page-title">
      <span class="page-title-icon">🍴</span>
      <h1>Menu Management</h1>
    </div>
    <button class="admin-btn"><div class="admin-avatar">👤</div> Admin ▾</button>
  </div>

  <div class="content">
    <div class="sub-header">
      <p>Manage your restaurant menu items</p>

      <!-- UPDATED BUTTON -->
      <a href="#popupForm" class="add-btn">＋ Add New Item</a>

    </div>

    <div class="grid">
      <div class="table-card">
        <div class="table-header">
          <div class="table-title">≡ Menu Items</div>
          <div class="search-wrap">
            <input type="text" placeholder="Search items...">
            <span style="color:var(--text-light);">🔍</span>
          </div>
        </div>

        <table>
          <thead>
            <tr>
              <th>Image</th><th>Item Name</th><th>Category</th><th>Price (Rs.)</th><th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td><div class="item-img">🍕</div></td>
              <td><strong>Margherita Pizza</strong></td>
              <td><span class="badge badge-pizza">Pizza</span></td>
              <td>Rs. 660.00</td>
              <td><div class="action-btns"><button class="btn-edit">Edit</button><button class="btn-delete">Delete</button></div></td>
            </tr>
            <tr>
              <td><div class="item-img">🍝</div></td>
              <td><strong>Spaghetti Carbonara</strong></td>
              <td><span class="badge badge-pasta">Pasta</span></td>
              <td>Rs. 450.00</td>
              <td><div class="action-btns"><button class="btn-edit">Edit</button><button class="btn-delete">Delete</button></div></td>
            </tr>
            <tr>
              <td><div class="item-img">🫕</div></td>
              <td><strong>Lasagna</strong></td>
              <td><span class="badge badge-pasta">Pasta</span></td>
              <td>Rs. 330.00</td>
              <td><div class="action-btns"><button class="btn-edit">Edit</button><button class="btn-delete">Delete</button></div></td>
            </tr>
            <tr>
              <td><div class="item-img">🍰</div></td>
              <td><strong>Tiramisu</strong></td>
              <td><span class="badge badge-dessert">Dessert</span></td>
              <td>Rs. 660.00</td>
              <td><div class="action-btns"><button class="btn-edit">Edit</button><button class="btn-delete">Delete</button></div></td>
            </tr>
            <tr>
              <td><div class="item-img">☕</div></td>
              <td><strong>Espresso</strong></td>
              <td><span class="badge badge-drinks">Drinks</span></td>
              <td>Rs. 300.00</td>
              <td><div class="action-btns">
                <button class="btn-edit">Edit</button>
                <button class="btn-delete">Delete</button></div></td>
            </tr>
          </tbody>
        </table>

        <div class="pagination">
          <span class="pagination-info">Showing 1 to 5 of 25 items</span>
          <div class="pagination-btns">
            <button class="pg-btn">‹</button>
            <button class="pg-btn active">1</button>
            <button class="pg-btn">2</button>
            <button class="pg-btn">3</button>
            <button class="pg-btn">…</button>
            <button class="pg-btn">5</button>
            <button class="pg-btn">›</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</main>

<!-- POPUP FORM -->
<div id="popupForm" class="popup-overlay">
  <div class="popup-box">

    <a href="#" class="popup-close">✕</a>

    <div class="form-header">
      <span class="form-header-icon">🍽️</span>
      <h2>Add New Item</h2>
    </div>

    <div class="form-group">
      <label>Item Name</label>
      <input type="text" placeholder="Enter item name">
    </div>

    <div class="form-group">
      <label>Category</label>
      <div class="select-wrap">
        <select>
          <option disabled selected>Select category</option>
          <option>Food</option>
          <option>Dessert</option>
          <option>Drinks</option>
        </select>
      </div>
    </div>

    <div class="form-group">
      <label>Price (Rs.)</label>
      <input type="number" placeholder="Enter price">
    </div>

    <div class="form-group">
      <label>Upload Image</label>
      <input type="file">
    </div>

    <div class="form-group">
      <label>Description</label>
      <textarea placeholder="Enter item description"></textarea>
    </div>

    <button class="save-btn">💾 Save Item</button>
    <a href="#" class="cancel-btn">Cancel</a>

  </div>
</div>

</body>
</html>
