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
        } else if (sectionId === 'meetings') {
            loadMeetings();
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
loadUpcomingMeetingsCount();

// Load meetings
async function loadMeetings() {
    const meetingsList = document.getElementById('meetingsList');
    const createMeetingBtn = document.getElementById('createMeetingBtn');

    if (!meetingsList) return;

    meetingsList.innerHTML = '<div class="loading">Cargando reuniones...</div>';

    try {
        const meetings = await api.getMeetings();

        if (meetings.length === 0) {
            meetingsList.innerHTML = `
                <div class="empty-state">
                    <p>No hay reuniones programadas</p>
                    <p style="font-size: 13px; margin-top: 8px;">Haz clic en "Nueva Reunión" para crear una</p>
                </div>
            `;
            return;
        }

        meetingsList.innerHTML = '<div class="meetings-list">' + meetings.map(meeting => {
            const date = new Date(meeting.fechaProgramada);
            const formattedDate = date.toLocaleDateString('es-ES', {
                weekday: 'long',
                year: 'numeric',
                month: 'long',
                day: 'numeric'
            });
            const formattedTime = date.toLocaleTimeString('es-ES', {
                hour: '2-digit',
                minute: '2-digit'
            });

            return `
                <div class="meeting-item">
                    <div class="meeting-header">
                        <h4>${meeting.meetingTypeName}</h4>
                        <span class="meeting-status status-${meeting.estado.toLowerCase()}">${meeting.estado}</span>
                    </div>
                    <div class="meeting-details">
                        <p><strong>📅</strong> ${formattedDate} - ${formattedTime}</p>
                        <p><strong>👤</strong> Conductor: ${meeting.conductorName}</p>
                        ${meeting.ubicacion ? `<p><strong>📍</strong> ${meeting.ubicacion}</p>` : ''}
                        ${meeting.notasGenerales ? `<p><strong>📝</strong> ${meeting.notasGenerales}</p>` : ''}
                    </div>
                    <div class="meeting-actions">
                        <button class="btn-view" onclick="viewMeetingAgenda('${meeting.id}')">Ver Agenda</button>
                        <button class="btn-edit" onclick="editMeeting('${meeting.id}')">Editar</button>
                        <button class="btn-delete" onclick="deleteMeeting('${meeting.id}')">Eliminar</button>
                    </div>
                </div>
            `;
        }).join('') + '</div>';
    } catch (error) {
        console.error('Error loading meetings:', error);
        meetingsList.innerHTML = '<div class="empty-state">Error al cargar reuniones</div>';
    }
}

// Load upcoming meetings count for dashboard
async function loadUpcomingMeetingsCount() {
    try {
        const meetings = await api.getUpcomingMeetings();
        const upcomingCount = document.querySelector('.stat-success .stat-value');
        if (upcomingCount) {
            upcomingCount.textContent = meetings.length;
        }
    } catch (error) {
        console.error('Error loading upcoming meetings count:', error);
    }
}

// Delete meeting
async function deleteMeeting(meetingId) {
    if (!confirm('¿Estás seguro de eliminar esta reunión?')) {
        return;
    }

    try {
        await api.deleteMeeting(meetingId);
        loadMeetings();
        loadUpcomingMeetingsCount();
    } catch (error) {
        alert('Error al eliminar reunión: ' + error.message);
    }
}

// Show create meeting modal
async function showCreateMeetingModal() {
    const modal = document.getElementById('createMeetingModal');
    modal.classList.add('show');

    // Load meeting types
    try {
        const meetingTypes = await api.getMeetingTypes();
        const select = document.getElementById('meetingType');
        select.innerHTML = '<option value="">Seleccionar...</option>' +
            meetingTypes.map(type => `<option value="${type.id}">${type.nombre}</option>`).join('');
    } catch (error) {
        console.error('Error loading meeting types:', error);
    }

    // Load users for conductor
    try {
        const users = await api.getActiveUsers();
        const select = document.getElementById('meetingConductor');
        select.innerHTML = '<option value="">Seleccionar...</option>' +
            users.map(user => `<option value="${user.id}">${user.nombre} (${user.rolNombre})</option>`).join('');
    } catch (error) {
        console.error('Error loading users:', error);
    }
}

function closeCreateMeetingModal() {
    const modal = document.getElementById('createMeetingModal');
    modal.classList.remove('show');
    document.getElementById('createMeetingForm').reset();
}

// Handle create meeting form submission
document.getElementById('createMeetingForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const formData = new FormData(e.target);
    const meetingData = {
        meetingTypeId: formData.get('meetingTypeId'),
        fechaProgramada: formData.get('fechaProgramada'),
        ubicacion: formData.get('ubicacion') || null,
        conductorId: formData.get('conductorId'),
        notasGenerales: formData.get('notasGenerales') || null,
    };

    try {
        await api.createMeeting(meetingData);
        closeCreateMeetingModal();
        loadMeetings();
        loadUpcomingMeetingsCount();
        alert('Reunión creada exitosamente');
    } catch (error) {
        alert('Error al crear reunión: ' + error.message);
    }
});

