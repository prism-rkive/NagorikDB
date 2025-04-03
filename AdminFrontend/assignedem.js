document.addEventListener("DOMContentLoaded", async function () {
    try {
        const a_id = sessionStorage.getItem('a_id');
    
        if (!a_id) {
            console.error('NID not found in sessionStorage!');
            return;
        }
        const response = await fetch(`http://localhost:8080/api/area/assigned/${a_id}`);
        const emergencyList = document.getElementById("emergency-list");

        emergencyList.innerHTML = ""; // Clear the list before populating

        if (response.status === 204) {
            // Handle No Content (empty list) case
            emergencyList.innerHTML = "<p></p>";
            return;
        }
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || "Failed to fetch emergencies.");
        }

        const emergencies = await response.json();

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
           

            let content = ` 
                    <h3>CASE_ID#${emergency.case_ID}</h3>
                    <br>
                    <button class="category-label ${categoryClass}">${emergency.category}</button>
                    <p><b>NID     :</b> ${emergency.user.nid}<p>
                    <p><b>Address :</b> ${emergency.house}, ${emergency.road}, ${new Number(emergency.postalCode)}, ${emergency.thana}, ${emergency.district}</p>
                    <p><b>Reported: </b>${new Date(emergency.timeposted).toLocaleString()}</p>

                `;
                /*                    <p><b>NID     :</b> ${emergency.user.nid}<p>*/

                


                if (emergency.description && emergency.description.trim()) {
                    content += `<p>Description: ${emergency.description}</p>`;
                }
                content += `
                    <label for="status-dropdown-${emergency.case_ID}"></label>
                    <button id="update-status-button-${emergency.case_ID}" class="status-update-button">Update Status</button>
                    <div id="status-dropdown-container-${emergency.case_ID}" class="dropdown-container" style="display:none;">
                        <button class="dropdown-option" id="rescued-option-${emergency.case_ID}">Rescued</button>
                        <button class="dropdown-option" id="delayed-option-${emergency.case_ID}">Delayed</button>
                    </div>
                `;
            

                emergencyDiv.innerHTML = content;
                emergencyList.appendChild(emergencyDiv);
                const updateButton = document.getElementById(`update-status-button-${emergency.case_ID}`);
                const dropdownContainer = document.getElementById(`status-dropdown-container-${emergency.case_ID}`);

                updateButton.addEventListener("click", function () {
                    dropdownContainer.style.display = dropdownContainer.style.display === "none" ? "block" : "none";
                });

                const rescuedOption = document.getElementById(`rescued-option-${emergency.case_ID}`);
                const delayedOption = document.getElementById(`delayed-option-${emergency.case_ID}`);

                rescuedOption.addEventListener("click", async function () {
                    try {
                        const updateResponse = await fetch(`http://localhost:8080/api/emergency/update/rescued/${emergency.case_ID}`, {
                            method: 'POST'
                        });

                        if (!updateResponse.ok) {
                            const updateError = await updateResponse.json();
                            throw new Error(updateError.message || "Failed to update status to rescued.");
                        }

                        alert(`Status updated to 'rescued' successfully.`);
                        dropdownContainer.style.display = "none";
                    } catch (updateError) {
                        console.error("Error updating status to rescued:", updateError);
                        alert("An error occurred while updating the status to rescued.");
                    }
                });

                delayedOption.addEventListener("click", async function () {
                    try {
                        const updateResponse = await fetch(`http://localhost:8080/api/emergency/update/delayed/${emergency.case_ID}`, {
                            method: 'POST'
                        });

                        if (!updateResponse.ok) {
                            const updateError = await updateResponse.json();
                            throw new Error(updateError.message || "Failed to update status to delayed.");
                        }

                        alert(`Status updated to 'delayed' successfully.`);
                        dropdownContainer.style.display = "none";
                    } catch (updateError) {
                        console.error("Error updating status to delayed:", updateError);
                        alert("An error occurred while updating the status to delayed.");
                    }
                });
            });

        }
    }
       catch (err) {
        console.error("Error fetching surveys:", err);
        alert("An error occurred while fetching surveys.");
    }
});