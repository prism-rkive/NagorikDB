document.addEventListener("DOMContentLoaded", async function () {
    try {
        const s_id = sessionStorage.getItem('s_id');
    
        if (!s_id) {
            console.error('Not logged in');
            return;
        }

       
             const response = await fetch(`http://localhost:8080/api/complaint/assigned/${s_id}`);
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
                    const id=complaint.complaint_id;
                    let content = `
                    <h3>CASE#${complaint.complaint_id}</h3>
                    <br>
                    <button class="category-label  ${categoryClass}">${complaint.sector}</button>
                    <button class="status-label ${statusClass}">${complaint.status}</button>
                    <br><br>
                    <p>NID              : ${complaint.user.nid}</p>
                    <p>Department/Office: ${complaint.department}</p>

                    <p>Reported         : ${new Date(complaint.postedAt).toLocaleString()}</p>
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
                        content+=`<button class="update-status-btn" id="update-btn-${complaint.complaint_id}">Update Status</button>
                        `;
        
                        complainDiv.innerHTML = content;
                        const updateButton = complainDiv.querySelector(`#update-btn-${complaint.complaint_id}`);
                        updateButton.addEventListener("click", () => {
                            // Change the status locally
                            complaint.status = "Processing";
                
                            // Update the status button's text and class
                            //const statusButton = complainDiv.querySelector(`#status-${complaint.complaint_id}`);
                           // statusButton.textContent = complaint.status;
                           // statusButton.className = `status-label ${statusClass}`; // Update class dynamically if needed
                
                            // TODO: Update the status in the backend by making an API call
                            fetch(`http://localhost:8080/api/complaint/update/process/${complaint.complaint_id}`, {
                                method: "POST",
                                headers: {
                                    "Content-Type": "application/json"
                                },
                                body: JSON.stringify({ status: "Processing" })
                            })
                            .then(response => response.json())
                            .then(data => {
                                console.log("Status updated:", data);
                            })
                            .catch(error => {
                                console.error("Error updating status:", error);
                            });
                        });
                
                
                        complainList.appendChild(complainDiv);
                    });
        
                }
            }
               catch (err) {
                console.error("Error fetching surveys:", err);
                alert("An error occurred while fetching surveys.");
            }
        });