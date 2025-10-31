// Check if already logged in
if (api.isAuthenticated()) {
    window.location.href = 'dashboard.html';
}

// Login form handling
document.getElementById('loginForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const errorMessage = document.getElementById('errorMessage');
    const submitButton = e.target.querySelector('button[type="submit"]');

    // Disable button and show loading
    submitButton.disabled = true;
    submitButton.textContent = 'Iniciando sesión...';
    errorMessage.style.display = 'none';

    try {
        const response = await api.login(email, password);

        // Save token and user data
        localStorage.setItem('token', response.token);
        localStorage.setItem('user', JSON.stringify({
            userId: response.userId,
            email: response.email,
            nombre: response.nombre,
            rolNombre: response.rolNombre,
        }));

        // Redirect to dashboard
        window.location.href = 'dashboard.html';
    } catch (error) {
        errorMessage.textContent = error.message || 'Error al iniciar sesión. Verifica tus credenciales.';
        errorMessage.style.display = 'block';
        submitButton.disabled = false;
        submitButton.textContent = 'Iniciar Sesión';
    }
});