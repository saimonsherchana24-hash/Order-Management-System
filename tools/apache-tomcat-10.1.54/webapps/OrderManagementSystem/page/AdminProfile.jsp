<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <title>Admin Profile - Amici de Gusto</title>
    <link rel="stylesheet" href="../css/AdminProfile.css" />
</head>
<body>
<main>
    <section class="admin-wrap">
        <div class="container">
            <div class="profile-head">
                <p class="eyebrow">Admin Profile</p>
                <h1>Admin Account</h1>
                <p class="lead">Manage administrator details and review the latest login activity.</p>
            </div>
            <div class="admin-grid">
                <section class="panel admin-panel">
                    <div class="avatar">SR</div>
                    <div class="admin-info">
                        <div class="title-row">
                            <div>
                                <h2 id="displayName">Sophia Russo</h2>
                                <p class="role" id="displayRole">Administrator</p>
                            </div>
                            <button class="btn btn-gold" type="button" id="editToggle">Edit Profile</button>
                        </div>
                        <dl>
                            <div><dt>Name</dt><dd id="detailName">Sophia Russo</dd></div>
                            <div><dt>Role</dt><dd id="detailRole">Administrator</dd></div>
                            <div><dt>Email</dt><dd id="detailEmail">sophia.russo@amicidegusto.com.np</dd></div>
                        </dl>
                    </div>
                </section>
                <section class="panel activity-panel">
                    <div class="section-title">
                        <p class="eyebrow small">Account Activity</p>
                        <h2>Last Login Time</h2>
                    </div>
                    <div class="login-card">
                        <div class="login-icon">
                            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <circle cx="12" cy="12" r="10"/><path d="M12 6v6l4 2"/>
                            </svg>
                        </div>
                        <div>
                            <strong>May 2, 2026 - 23:49</strong>
                            <p>Last successful administrator login.</p>
                        </div>
                    </div>
                </section>
            </div>
            <section class="panel edit-panel" id="editPanel" hidden>
                <div class="section-title">
                    <p class="eyebrow small">Edit Details</p>
                    <h2>Update Admin Profile</h2>
                </div>
                <form class="admin-form" id="adminProfileForm">
                    <label>
                        Admin Name
                        <input type="text" id="adminName" value="Sophia Russo" />
                    </label>
                    <label>
                        Email
                        <input type="email" id="adminEmail" value="sophia.russo@amicidegusto.com.np" />
                    </label>
                    <label>
                        Role
                        <input type="text" id="adminRole" value="Administrator" readonly />
                    </label>
                    <div class="form-actions">
                        <button class="btn btn-gold" type="submit">Save Changes</button>
                        <button class="btn btn-soft" type="button" id="cancelEdit">Cancel</button>
                    </div>
                </form>
            </section>
        </div>
    </section>
</main>
<script>
    const editToggle = document.getElementById("editToggle");
    const editPanel = document.getElementById("editPanel");
    const cancelEdit = document.getElementById("cancelEdit");
    const form = document.getElementById("adminProfileForm");
    editToggle.addEventListener("click", () => {
        editPanel.hidden = false;
        document.getElementById("adminName").focus();
    });
    cancelEdit.addEventListener("click", () => {
        editPanel.hidden = true;
    });
    form.addEventListener("submit", (event) => {
        event.preventDefault();
        const name = document.getElementById("adminName").value.trim();
        const email = document.getElementById("adminEmail").value.trim();
        const role = document.getElementById("adminRole").value.trim();
        document.getElementById("displayName").textContent = name;
        document.getElementById("displayRole").textContent = role;
        document.getElementById("detailName").textContent = name;
        document.getElementById("detailEmail").textContent = email;
        document.getElementById("detailRole").textContent = role;
        editPanel.hidden = true;
    });
</script>
</body>
</html>