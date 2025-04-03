document.addEventListener("DOMContentLoaded", async function () {
    try {
        // Fetch survey statistics
        const response = await fetch("http://localhost:8080/api/surveys/statistics");
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || "Failed to fetch survey statistics.");
        }

        const surveyStats = await response.json();

        // Display survey statistics in the survey list container
        const statsList = document.getElementById("stats-list");
        if (surveyStats.length === 0) {
            statsList.innerHTML = "<p>No survey statistics available.</p>";
        } else {
			surveyStats.forEach(stat => {
			    const statDiv = document.createElement("div");
			    statDiv.classList.add("stat");

			    const yesPercentage = stat.yesPercentage !== null ? stat.yesPercentage.toFixed(2) : "0.00";
			    const noPercentage = stat.noPercentage !== null ? stat.noPercentage.toFixed(2) : "0.00";

			    statDiv.innerHTML = `
			        <h3>${stat.question}</h3>
			        <p>Yes: ${yesPercentage}%</p>
			        <p>No: ${noPercentage}%</p>
			        <p>Created by Admin ID: ${stat.adminId}</p>
			    `;

			    statsList.appendChild(statDiv);
			});

        }
    } catch (err) {
        console.error("Error fetching survey statistics:", err);
        alert("An error occurred while fetching survey statistics.");
    }
});