// Close modal when clicking outside
window.onclick = function(event) {
    const modal = document.getElementById('createMeetingModal');
    if (event.target === modal) {
        closeCreateMeetingModal();
    }

    const agendaModal = document.getElementById('agendaModal');
    if (event.target === agendaModal) {
        closeAgendaModal();
    }
};

// View meeting agenda
let currentMeetingId = null;

async function viewMeetingAgenda(meetingId) {
    currentMeetingId = meetingId;
    const modal = document.getElementById('agendaModal');
    modal.classList.add('show');

    await loadAgendaItems(meetingId);
}

async function loadAgendaItems(meetingId) {
    const agendaList = document.getElementById('agendaItemsList');
    agendaList.innerHTML = '<div class="loading">Cargando agenda...</div>';

    try {
        const items = await api.getAgendaByMeeting(meetingId);

        if (items.length === 0) {
            agendaList.innerHTML = `
                <div class="empty-state">
                    <p>No hay items en la agenda</p>
                    <p style="font-size: 13px; margin-top: 8px;">Usa el botón "Agregar Item" para comenzar</p>
                </div>
            `;
            return;
        }

        agendaList.innerHTML = items.map(item => `
            <div class="agenda-item">
                <div class="agenda-order">${item.numeroOrden}</div>
                <div class="agenda-content">
                    <div class="agenda-header">
                        <h5>${item.titulo}</h5>
                        <span class="agenda-type">${item.tipo}</span>
                    </div>
                    ${item.descripcion ? `<p class="agenda-description">${item.descripcion}</p>` : ''}
                    ${item.asignadoNombre || item.emailAsignado ? `
                        <p class="agenda-assigned">
                            <strong>Asignado:</strong> ${item.asignadoNombre || item.emailAsignado}
                            ${item.confirmado ? '<span class="badge-confirmed">✓ Confirmado</span>' : '<span class="badge-pending">Pendiente</span>'}
                        </p>
                    ` : ''}
                    ${item.tiempoAsignado ? `<p class="agenda-time">${item.tiempoAsignado} minutos</p>` : ''}
                </div>
                <div class="agenda-actions">
                    ${!item.confirmado && item.asignadoNombre ? `<button class="btn-confirm" onclick="confirmAgendaItem('${item.id}')">Confirmar</button>` : ''}
                    <button class="btn-delete-small" onclick="deleteAgendaItem('${item.id}')">🗑️</button>
                </div>
            </div>
        `).join('');
    } catch (error) {
        console.error('Error loading agenda:', error);
        agendaList.innerHTML = '<div class="empty-state">Error al cargar agenda</div>';
    }
}

function closeAgendaModal() {
    const modal = document.getElementById('agendaModal');
    modal.classList.remove('show');
    currentMeetingId = null;
}

function showAddAgendaItemModal() {
    document.getElementById('agendaModal').classList.remove('show');
    document.getElementById('addAgendaItemModal').classList.add('show');

    // Load users for assignment
    loadUsersForAgenda();
}

async function loadUsersForAgenda() {
    try {
        const users = await api.getActiveUsers();
        const select = document.getElementById('agendaAsignado');
        select.innerHTML = '<option value="">Sin asignar</option>' +
            users.map(user => `<option value="${user.id}">${user.nombre} (${user.rolNombre})</option>`).join('');
    } catch (error) {
        console.error('Error loading users:', error);
    }
}

function closeAddAgendaItemModal() {
    document.getElementById('addAgendaItemModal').classList.remove('show');
    document.getElementById('addAgendaItemForm').reset();
    document.getElementById('agendaModal').classList.add('show');
}

// Handle add agenda item form
document.getElementById('addAgendaItemForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const formData = new FormData(e.target);
    const agendaData = {
        numeroOrden: parseInt(formData.get('numeroOrden')),
        tipo: formData.get('tipo'),
        titulo: formData.get('titulo'),
        descripcion: formData.get('descripcion') || null,
        asignadoAId: formData.get('asignadoAId') || null,
        emailAsignado: formData.get('emailAsignado') || null,
        tiempoAsignado: formData.get('tiempoAsignado') ? parseInt(formData.get('tiempoAsignado')) : null,
    };

    try {
        await api.createAgendaItem(currentMeetingId, agendaData);
        closeAddAgendaItemModal();
        await loadAgendaItems(currentMeetingId);
    } catch (error) {
        alert('Error al agregar item: ' + error.message);
    }
});

async function deleteAgendaItem(agendaId) {
    if (!confirm('¿Eliminar este item de la agenda?')) {
        return;
    }

    try {
        await api.deleteAgendaItem(agendaId);
        await loadAgendaItems(currentMeetingId);
    } catch (error) {
        alert('Error al eliminar item: ' + error.message);
    }
}

async function confirmAgendaItem(agendaId) {
    try {
        await api.confirmAgendaItem(agendaId);
        await loadAgendaItems(currentMeetingId);
    } catch (error) {
        alert('Error al confirmar: ' + error.message);
    }
}