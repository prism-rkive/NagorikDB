
/*document.addEventListener("DOMContentLoaded", async () => {
    const surveyContainer = document.getElementById("survey-container");

    try {
        // Fetch surveys from the backend
        const response = await fetch("http://localhost:8080/api/surveys");

        if (!response.ok) {
            throw new Error(`Error fetching surveys: ${response.statusText}`);
        }

        const surveys = await response.json();

        // If no surveys are available
        if (surveys.length === 0) {
            surveyContainer.innerHTML = `
                <div class="col-12">
                    <p class="text-center text-muted">No surveys are available at the moment.</p>
                </div>`;
            return;
        }

        const currentTime = new Date().toISOString();  // Get the current time to compare with the survey timings

        // Generate survey cards
        surveys.forEach(async (survey, index) => {
            const surveyCard = document.createElement("div");
            surveyCard.className = "col-md-6 mb-4";

            const bgColor = index % 2 === 0 ? "#f8f9fa" : "#e9ecef";

            // Check if the survey is within the valid time range
            const isSurveyAvailable = currentTime >= survey.startTime && currentTime <= survey.endTime;

            surveyCard.innerHTML = `
                <div class="card h-100" style="background-color: ${bgColor}" data-survey-id="${survey.surveyId}">
                    <div class="card-body">
                        <h5 class="card-title">${survey.question}</h5>
                        <p class="card-text text-muted">
                            <strong>Starts:</strong> ${formatDate(survey.startTime)}<br>
                            <strong>Ends:</strong> ${formatDate(survey.endTime)}
                        </p>
                        <div class="d-flex justify-content-between mt-3">
                            <button class="custom-yes-btn" onclick="vote(${survey.surveyId}, 'yes')" ${!isSurveyAvailable ? 'disabled' : ''}>
                                Yes
                            </button>
                            <button class="custom-no-btn" onclick="vote(${survey.surveyId}, 'no')" ${!isSurveyAvailable ? 'disabled' : ''}>
                                No
                            </button>
                        </div>
                        <div class="mt-3">
                            <p class="statistics text-primary">Loading stats...</p>
                        </div>
                        <div class="mt-4">
                            <canvas id="pieChart-${survey.surveyId}" style="max-height: 400px;"></canvas>
                        </div>
                    </div>
                </div>`;

            surveyContainer.appendChild(surveyCard);

            // Fetch initial statistics
            const statsResponse = await fetch(`http://localhost:8080/api/participations/survey/${survey.surveyId}/statistics`);
            const stats = statsResponse.ok ? await statsResponse.text() : "No data available";

            const statsElement = surveyCard.querySelector(".statistics");
            statsElement.textContent = stats;
        });
    } catch (error) {
        console.error("Error loading surveys:", error);
        surveyContainer.innerHTML = `
            <div class="col-12">
                <p class="text-center text-danger">Failed to load surveys. Please try again later.</p>
            </div>`;
    }
});

// Function to format date for better readability
function formatDate(dateTime) {
    if (!dateTime) return "N/A";
    const date = new Date(dateTime);
    return date.toLocaleString("en-US", { dateStyle: "medium", timeStyle: "short" });
}

// Function to handle voting and update stats
async function vote(surveyId, choice) {
    try {
        const userId = localStorage.getItem("userId"); // Get the user ID from localStorage
        if (!userId || isNaN(userId)) {
            alert("User not logged in or invalid user ID.");
            return;
        }

        // Prepare the vote payload
        const votePayload = {
            id: {
                surveyId: surveyId,
                userId: Number(userId),
            },
            response: choice, // "yes" or "no"
            submissionTime: new Date().toISOString(),
        };

        console.log("Submitting vote payload:", votePayload);

        // Send vote to the backend
        const response = await fetch("http://localhost:8080/api/participations", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(votePayload),
        });

        if (!response.ok) {
            throw new Error(`Error submitting vote: ${response.statusText}`);
        }

        // Fetch updated statistics
        const statsResponse = await fetch(`http://localhost:8080/api/participations/survey/${surveyId}/statistics`);
        if (!statsResponse.ok) {
            throw new Error(`Error fetching statistics: ${statsResponse.statusText}`);
        }

        const statsText = await statsResponse.text(); // Get the string response, e.g., "Yes: 60%, No: 40%"
        console.log("Stats string:", statsText);

        // Parse the string to extract percentages
        const yesMatch = statsText.match(/Yes:\s(\d+)%/);
        const noMatch = statsText.match(/No:\s(\d+)%/);
        const yesPercentage = yesMatch ? parseInt(yesMatch[1]) : 0;
        const noPercentage = noMatch ? parseInt(noMatch[1]) : 0;

        // Update the UI
        const surveyCard = document.querySelector(`[data-survey-id="${surveyId}"]`);
        const statsElement = surveyCard.querySelector(".statistics");
        statsElement.textContent = statsText;

        // Update Pie Chart
        updatePieChart(surveyId, { yes: yesPercentage, no: noPercentage });

        alert("Vote recorded successfully!");
    } catch (error) {
        console.error("Error during voting:", error);
        alert("Failed to submit your vote. Please try again later.");
    }
}

// Store chart instances for each survey
const pieCharts = {};

function updatePieChart(surveyId, stats) {
    const canvas = document.getElementById(`pieChart-${surveyId}`);
    const data = [stats.yes, stats.no]; // Data for Yes and No percentages

    // If the chart already exists, update its data
    if (pieCharts[surveyId]) {
        pieCharts[surveyId].data.datasets[0].data = data;
        pieCharts[surveyId].update();
    } else {
        // Initialize a new pie chart
        pieCharts[surveyId] = new Chart(canvas, {
            type: "pie",
            data: {
                labels: ["Yes", "No"],
                datasets: [{
                    label: "Votes",
                    data: data,
                    backgroundColor: ['#2B7A78', '#3AAFA9'], // colors for yes no
                }],
            },
        });
    }
}*/
document.addEventListener("DOMContentLoaded", async () => {
    const surveyContainer = document.getElementById("survey-container");

    try {
        // Fetch surveys from the backend
        const response = await fetch("http://localhost:8080/api/surveys");

        if (!response.ok) {
            throw new Error(`Error fetching surveys: ${response.statusText}`);
        }

        const surveys = await response.json();

        // If no surveys are available
        if (surveys.length === 0) {
            surveyContainer.innerHTML = `
                <div class="col-12">
                    <p class="text-center text-muted">No surveys are available at the moment.</p>
                </div>`;
            return;
        }

        // Generate survey cards
        surveys.forEach(async (survey, index) => {
            const surveyCard = document.createElement("div");
            surveyCard.className = "col-md-6 mb-4";

            const bgColor = index % 2 === 0 ? "#f8f9fa":"#f8f9fa"/* : "#e9ecef"*/;

            surveyCard.innerHTML = `
                <div class="card h-100" style="background-color: ${bgColor};box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); 
                border-radius: 15px;" data-survey-id="${survey.surveyId}">
                    <div class="card-body">
                        <h5 class="card-title">${survey.question}</h5>
                        <p class="card-text text-muted">
                            <strong>Starts:</strong> ${formatDate(survey.startTime)}<br>
                            <strong>Ends:</strong> ${formatDate(survey.endTime)}
                        </p>
                        <div class="d-flex justify-content-between mt-3">
                            <button class="custom-yes-btn" onclick="vote(${survey.surveyId}, 'yes', '${survey.endTime}')">
                                Yes
                            </button>
                            <button class="custom-no-btn" onclick="vote(${survey.surveyId}, 'no', '${survey.endTime}')">
                                No
                            </button>
                        </div>
                        <div class="mt-3">
                            <p class="statistics text-primary">Loading stats...</p>
                        </div>
						<div class="mt-4">
						    <canvas id="pieChart-${survey.surveyId}" style="max-height: 400px;"></canvas>
						</div>
                    </div>
                </div>`;

            surveyContainer.appendChild(surveyCard);

            // Fetch initial statistics
            const statsResponse = await fetch(`http://localhost:8080/api/participations/survey/${survey.surveyId}/statistics`);
            const stats = statsResponse.ok ? await statsResponse.text() : "No data available";

            const statsElement = surveyCard.querySelector(".statistics");
            statsElement.textContent = stats;
        });
    } catch (error) {
        console.error("Error loading surveys:", error);
        surveyContainer.innerHTML = `
            <div class="col-12">
                <p class="text-center text-danger">Failed to load surveys. Please try again later.</p>
            </div>`;
    }
});

