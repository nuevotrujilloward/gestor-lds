// Redirect to login if not authenticated
if (!api.isAuthenticated()) {
    window.location.href = 'index.html';
}

// Load user data
const user = api.getCurrentUser();
if (user) {
    document.getElementById('userName').textContent = user.nombre;
    document.getElementById('userRole').textContent = user.rolNombre;
    document.getElementById('welcomeName').textContent = user.nombre.split(' ')[0];

    // Show admin section if user is Obispo
    if (user.rolNombre === 'Obispo') {
        document.getElementById('adminSection').style.display = 'block';
    }
}

// Navigation handling
const navItems = document.querySelectorAll('.nav-item');
const contentSections = document.querySelectorAll('.content-section');

navItems.forEach(item => {
    item.addEventListener('click', () => {
        const sectionId = item.getAttribute('data-section');

        // Update active nav item
        navItems.forEach(nav => nav.classList.remove('active'));
        item.classList.add('active');

        // Show corresponding content section
        contentSections.forEach(section => section.classList.remove('active'));
        document.getElementById(sectionId + 'Section').classList.add('active');

        // Load section data if needed
        if (sectionId === 'members') {
            loadMembers();
        } else if (sectionId === 'users') {
            loadUsers();
        }
    });
});

// Load active members count
async function loadActiveMembersCount() {
    try {
        const users = await api.getActiveUsers();
        document.getElementById('activeMembersCount').textContent = users.length;
    } catch (error) {
        console.error('Error loading active members:', error);
        document.getElementById('activeMembersCount').textContent = '-';
    }
}

// Load members list
async function loadMembers() {
    const membersList = document.getElementById('membersList');
    membersList.innerHTML = '<div class="loading">Cargando miembros...</div>';

    try {
        const users = await api.getActiveUsers();

        if (users.length === 0) {
            membersList.innerHTML = '<div class="empty-state">No hay miembros registrados</div>';
            return;
        }

        membersList.innerHTML = '<div class="user-list">' + users.map(user => `
            <div class="user-item">
                <div class="user-item-info">
                    <div class="user-avatar">${user.nombre.charAt(0)}</div>
                    <div class="user-details">
                        <h4>${user.nombre}</h4>
                        <p>${user.email} • ${user.rolNombre}</p>
                    </div>
                </div>
                <span class="user-status status-${user.activo ? 'active' : 'inactive'}">
                    ${user.activo ? 'Activo' : 'Inactivo'}
                </span>
            </div>
        `).join('') + '</div>';
    } catch (error) {
        console.error('Error loading members:', error);
        membersList.innerHTML = '<div class="empty-state">Error al cargar miembros</div>';
    }
}

// Load users list (admin only)
async function loadUsers() {
    const usersList = document.getElementById('usersList');
    usersList.innerHTML = '<div class="loading">Cargando usuarios...</div>';

    try {
        const users = await api.getUsers();

        if (users.length === 0) {
            usersList.innerHTML = '<div class="empty-state">No hay usuarios registrados</div>';
            return;
        }

        usersList.innerHTML = '<div class="user-list">' + users.map(user => `
            <div class="user-item">
                <div class="user-item-info">
                    <div class="user-avatar">${user.nombre.charAt(0)}</div>
                    <div class="user-details">
                        <h4>${user.nombre}</h4>
                        <p>${user.email} • ${user.rolNombre}</p>
                    </div>
                </div>
                <span class="user-status status-${user.activo ? 'active' : 'inactive'}">
                    ${user.activo ? 'Activo' : 'Inactivo'}
                </span>
            </div>
        `).join('') + '</div>';
    } catch (error) {
        console.error('Error loading users:', error);
        usersList.innerHTML = '<div class="empty-state">Error al cargar usuarios</div>';
    }
}

// Initialize dashboard
loadActiveMembersCount();