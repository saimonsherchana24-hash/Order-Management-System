/* adminMenu.js — Admin Menu Management page scripts */

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
