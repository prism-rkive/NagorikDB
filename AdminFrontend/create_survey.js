document.getElementById("create-survey-form").addEventListener("submit", async function (event) {
    event.preventDefault();

    const question = document.getElementById("question").value;
    const startTime = document.getElementById("start-time").value;
    const endTime = document.getElementById("end-time").value;

    // Retrieve adminId from sessionStorage
    const adminId = sessionStorage.getItem("adminId");

    if (!adminId) {
        alert("Admin is not logged in. Please log in and try again.");
        return;
    }

    const surveyData = {
        question,
        startTime,
        endTime,
        admin: {
            id: adminId, // Include the adminId in the request
        },
    };

    try {
        const response = await fetch("http://localhost:8080/api/surveys", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(surveyData),
        });

        if (response.ok) {
            alert("Survey created successfully!");
            window.location.href = "admin_index.html";
        } else {
            const errorMessage = await response.text();
            alert(`Failed to create survey: ${errorMessage}`);
        }
    } catch (err) {
        console.error("Error creating survey:", err);
        alert("An error occurred. Please try again.");
    }
});
