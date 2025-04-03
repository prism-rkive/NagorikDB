document.getElementById("admin-login-form").addEventListener("submit", async function (event) {
    event.preventDefault(); // Prevent the default form submission behavior

    // Collect form data
    const email = document.getElementById("email").value;
    const password = document.getElementById("admin-password").value;

    // Create a login object to send to the server
    const loginData = {
        email: email,
        adminPassword: password
    };

    try {
        // Send a POST request to the backend API
        const response = await fetch("http://localhost:8080/api/admin/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(loginData),
        });

        // Parse the JSON response
        const data = await response.json();

        // Check if the response is successful
        if (response.ok) {
            alert(data.message); // Show the success message
            
            // Store admin ID in sessionStorage
            sessionStorage.setItem("adminId", data.id);

            // Redirect to the admin dashboard or another page
            window.location.href = "admin_index.html";
        } else {
            alert("Login failed: " + (data.error || "Invalid credentials"));
        }
    } catch (err) {
        console.error("Error during login:", err);
        alert("An error occurred. Please try again later.");
    }
});
