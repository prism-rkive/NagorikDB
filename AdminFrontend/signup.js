/*document.querySelector("form").addEventListener("submit", async (e) => {
    e.preventDefault();

    // Collect form data
    const formData = {
        nid: document.querySelector("input[name='NID']").value,
        name: document.querySelector("input[name='name']").value,
        password: document.querySelector("input[name='password']").value,
    };

    try {
        // Send POST request to the backend
        const response = await fetch("http://localhost:8080/api/users", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(formData),
        });

        if (response.ok) {
            alert("Signup successful! You can now log in.");
            window.location.href = "login.html";
        } else {
            const errorMsg = await response.text();
            alert(`Signup failed: ${errorMsg}`);
        }
    } catch (error) {
        console.error("Error during signup:", error);
        alert("An error occurred. Please try again.");
    }
});*/
document.querySelector("form").addEventListener("submit", async (e) => {
    e.preventDefault();

    const nidInput = document.querySelector("input[name='NID']");
    if (nidInput.value.length !== 7) {
        alert("NID must be exactly 7 characters long.");
        return;
    }

    const formData = {
        nid: nidInput.value,
        name: document.querySelector("input[name='name']").value,
        password: document.querySelector("input[name='password']").value,
    };

    try {
        const response = await fetch("http://localhost:8080/api/users", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(formData),
        });

        if (response.ok) {
            alert("Signup successful! You can now log in.");
            window.location.href = "login.html";
        } else {
            const errorMsg = await response.text();
            alert(`Signup failed: ${errorMsg}`);
        }
    } catch (error) {
        console.error("Error during signup:", error);
        alert("An error occurred. Please try again.");
    }
});

// Password strength validation
document.getElementById('togglePassword').addEventListener('click', function () {
    const passwordInput = document.getElementById('password');
    const icon = this.querySelector('i');

    if (passwordInput.type === 'password') {
        passwordInput.type = 'text';
        icon.classList.remove('fa-eye');
        icon.classList.add('fa-eye-slash');
    } else {
        passwordInput.type = 'password';
        icon.classList.remove('fa-eye-slash');
        icon.classList.add('fa-eye');
    }
});

function validatePassword(password) {
    const strongPasswordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
    const errorMessage = document.getElementById('errorMessage');

    document.getElementById('minLength').innerHTML =
        password.length >= 8
            ? '<i class="fas fa-check text-success"></i> Minimum 8 characters'
            : '<i class="fas fa-times text-danger"></i> Minimum 8 characters';
    document.getElementById('uppercase').innerHTML =
        /[A-Z]/.test(password)
            ? '<i class="fas fa-check text-success"></i> At least one uppercase letter'
            : '<i class="fas fa-times text-danger"></i> At least one uppercase letter';
    document.getElementById('lowercase').innerHTML =
        /[a-z]/.test(password)
            ? '<i class="fas fa-check text-success"></i> At least one lowercase letter'
            : '<i class="fas fa-times text-danger"></i> At least one lowercase letter';
    document.getElementById('symbol').innerHTML =
        /[@$!%*?&]/.test(password)
            ? '<i class="fas fa-check text-success"></i> At least one symbol (@$!%*?&)'
            : '<i class="fas fa-times text-danger"></i> At least one symbol (@$!%*?&)';

    if (strongPasswordRegex.test(password)) {
        errorMessage.textContent = 'Strong Password';
        errorMessage.classList.remove('text-danger');
        errorMessage.classList.add('text-success');
		errorMessage.style.color = 'white';
    } else {
        errorMessage.textContent = 'Weak Password';
        errorMessage.classList.remove('text-success');
        errorMessage.classList.add('text-danger');
		errorMessage.style.color = '#2F4F4F';
		
    }
}

