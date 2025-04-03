/*document.addEventListener("DOMContentLoaded", async function () {
    try {
        const nid = sessionStorage.getItem('nid');
    
        if (!nid) {
            console.error('NID not found in sessionStorage!');
            return;
        }
        const response = await fetch(`http://localhost:8080/api/users/complaint/${nid}`);
        const complainList = document.getElementById("complaint-list");
        if (response.status === 204) {
            // Handle No Content (empty list) case
            complainList.innerHTML = "<p></p>";
            return;
        }
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || "Failed to fetch emergencies.");
        }
        const complains = await response.json();


        
        if (complains.length === 0) {
            complainList.innerHTML = "<p>You Didn't Report Any Emergency .</p>";
        } 
        else {
            complains.forEach(complaint => {
            const complainDiv = document.createElement("div");
            complainDiv.classList.add("complaint");

            const normalizeKey = (key) => key.toLowerCase().replace(/\s+/g, '');

            const categoryClass = {
                bribery: "fire-color",
                scam: "natural-disaster-color",
                faultyservice: "medical-color",
                delayedservice: "other-color"
            }[normalizeKey(complaint.sector)] || "default-color";
            const statusClass = {
                submitted: "submitted-color",
                resolved: "rescued-color",
                assigned: "assigned-color",
                processing: "processing-color"
            }[complaint.status.toLowerCase()] || "default-status-color";

            let content = `
                    <h3>${complaint.complaint_id}</h3>
                    <br>
                    <button class="category-label  ${categoryClass}">${complaint.sector}</button>
                    <button class="status-label ${statusClass}">${complaint.status}</button>
                    <p>Department/Office: ${complaint.department}</p>

                    <p>Reported: ${new Date(complaint.postedAt).toLocaleString()}</p>
                `;
                if (complaint.attachment) {
                    content += `
                        <p>Attachment: <a href="http://localhost:8080/api/complaint/${complaint.complaint_id}/attachment" target="_blank">Download Attachment</a></p>
                    `;
                } else {
                    content += `<p>Attachment: None</p>`;
                }


                // Only add description if it exists
                if (complaint.details && complaint.details.trim()) {
                    content += `<p>Additional Note: ${complaint.details}</p>`;
                }

                complainDiv.innerHTML = content;
                complainList.appendChild(complainDiv);
            });

        }
    }
       catch (err) {
        console.error("Error fetching surveys:", err);
        alert("An error occurred while fetching surveys.");
    }
});*/
document.addEventListener("DOMContentLoaded", async function () {
    try {
        const nid = sessionStorage.getItem('nid');
    
        if (!nid) {
            console.error('NID not found in sessionStorage!');
            return;
        }
        const response = await fetch(`http://localhost:8080/api/users/complaint/${nid}`);

        const complainList = document.getElementById("complaint-list");
        if (response.status === 204) {
            // Handle No Content (empty list) case
            complainList.innerHTML = "<p></p>";
            return;
        }
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || "Failed to fetch emergencies.");
        }
        const complains = await response.json();


        
        if (complains.length === 0) {
            complainList.innerHTML = "<p>You Didn't Report Any Emergency .</p>";
        } 
        else {
            complains.forEach(complaint => {
            const complainDiv = document.createElement("div");
            complainDiv.classList.add("complaint");

            const normalizeKey = (key) => key.toLowerCase().replace(/\s+/g, '');

            const categoryClass = {
                bribery: "fire-color",
                scam: "natural-disaster-color",
                faultyservice: "medical-color",
                delayedservice: "other-color"
            }[normalizeKey(complaint.sector)] || "default-color";
            const statusClass = {
                submitted: "submitted-color",
                resolved: "rescued-color",
                assigned: "assigned-color",
                processing: "processing-color"
            }[complaint.status.toLowerCase()] || "default-status-color";

            let content = `
                    <h3><b>CASE#${complaint.complaint_id}</b></h3>
                    <br>
                    <button class="category-label  ${categoryClass}">${complaint.sector}</button>
                    <button class="status-label ${statusClass}">${complaint.status}</button>
                    <br><br>
                    <p><b>Department/Office:</b> ${complaint.department}</p>

                    <p><b>Reported         :</b> ${new Date(complaint.postedAt).toLocaleString()}</p>
                `;
                if (complaint.attachment) {
                    content += `
                     <p><b>Attachment      :</b> <a href="http://localhost:8080/api/complaint/${complaint.complaint_id}/attachment" target="_blank">Download Attachment</a></p>
                    `;
                } else {
                    content += `<p><b>Attachment:<b> None</p>`;
                }


                // Only add description if it exists
                if (complaint.details && complaint.details.trim()) {
                    content += `<p><b>Additional Note:</b> ${complaint.details}</p>`;
                }

                complainDiv.innerHTML = content;
                complainList.appendChild(complainDiv);
            });

        }
    }
       catch (err) {
        console.error("Error fetching surveys:", err);
        alert("An error occurred while fetching surveys.");
    }
});