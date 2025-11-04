const API_BASE_URL = 'http://localhost:8080/api';

const api = {
    // Login
    async login(email, password) {
        const response = await fetch(`${API_BASE_URL}/auth/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ email, password }),
        });

        if (!response.ok) {
            const error = await response.json();
            throw new Error(error.message || 'Error al iniciar sesión');
        }

        return response.json();
    },

    // Get all users
    async getUsers() {
        const token = localStorage.getItem('token');
        const response = await fetch(`${API_BASE_URL}/auth/users`, {
            headers: {
                'Authorization': `Bearer ${token}`,
            },
        });

        if (!response.ok) {
            throw new Error('Error al obtener usuarios');
        }

        return response.json();
    },

    // Get active users
    async getActiveUsers() {
        const token = localStorage.getItem('token');
        const response = await fetch(`${API_BASE_URL}/auth/users/active`, {
            headers: {
                'Authorization': `Bearer ${token}`,
            },
        });

        if (!response.ok) {
            throw new Error('Error al obtener usuarios activos');
        }

        return response.json();
    },

    // Get current user info
    getCurrentUser() {
        const userData = localStorage.getItem('user');
        return userData ? JSON.parse(userData) : null;
    },

    // Check if user is authenticated
    isAuthenticated() {
        return !!localStorage.getItem('token');
    },

    // Logout
    logout() {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        window.location.href = 'index.html';
    },

    // Meetings
    async getMeetings() {
        const token = localStorage.getItem('token');
        const response = await fetch(`${API_BASE_URL}/meetings`, {
            headers: {
                'Authorization': `Bearer ${token}`,
            },
        });

        if (!response.ok) {
            throw new Error('Error al obtener reuniones');
        }

        return response.json();
    },

    async getUpcomingMeetings() {
        const token = localStorage.getItem('token');
        const response = await fetch(`${API_BASE_URL}/meetings/upcoming`, {
            headers: {
                'Authorization': `Bearer ${token}`,
            },
        });

        if (!response.ok) {
            throw new Error('Error al obtener reuniones próximas');
        }

        return response.json();
    },

    async getMeetingTypes() {
        const token = localStorage.getItem('token');
        const response = await fetch(`${API_BASE_URL}/meetings/types`, {
            headers: {
                'Authorization': `Bearer ${token}`,
            },
        });

        if (!response.ok) {
            throw new Error('Error al obtener tipos de reuniones');
        }

        return response.json();
    },

    async createMeeting(meetingData) {
        const token = localStorage.getItem('token');
        const response = await fetch(`${API_BASE_URL}/meetings`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`,
            },
            body: JSON.stringify(meetingData),
        });

        if (!response.ok) {
            const error = await response.json();
            throw new Error(error.message || 'Error al crear reunión');
        }

        return response.json();
    },

    async deleteMeeting(meetingId) {
        const token = localStorage.getItem('token');
        const response = await fetch(`${API_BASE_URL}/meetings/${meetingId}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${token}`,
            },
        });

        if (!response.ok) {
            throw new Error('Error al eliminar reunión');
        }
    },

    // Agenda
    async getAgendaByMeeting(meetingId) {
        const token = localStorage.getItem('token');
        const response = await fetch(`${API_BASE_URL}/agenda/meeting/${meetingId}`, {
            headers: {
                'Authorization': `Bearer ${token}`,
            },
        });

        if (!response.ok) {
            throw new Error('Error al obtener agenda');
        }

        return response.json();
    },

    async createAgendaItem(meetingId, agendaData) {
        const token = localStorage.getItem('token');
        const response = await fetch(`${API_BASE_URL}/agenda/meeting/${meetingId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`,
            },
            body: JSON.stringify(agendaData),
        });

        if (!response.ok) {
            const error = await response.json();
            throw new Error(error.message || 'Error al crear item de agenda');
        }

        return response.json();
    },

    async deleteAgendaItem(agendaId) {
        const token = localStorage.getItem('token');
        const response = await fetch(`${API_BASE_URL}/agenda/${agendaId}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${token}`,
            },
        });

        if (!response.ok) {
            throw new Error('Error al eliminar item de agenda');
        }
    },

    async confirmAgendaItem(agendaId) {
        const token = localStorage.getItem('token');
        const response = await fetch(`${API_BASE_URL}/agenda/${agendaId}/confirm`, {
            method: 'PUT',
            headers: {
                'Authorization': `Bearer ${token}`,
            },
        });

        if (!response.ok) {
            throw new Error('Error al confirmar item de agenda');
        }

        return response.json();
    },
};