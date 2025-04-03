document.addEventListener("DOMContentLoaded", async function () {
    try {
        // Get the admin ID from session storage or secure storage
        const adminId = sessionStorage.getItem("adminId");

        if (!adminId) {
            alert("Admin ID is missing. Please log in again.");
            return;
        }

        // Fetch surveys created by the admin
        const response = await fetch(`http://localhost:8080/api/surveys/admin/${adminId}`);
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || "Failed to fetch surveys.");
        }

        const surveys = await response.json();

        // Display surveys in the survey list container
        const surveyList = document.getElementById("survey-list");
        if (surveys.length === 0) {
            surveyList.innerHTML = "<p>No surveys found.</p>";
        } else {
            surveys.forEach(survey => {
                const surveyDiv = document.createElement("div");
                surveyDiv.classList.add("survey");

                surveyDiv.innerHTML = `
                    <h3>${survey.question}</h3>
                    <p>Start Time: ${new Date(survey.startTime).toLocaleString()}</p>
                    <p>End Time: ${new Date(survey.endTime).toLocaleString()}</p>
                    <button onclick="deleteSurvey(${survey.surveyId})">Delete Survey</button>
                `;

                surveyList.appendChild(surveyDiv);
            });
        }
    } catch (err) {
        console.error("Error fetching surveys:", err);
        alert("An error occurred while fetching surveys.");
    }
});

async function deleteSurvey(surveyId) {
    const confirmDelete = confirm("Are you sure you want to delete this survey?");

    if (confirmDelete) {
        try {
            const response = await fetch(`http://localhost:8080/api/surveys/${surveyId}`, {
                method: "DELETE",
            });

            if (response.ok) {
                alert("Survey deleted successfully.");
                window.location.reload(); // Refresh the survey list
            } else {
                const errorData = await response.json();
                alert("Failed to delete survey: " + (errorData.message || "Unknown error"));
            }
        } catch (err) {
            console.error("Error deleting survey:", err);
            alert("An error occurred. Please try again.");
        }
    }
}
