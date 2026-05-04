<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, aptProject.model.MenuItem, aptProject.model.User" %>
<% User adminUser = (User) session.getAttribute("user");
   String adminInitial = (adminUser != null && adminUser.getFullName() != null)
                         ? adminUser.getFullName().substring(0,1).toUpperCase() : "A";
   String adminName = (adminUser != null) ? adminUser.getFullName() : "Admin"; %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Amici De Gusto – Menu Management</title>
  <link rel="icon" href="../Resource/favicon.svg" type="image/svg+xml">

  <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@400;600;700&family=Lato:wght@300;400;700&display=swap" rel="stylesheet">

  <link rel="stylesheet" href="../css/AdminMenu.css">
  <style>
    /* Popups hidden by default, shown only when their id is the URL hash */
    .popup-overlay        { display: none; }
    .popup-overlay:target { display: flex; }
  </style>
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
    <a class="nav-item" href="<%= request.getContextPath() %>/admin/dashboard"><span class="nav-icon">🏠</span> Dashboard</a>
    <a class="nav-item" href="<%= request.getContextPath() %>/admin/orders"><span class="nav-icon">📋</span> Order Management</a>
    <a class="nav-item active" href="<%= request.getContextPath() %>/admin/menu"><span class="nav-icon">🍴</span> Menu Management</a>
    <a class="nav-item" href="<%= request.getContextPath() %>/admin/billing"><span class="nav-icon">🧾</span> Billing System</a>
    <a class="nav-item" href="<%= request.getContextPath() %>/logout"><span class="nav-icon">🚪</span> Logout</a>
  </nav>
</aside>

<main class="main">
  <div class="topbar">
    <div class="page-title">
      <span class="page-title-icon">🍴</span>
      <h1>Menu Management</h1>
    </div>

    <a href="<%= request.getContextPath() %>/admin/profile" class="admin-profile-link">
      <div class="admin-avatar"><%= adminInitial %></div>
      <div class="admin-profile-info">
        <span class="admin-profile-name"><%= adminName %></span>
        <span class="admin-profile-role">Administrator</span>
      </div>
    </a>
  </div>

  <div class="content">
    <div class="sub-header">
      <p>Manage your restaurant menu items</p>
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
            <th>Image</th>
            <th>Item Name</th>
            <th>Category</th>
            <th>Price (Rs.)</th>
            <th>Actions</th>
          </tr>
          </thead>

          <tbody>
          <%
            List<MenuItem> menuItems = (List<MenuItem>) request.getAttribute("menuItems");
            if (menuItems != null) {
              for (MenuItem item : menuItems) {
          %>
          <tr>
            <td><div class="item-img">🍽️</div></td>
            <td><strong><%= item.getName() %></strong></td>
            <td><span class="badge"><%= item.getCategory() %></span></td>
            <td>Rs. <%= String.format("%.2f", item.getPrice()) %></td>
            <td>
              <div class="action-btns">
                <!-- Edit: open popup pre-filled -->
                <button class="btn-edit" onclick="openEdit(<%= item.getId() %>,'<%= item.getName() %>','<%= item.getCategory() %>',<%= item.getPrice() %>,'<%= item.getDescription() %>','<%= item.getImageUrl() %>')">Edit</button>
                <!-- Delete: small form POST -->
                <form method="post" action="<%= request.getContextPath() %>/admin/menu/delete" style="display:inline">
                  <input type="hidden" name="itemId" value="<%= item.getId() %>">
                  <button type="submit" class="btn-delete" onclick="return confirm('Delete this item?')">Delete</button>
                </form>
              </div>
            </td>
          </tr>
          <% } } %>
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

<div id="popupForm" class="popup-overlay">
  <div class="popup-box">
    <a href="#" class="popup-close">✕</a>
    <div class="form-header">
      <span class="form-header-icon">🍽️</span>
      <h2>Add New Item</h2>
    </div>
    <form method="post" action="<%= request.getContextPath() %>/admin/menu/add">
      <div class="form-group">
        <label>Item Name</label>
        <input type="text" name="itemName" placeholder="Enter item name" required>
      </div>
      <div class="form-group">
        <label>Category</label>
        <div class="select-wrap">
          <select name="category" required>
            <option disabled selected>Select category</option>
            <option value="food">Food</option>
            <option value="dessert">Dessert</option>
            <option value="drinks">Drinks</option>
          </select>
        </div>
      </div>
      <div class="form-group">
        <label>Price (Rs.)</label>
        <input type="number" name="price" placeholder="Enter price" step="0.01" required>
      </div>
      <div class="form-group">
        <label>Image URL</label>
        <input type="text" name="imageUrl" placeholder="e.g. ../Resource/pizza.jpg">
      </div>
      <div class="form-group">
        <label>Description</label>
        <textarea name="description" placeholder="Enter item description"></textarea>
      </div>
      <button type="submit" class="save-btn">💾 Save Item</button>
      <a href="#" class="cancel-btn">Cancel</a>
    </form>
  </div>
</div>

<!-- Edit Item Popup — opened via href="#editPopup" set by openEdit() -->
<div id="editPopup" class="popup-overlay">
  <div class="popup-box">
    <a href="#" class="popup-close">✕</a>
    <div class="form-header">
      <span class="form-header-icon">✏️</span>
      <h2>Edit Item</h2>
    </div>
    <form method="post" action="<%= request.getContextPath() %>/admin/menu/update">
      <input type="hidden" name="itemId" id="editItemId">
      <div class="form-group">
        <label>Item Name</label>
        <input type="text" name="itemName" id="editItemName" required>
      </div>
      <div class="form-group">
        <label>Category</label>
        <div class="select-wrap">
          <select name="category" id="editCategory" required>
            <option value="food">Food</option>
            <option value="dessert">Dessert</option>
            <option value="drinks">Drinks</option>
          </select>
        </div>
      </div>
      <div class="form-group">
        <label>Price (Rs.)</label>
        <input type="number" name="price" id="editPrice" step="0.01" required>
      </div>
      <div class="form-group">
        <label>Image URL</label>
        <input type="text" name="imageUrl" id="editImageUrl">
      </div>
      <div class="form-group">
        <label>Description</label>
        <textarea name="description" id="editDescription"></textarea>
      </div>
      <button type="submit" class="save-btn">💾 Update Item</button>
      <a href="#" class="cancel-btn">Cancel</a>
    </form>
  </div>
</div>

<%-- Edit still needs JS only to pre-fill the form fields with item data --%>
<script>
function openEdit(id, name, category, price, description, imageUrl) {
    document.getElementById('editItemId').value      = id;
    document.getElementById('editItemName').value    = name;
    document.getElementById('editCategory').value    = category;
    document.getElementById('editPrice').value       = price;
    document.getElementById('editDescription').value = description;
    document.getElementById('editImageUrl').value    = imageUrl;
    window.location.hash = 'editPopup'; /* trigger CSS :target to show popup */
}
</script>

</body>
</html>