document.getElementById("emergencyForm").addEventListener("submit", async function (event) {
    event.preventDefault();

    const category = document.getElementById("category").value;
    const district = document.getElementById("district").value;
    const thana = document.getElementById("thana").value;
    const road = document.getElementById("road").value;
    const house = document.getElementById("house").value;
    const postalCode = document.getElementById("postalCode").value;
    const no_affected = document.getElementById("no_affected").value;
    const description = document.getElementById("description").value;


    const nid = sessionStorage.getItem("nid");
    console.log("Retrieved NID from sessionStorage:", nid); // Debugging log

    if (!nid) {
        alert("User is not logged in. Please log in and try again.");
        window.location.href = "login.html";//later to my emergencies

        return;
    }

    const emergencyData = {
        category,
        district,
        thana,
        postalCode,
        road,
        house,
        no_affected,
        description,
        user: {
            nid: nid 
        }
    };
    console.log("Emergency data being sent:", emergencyData); // Debugging log


    try {
        const response = await fetch("http://localhost:8080/api/emergency", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(emergencyData),
        });

        if (response.ok) {
            const data = await response.json(); // Get response data (including case_ID)
            alert(`Emergency reported successfully! Case ID: ${data.case_ID}`);
            window.location.href = "myemergencies.html"; // Redirect to emergency page
        } else {
            const errorMessage = await response.text();
            console.error("Backend error:", errorMessage); // Debugging log

            alert(`Failed to report emergency: ${errorMessage}`);
        }
    } catch (err) {
        console.error("Error reporting emergency", err);
        alert("An error occurred. Please try again.");
    }
});




/* Function to fetch emergencies for a logged-in user
async function getUserEmergencies() {
    const nid = sessionStorage.getItem("nid");

    if (!nid) {
        alert("User is not logged in. Please log in and try again.");
        window.location.href = "login.html"; // Redirect to login page if not logged in
        return;
    }

    try {
        const response = await fetch(`http://localhost:8080/api/emergency/user/${nid}`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            }
        });

        if (response.ok) {
            const emergencies = await response.json();
            console.log("User Emergencies:", emergencies);

            // Render emergencies in the UI
            const emergenciesList = document.getElementById("emergenciesList");
            emergenciesList.innerHTML = ""; // Clear any existing data

            emergencies.forEach(emergency => {
                const listItem = document.createElement("li");
                listItem.textContent = `Case ID: ${emergency.case_ID}, Category: ${emergency.category}, Status: ${emergency.e_status}, Time: ${emergency.timePosted}`;
                emergenciesList.appendChild(listItem);
            });
        } else {
            const errorMessage = await response.text();
            alert(`Error fetching emergencies: ${errorMessage}`);
        }
    } catch (error) {
        console.error("Error fetching emergencies:", error);
        alert("An error occurred while fetching emergencies. Please try again.");
    }
}*/