document.addEventListener("DOMContentLoaded", async function () {
    try {
        const a_id = sessionStorage.getItem('a_id');
    
        if (!a_id) {
            console.error('NID not found in sessionStorage!');
            return;
        }
        const response = await fetch(`http://localhost:8080/api/area/resolved/${a_id}`);
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