document.addEventListener("DOMContentLoaded", () => {
    const loginForm = document.getElementById("login-form");

    loginForm.addEventListener("submit", async (e) => {
        e.preventDefault(); // Prevent form submission reload

        // Get the input values (NID and Password)
        const nid = document.getElementById("nid").value;
        const password = document.getElementById("password").value;

        // Convert nid to a number (Long type expected by the backend)
        const nidNumber = Number(nid);

        // If nid conversion fails, show an error
        if (isNaN(nidNumber)) {
            alert("Invalid NID entered. Please check your input.");
            return;
        }

        try {
            // Authenticate the user via the backend
            const response = await fetch("http://localhost:8080/api/users/authenticate", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ nid: nidNumber, password }), // Send nid and password as JSON
            });

            if (response.ok) {
                const user = await response.json(); // Parse the returned UserDTO

                // Store the user ID (nid) in localStorage
                sessionStorage.setItem("nid", user.nid);
				sessionStorage.setItem("userName",user.name);
                localStorage.setItem("userNid", user.nid);
                localStorage.setItem("userName", user.name);



                // Show success message
                const successMessage = document.createElement("p");
                successMessage.textContent = "Login successful! Redirecting...";
                successMessage.style.color = "white";
                successMessage.style.fontWeight = "bold";
                successMessage.style.textAlign = "center";
                document.body.appendChild(successMessage);

                // Redirect to homepage after 2 seconds
                setTimeout(() => {
                    window.location.href = "index.html"; // Adjust this as needed
                }, 2000);
            } else {
                const errorMessage = await response.text();
                alert(`Login failed: ${errorMessage}`);
            }
        } catch (error) {
            console.error("Error during login:", error);
            alert("An error occurred while trying to log in.");
        }
    });
});
