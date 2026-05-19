/* adminMenu.js — Admin Menu Management page scripts */

/* ── Live search filter ── */
document.addEventListener('DOMContentLoaded', function () {
    var searchInput = document.getElementById('menuSearch');
    if (!searchInput) return;

    searchInput.addEventListener('input', function () {
        var query = this.value.toLowerCase().trim();
        var rows = document.querySelectorAll('tbody tr[data-name]');
        var visibleCount = 0;

        rows.forEach(function (row) {
            var name     = row.getAttribute('data-name') || '';
            var category = row.getAttribute('data-category') || '';
            var matches  = name.includes(query) || category.includes(query);
            row.style.display = matches ? '' : 'none';
            if (matches) visibleCount++;
        });

        // Update count label if it exists
        var info = document.querySelector('.pagination-info');
        if (info) {
            info.textContent = query
                ? 'Showing ' + visibleCount + ' result' + (visibleCount !== 1 ? 's' : '') + ' for "' + query + '"'
                : 'Showing all items';
        }
    });
});

/* ── Edit popup ── */
function openEdit(id, name, category, price, description, imageUrl) {
    document.getElementById('editItemId').value        = id;
    document.getElementById('editItemName').value      = name;
    document.getElementById('editCategory').value      = category;
    document.getElementById('editPrice').value         = price;
    document.getElementById('editDescription').value   = description;
    document.getElementById('editExistingImage').value = imageUrl;

    var imgLabel = document.getElementById('editCurrentImg');
    if (imageUrl) {
        imgLabel.textContent = 'Current: ' + imageUrl.split('/').pop();
    } else {
        imgLabel.textContent = 'No image set';
    }

    window.location.hash = 'editPopup';
}
