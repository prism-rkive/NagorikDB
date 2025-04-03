document.addEventListener("DOMContentLoaded", async function () {
    try {
        const nid = sessionStorage.getItem('nid');
    
        if (!nid) {
            console.error('NID not found in sessionStorage!');
            return;
        }
        const response = await fetch(`http://localhost:8080/api/users/emergency/${nid}`);
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
            const statusClass = {
                submitted: "submitted-color",
                rescued: "rescued-color",
                assigned: "assigned-color",
                delayed: "delayed-color"
            }[emergency.estatus.toLowerCase()] || "default-status-color";
            

            let content = `
                    <h3 color: "#1725A"><b>CASE_ID#${emergency.case_ID}</b></h3>
                    <br>
                    <button class="category-label ${categoryClass}">${emergency.category}</button>
                    <button class="category-label ${statusClass}">${emergency.estatus}</button>
                    <br><br>
                    <p><b>Address :</b> ${emergency.house}, ${emergency.road}, ${new Number(emergency.postalCode)}, ${emergency.thana}, ${emergency.district}</p>
                    <p><b>Reported: </b>${new Date(emergency.timeposted).toLocaleString()}</p>

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
                /*// Category color class based on the emergency type
                const categoryClass = {
                    Fire: "fire-color",
                    natural_disaster: "natural-disaster-color",
                    medical: "medical-color",
                    other: "other-color"
                }[emergency.category.toLowerCase()] || "default-color";
                const statusClass = {
                    submitted: "submitted-color",
                    rescued: "rescued-color",
                    assigned: "assigned-color",
                    delayed: "delayed-color"
                }[emergency.estatus.toLowerCase()] || "default-status-color";
                <button class="category-label ${categoryClass}">${emergency.category}</button>
                                    <div class="dropDown" id="dropDown-${emergency.case_ID}" style="display: none;"></div>
*/


                // Emergency content including the category and details
               