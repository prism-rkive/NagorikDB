document.addEventListener("DOMContentLoaded", () => {
    const loginForm = document.getElementById("login-form");

    loginForm.addEventListener("submit", async (e) => {
        e.preventDefault(); // Prevent form submission reload

        const response_id = document.getElementById("id").value;
        const password = document.getElementById("password").value;
        const a_id=response_id;
        const s_id=response_id;

        try {
            const response1 = await fetch("http://localhost:8080/api/area/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                },
                body: new URLSearchParams({response_id,password}),
            });
            const response2 = await fetch("http://localhost:8080/api/sector/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded",
                },
                body: new URLSearchParams({response_id,password}),
            });
            if (response1.ok || response2.ok) {
                // Show success message on the same page
                const successMessage = document.createElement("p");
                successMessage.textContent = "Login successful!";
                successMessage.style.color = "white";
                //successMessage.style.fontWeight = "bold";
                successMessage.style.textAlign = "left";
                document.body.appendChild(successMessage);
                if(response1.ok){ // Add the success message to the page
                  sessionStorage.setItem("a_id", response_id);
                  setTimeout(() => {
                    window.location.href = "responsehome.html"; // Change this to your homepage route
                    }, 2000);
                } 
                else{
                    sessionStorage.setItem("s_id", response_id);
                    setTimeout(() => {
                      window.location.href = "sector_response.html"; // Change this to your homepage route
                      }, 2000);

                }// Delay of 2 seconds (2000ms)
            }

            
            else {
                const errorMessage = await response.text();
                alert(`Login failed: ${errorMessage}`);
            }
        } catch (error) {
            console.error("Error during login:", error);
            alert("An error occurred while trying to log in.");
        }
    });
});