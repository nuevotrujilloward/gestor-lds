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
};