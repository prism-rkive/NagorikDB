document.getElementById("complaintForm").addEventListener("submit", async function (event) {
    event.preventDefault();
    const nid = sessionStorage.getItem("nid");
    console.log("Retrieved NID from sessionStorage:", nid); // Debugging log

    if (!nid) {
        alert("User is not logged in. Please log in and try again.");
        window.location.href = "login.html";//later to my emergencies

        return;
    }
    const formData = new FormData();
    formData.append("sector", document.getElementById("sector").value);
    formData.append("department", document.getElementById("department").value);
    formData.append("contact_no", document.getElementById("phone").value);
    formData.append("details", document.getElementById("note").value);
    formData.append("attachment", document.getElementById("attachment").files[0]); // Get the file
    formData.append("nid", sessionStorage.getItem("nid")); // Add the user NID
    

    try {
        const response = await fetch("http://localhost:8080/api/complaint", {
            method: "POST",
            body: formData, // Send FormData instead of JSON
        });

        if (response.ok) {
            const data = await response.json(); // Get response data
            alert(`Complaint reported successfully! Complaint ID: ${data.complaint_id}`);
            window.location.href = "mycomplaint.html"; // Redirect to complaint page
        } else {
            const errorMessage = await response.text();
            console.error("Backend error:", errorMessage);
            alert(`Failed to report complaint: ${errorMessage}`);
        }
    } catch (err) {
        console.error("Error reporting complaint", err);
        alert("An error occurred. Please try again.");
    }
});



   

    