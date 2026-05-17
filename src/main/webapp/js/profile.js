/* profile.js — shared JS for AdminProfile and UserProfile pages */

/* Auto-remove toast after 3 seconds */
(function () {
    var toast = document.getElementById('toast');
    if (toast) {
        setTimeout(function () {
            toast.remove();
        }, 3000);
    }
})();
