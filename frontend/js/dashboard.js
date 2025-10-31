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
}

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

// Initialize dashboard
loadActiveMembersCount();