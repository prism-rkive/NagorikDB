document.addEventListener("DOMContentLoaded", async function () {
    try {
        const a_id = sessionStorage.getItem('a_id');
    
        if (!a_id) {
            console.error('NID not found in sessionStorage!');
            return;
        }
        const response = await fetch(`http://localhost:8080/api/area/subemergency/${a_id}`);
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || "Failed to fetch emergencies.");
        }

        const emergencies = await response.json();

        const emergencyList = document.getElementById("emergency-list");
        if (emergencies.length === 0) {
            emergencyList.innerHTML = "<p>You Didn't Report Any Emergency .</p>";
        } 
        else {
            emergencies.forEach(emergency => {
            const emergencyDiv = document.createElement("div");
            emergencyDiv.classList.add("emergency");

            const normalizeKey = (key) => key.toLowerCase().replace(/\s+/g, '');

            const categoryClass = {
                    Fire: "fire-color",
                    natural_disaster: "natural-disaster-color",
                    medical: "medical-color",
                    other: "other-color"
            }[normalizeKey(emergency.category)] || "default-color";
            
/*                    <p><b>NID     :</b> ${emergency.user.nid}<p>*/

            let content = `
                    <h3>CASE_ID#${emergency.case_ID}</h3>
                    <br>
                    <button class="category-label ${categoryClass}">${emergency.category}</button>
                    <p><b>Address :</b> ${emergency.house}, ${emergency.road}, ${new Number(emergency.postalCode)}, ${emergency.thana}, ${emergency.district}</p>
                    <p><b>NID     :</b> ${emergency.user.nid}<p>

                    <p><b>Reported: </b>${new Date(emergency.timeposted).toLocaleString()}</p>
                    <button class="assign-team-btn" onclick="toggleDropdown('${emergency.case_ID}','${a_id}')"> Assign a Rescue Team   </button>
                    <div class="dropDown" id="dropDown-${emergency.case_ID}" style="display: none;"></div>

                `;
                


                if (emergency.description && emergency.description.trim()) {
                    content += `<p>Description: ${emergency.description}</p>`;
                }

                emergencyDiv.innerHTML = content;
                emergencyList.appendChild(emergencyDiv);
            });

        }
    }
       catch (err) {
        console.error("Error fetching surveys:", err);
        alert("An error occurred while fetching surveys.");
    }
});
/*<button class="assign-team-btn" onclick="toggleDropdown('${emergency.case_ID}','${a_id}')"> Assign a Rescue Team   </button>
                    <div class="dropDown" id="dropDown-${emergency.case_ID}" style="display: none;"></div>
*/
// Toggle the dropdown menu to show or hide
async function toggleDropdown(caseId,a_id) {
    console.log('toggleDropdown called with:', caseId, a_id); // Log parameters to check their values
    const dropdown = document.getElementById(`dropDown-${caseId}`);

    // Toggle the dropdown's visibility
    dropdown.style.display = dropdown.style.display === "block" ? "none" : "block";

    // Fetch rescue teams if dropdown is being shown
    if (dropdown.style.display === "block") {
        try {
            const response = await fetch(`http://localhost:8080/api/rescue/available/${a_id}`);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
    
            const teams = await response.json();
    
            dropdown.innerHTML = ''; // Clear existing content
            if (teams.length === 0) {
                dropdown.innerHTML = "<p>No Rescue Teams Available.</p>";
                return;
            }
    
            console.log("Response from API:", teams); // Debugging log
    
            // Append each team to the dropdown
            teams.forEach(team => {
                const teamItem = document.createElement('div');
                teamItem.className = 'dropDown-item';
                teamItem.textContent = `${team.name} - Team ID: ${team.team_id}`;
                teamItem.onclick = () => assignTeam(caseId, team.team_id, dropdown, a_id);
                dropdown.appendChild(teamItem);
            });
        } catch (error) {
            console.error("Error fetching available rescue teams:", error);
            alert("An error occurred while fetching rescue teams.");
        }
    }
    
    }

async function assignTeam(caseId, teamId, dropdown,a_id) {
    try {
        const response = await fetch(`http://localhost:8080/api/rescue/assign/${a_id}/${teamId}/${caseId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ case_ID: caseId })//what is here
        });

        if (!response.ok) {
            throw new Error(`Failed to assign team! Status: ${response.status}`);
        }

        alert(`Team ${teamId} successfully assigned to Emergency ${caseId}.`);
        dropdown.style.display = "none"; // Hide the dropdown
    } catch (error) {
        console.error("Error assigning rescue team:", error);
        alert("An error occurred while assigning the rescue team.");
    }
}