// Function to format date for better readability
function formatDate(dateTime) {
    if (!dateTime) return "N/A";
    const date = new Date(dateTime);
    return date.toLocaleString("en-US", { dateStyle: "medium", timeStyle: "short" });
}

// Function to handle voting and update stats
async function vote(surveyId, choice, endTime) {
    try {
        const userId = sessionStorage.getItem("nid"); // Get the user ID from localStorage
        if (!userId || isNaN(userId)) {
            alert("User not logged in or invalid user ID.");
            return;
        }

        // Check if the survey has expired
        const currentTime = new Date().toISOString();
        if (currentTime > endTime) {
            alert("Sorry, this survey has expired! Check ongoing surveys.");
            return;
        }

        // Prepare the vote payload
        const votePayload = {
            id: {
                surveyId: surveyId,
                userId: Number(userId),
            },
            response: choice, // "yes" or "no"
            submissionTime: currentTime,
        };

        console.log("Submitting vote payload:", votePayload);

        // Send vote to the backend
        const response = await fetch("http://localhost:8080/api/participations", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(votePayload),
        });

        if (!response.ok) {
            throw new Error(`Error submitting vote: ${response.statusText}`);
        }

        // Fetch updated statistics
        const statsResponse = await fetch(`http://localhost:8080/api/participations/survey/${surveyId}/statistics`);
        if (!statsResponse.ok) {
            throw new Error(`Error fetching statistics: ${statsResponse.statusText}`);
        }

        const statsText = await statsResponse.text(); // Get the string response, e.g., "Yes: 60%, No: 40%"
        console.log("Stats string:", statsText);

        // Parse the string to extract percentages
        const yesMatch = statsText.match(/Yes:\s(\d+)%/);
        const noMatch = statsText.match(/No:\s(\d+)%/);
        const yesPercentage = yesMatch ? parseInt(yesMatch[1]) : 0;
        const noPercentage = noMatch ? parseInt(noMatch[1]) : 0;

        // Update the UI
        const surveyCard = document.querySelector(`[data-survey-id="${surveyId}"]`);
        const statsElement = surveyCard.querySelector(".statistics");
        statsElement.textContent = statsText;

        // Update Pie Chart
        updatePieChart(surveyId, { yes: yesPercentage, no: noPercentage });

        alert("Vote recorded successfully!");
    } catch (error) {
        console.error("Error during voting:", error);
        alert("Failed to submit your vote. Please try again later.");
    }
}

// Store chart instances for each survey
const pieCharts = {};

function updatePieChart(surveyId, stats) {
    const canvas = document.getElementById(`pieChart-${surveyId}`);
    const data = [stats.yes, stats.no]; // Data for Yes and No percentages

    // If the chart already exists, update its data
    if (pieCharts[surveyId]) {
        pieCharts[surveyId].data.datasets[0].data = data;
        pieCharts[surveyId].update();
    } else {
        // Initialize a new pie chart
        pieCharts[surveyId] = new Chart(canvas, {
            type: "pie",
            data: {
                labels: ["Yes", "No"],
                datasets: [{
                    label: "Votes",
                    data: data,
                    backgroundColor: ['#2B7A78', '#3AAFA9'], // colors for yes no
                }],
            },
        });
    }
}





